package de.fpietzko.htmxdemo.examples.table.controller

import de.fpietzko.htmxdemo.components.datatable.SortDirection
import de.fpietzko.htmxdemo.examples.form.model.RegisterFormSubmission
import de.fpietzko.htmxdemo.examples.form.view.components.registerForm
import de.fpietzko.htmxdemo.examples.table.model.OrderingState
import de.fpietzko.htmxdemo.examples.table.model.TableModel
import de.fpietzko.htmxdemo.examples.table.view.components.addVisitorDrawer
import de.fpietzko.htmxdemo.examples.table.view.components.visitorTable
import de.fpietzko.htmxdemo.examples.table.view.visitorTablePage
import de.fpietzko.htmxdemo.html.htmx.hxSwapOob
import de.fpietzko.htmxdemo.html.mvc.HTMLResponseProvider
import de.fpietzko.htmxdemo.html.mvc.getAttributeOrDefault
import de.fpietzko.htmxdemo.html.mvc.respondHtml
import de.fpietzko.htmxdemo.html.mvc.respondHtmlSnippet
import de.fpietzko.htmxdemo.domain.visitors.VisitorService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@ResponseBody
@RequestMapping("/table")
class TableController(
    private val visitorService: VisitorService,
) {

    @GetMapping
    fun table(session: HttpSession): HTMLResponseProvider {
        val model = getTableData(session, "")
        return respondHtml {
            visitorTablePage(model)
        }
    }

    @GetMapping("/searchTable")
    fun searchTable(search: String, session: HttpSession): HTMLResponseProvider {
        val model = getTableData(session, search)
        return respondHtmlSnippet {
            visitorTable(model)
        }
    }

    @GetMapping("/add-sort")
    fun addSort(
        @RequestParam("columnName") columnName: String,
        @RequestParam("sort") sort: SortDirection,
        session: HttpSession
    ): HTMLResponseProvider {
        val orderingState = getOrderingState(session)
        orderingState.addOrder(columnName, sort)
        setOrderingState(session, orderingState)
        val model = getTableData(session, "")

        return respondHtmlSnippet {
            visitorTable(model)
        }
    }

    @GetMapping("/drawer")
    fun lazyDrawer() = respondHtmlSnippet { addVisitorDrawer() }

    @PostMapping("/add")
    fun addVisitor(model: RegisterFormSubmission, session: HttpSession): HTMLResponseProvider {
        visitorService.addVisitor(model.toVisitor())
        val tableData = getTableData(session, "")

        return respondHtmlSnippet {
            registerForm("/table/add")
            visitorTable(tableData) {
                hxSwapOob()
            }
        }
    }

    private fun getOrderingState(session: HttpSession): OrderingState =
        session.getAttributeOrDefault("ordering", OrderingState.Companion.default())

    private fun setOrderingState(session: HttpSession, orderingState: OrderingState) {
        session.setAttribute("ordering", orderingState)
    }

    private fun getTableData(session: HttpSession, filter: String): TableModel {
        val orderingState = getOrderingState(session)
        val visitors = visitorService
            .allVisitors()
            .sortedWith(orderingState.toComparator())
            .filter { it.name.contains(filter, ignoreCase = true) }
        return TableModel(visitors, orderingState)
    }
}