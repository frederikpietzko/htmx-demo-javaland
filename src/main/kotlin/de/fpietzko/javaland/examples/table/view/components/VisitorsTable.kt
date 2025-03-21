package de.fpietzko.javaland.examples.table.view.components

import de.fpietzko.javaland.components.datatable.Column
import de.fpietzko.javaland.components.datatable.SortDirection
import de.fpietzko.javaland.components.datatable.dataTable
import de.fpietzko.javaland.examples.table.model.TableModel
import de.fpietzko.javaland.examples.table.model.OrderedBy
import de.fpietzko.javaland.examples.table.model.OrderingState
import de.fpietzko.javaland.domain.visitors.JavalandVisitor
import kotlinx.html.DIV
import kotlinx.html.FlowContent


fun FlowContent.visitorTable(model: TableModel, block: DIV.() -> Unit = {}) {
    dataTable(model.visitors, columns(orderingState = model.orderingState), id = "visitorTable", block)
}

private fun columns(orderingState: OrderingState) = listOf(
    Column(
        "Name",
        JavalandVisitor::name,
        sortable = true,
        sortWith = "/table/add-sort",
        sortableName = "name",
        sortTarget = "#visitorTable",
        sortDirection = orderingState.orderedBy.toSortDirection("name")
    ),
    Column(
        "Age",
        JavalandVisitor::age,
        sortable = true,
        sortableName = "age",
        sortWith = "/table/add-sort",
        sortTarget = "#visitorTable",
        sortDirection = orderingState.orderedBy.toSortDirection("age")
    ),
    Column("Knows Kotlin", JavalandVisitor::knowsKotlin),
    Column("Knows Java", JavalandVisitor::knowsJava),
    Column("Knows htmx", JavalandVisitor::knowsHtmx),
    Column("Dislikes JavaScript", JavalandVisitor::dislikesJavascript),
)

private fun List<OrderedBy>.toSortDirection(column: String): SortDirection {
    return firstOrNull { it.column == column }?.sort ?: SortDirection.NONE
}
