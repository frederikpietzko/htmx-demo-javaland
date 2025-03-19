package de.fpietzko.javaland.table

import de.fpietzko.javaland.components.Column
import de.fpietzko.javaland.components.SortDirection
import de.fpietzko.javaland.components.dataTable
import de.fpietzko.javaland.form.model.RegisterFormSubmission
import de.fpietzko.javaland.form.registerForm
import de.fpietzko.javaland.shared.CommonLayout
import de.fpietzko.javaland.util.*
import de.fpietzko.javaland.visitors.JavalandVisitor
import de.fpietzko.javaland.visitors.JavalandVisitorRepository
import jakarta.servlet.http.HttpSession
import kotlinx.html.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@HtmlController
@RequestMapping("/table")
class TableController(
    private val visitorRepository: JavalandVisitorRepository,
) {

    @GetMapping
    @ResponseBody
    fun table(session: HttpSession): HTMLResponseProvider {
        return respondTemplate(CommonLayout("Table Demo")) {
            children {
                div("flex flex-col p-4 gap-3") {
                    div("flex gap-2") {
                        label("input w-full flex items-center") {
                            i("h-[1em] opacity-50 fa fa-solid fa-search")
                            input(InputType.search, classes = "grow") {
                                id = "search"
                                placeholder = "Search"
                                name = "search"
                                hxTrigger = "keyup changed delay:500ms"
                                hxGet = "/table/searchTable"
                                hxTarget = "#visitorTable"
                                hxSwap = "outerHTML"
                            }
                        }

                        label("drawer-button btn btn-primary") {
                            htmlFor = "drawer-toggle-1"
                            +"Add"
                        }
                    }

                    div {
                        hxTrigger = "load"
                        hxGet = "/table/drawer"
                        hxSwap = "outerHTML"
                    }

                    renderDataTable(session)
                }
            }
        }
    }

    @GetMapping("/searchTable")
    @ResponseBody
    fun searchTable(search: String, session: HttpSession): HTMLResponseProvider {
        return respondHtmlSnippet {
            renderDataTable(session, search)
        }
    }

    @GetMapping("/add-sort")
    @ResponseBody
    fun addSort(
        @RequestParam("columnName") columnName: String, @RequestParam("sort") sort: SortDirection, session: HttpSession
    ): HTMLResponseProvider {
        val orderingState = session.getAttributeOrDefault<OrderingState>("ordering", OrderingState.default())
        orderingState.upsert(columnName, sort)
        session.setAttribute("ordering", orderingState)
        return respondHtmlSnippet {
            renderDataTable(session)
        }
    }

    @GetMapping("/drawer")
    @ResponseBody
    fun lazyDrawer() = respondHtmlSnippet {
        div("drawer drawer-end") {
            input(classes = "drawer-toggle", type = InputType.checkBox) {
                id = "drawer-toggle-1"
            }
            div("drawer-side z-100") {
                label("drawer-overlay") {
                    htmlFor = "drawer-toggle-1"
                    attributes["aria-label"] = "close sidebar"
                }
                div("z-10 bg-base-100 p-4 min-h-full text-base-content w-[800px]") {
                    registerForm("/table/add")
                }
            }
        }
    }

    @PostMapping("/add")
    @ResponseBody
    fun addVisitor(model: RegisterFormSubmission, session: HttpSession): HTMLResponseProvider {
        visitorRepository.add(model.toVisitor())
        return respondHtmlSnippet {
            registerForm("/table/add")
            renderDataTable(session) {
                hxSwapOob()
            }
        }
    }

    private fun FlowContent.renderDataTable(session: HttpSession, filter: String = "", block: DIV.() -> Unit = {}) {
        val orderingState = session.getAttributeOrDefault<OrderingState>("ordering", OrderingState.default())
        val data = visitorRepository.allVisitors().sortedWith(orderingState.toComparator())
            .filter { it.name.contains(filter, ignoreCase = true) }

        dataTable(data, columns(orderingSate = orderingState), id = "visitorTable", block)
    }
}


private data class OrderingState(val orderedBy: MutableList<OrderedBy>) {
    companion object {
        fun default() = OrderingState(mutableListOf(OrderedBy("name", SortDirection.ASC)))
    }

    fun upsert(column: String, sort: SortDirection) {
        val existing = orderedBy.firstOrNull { it.column == column }
        if (existing != null) {
            existing.sort = sort
        } else {
            orderedBy.add(OrderedBy(column, sort))
        }
    }

    fun toComparator(): Comparator<JavalandVisitor> {
        return orderedBy.fold(Comparator { _, _ -> 0 }) { acc, orderedBy ->
            if (orderedBy.sort == SortDirection.NONE) acc
            else acc.then(compareBy<JavalandVisitor> {
                when (orderedBy.column) {
                    "name" -> it.name
                    "age" -> it.age
                    else -> error("Unknown column: ${orderedBy.column}")
                }
            }.let { if (orderedBy.sort == SortDirection.ASC) it else it.reversed() })
        }
    }
}

private data class OrderedBy(val column: String, var sort: SortDirection = SortDirection.NONE)

private fun List<OrderedBy>.toSortDirection(column: String): SortDirection {
    return firstOrNull { it.column == column }?.sort ?: SortDirection.NONE
}

private fun columns(orderingSate: OrderingState) = listOf(
    Column(
        "Name",
        JavalandVisitor::name,
        sortable = true,
        sortWith = "/table/add-sort",
        sortableName = "name",
        sortTarget = "#visitorTable",
        sortDirection = orderingSate.orderedBy.toSortDirection("name")
    ),
    Column(
        "Age",
        JavalandVisitor::age,
        sortable = true,
        sortableName = "age",
        sortWith = "/table/add-sort",
        sortTarget = "#visitorTable",
        sortDirection = orderingSate.orderedBy.toSortDirection("age")
    ),
    Column("Knows Kotlin", JavalandVisitor::knowsKotlin),
    Column("Knows Java", JavalandVisitor::knowsJava),
    Column("Knows htmx", JavalandVisitor::knowsHtmx),
    Column("Dislikes JavaScript", JavalandVisitor::dislikesJavascript),
)


