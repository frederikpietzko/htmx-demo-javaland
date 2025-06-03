package de.fpietzko.htmxdemo.examples.table.view.components

import de.fpietzko.htmxdemo.components.datatable.Column
import de.fpietzko.htmxdemo.components.datatable.SortDirection
import de.fpietzko.htmxdemo.components.datatable.dataTable
import de.fpietzko.htmxdemo.examples.table.model.TableModel
import de.fpietzko.htmxdemo.examples.table.model.OrderedBy
import de.fpietzko.htmxdemo.examples.table.model.OrderingState
import de.fpietzko.htmxdemo.domain.visitors.Visitor
import kotlinx.html.DIV
import kotlinx.html.FlowContent


fun FlowContent.visitorTable(model: TableModel, block: DIV.() -> Unit = {}) {
    dataTable(model.visitors, columns(orderingState = model.orderingState), id = "visitorTable", block)
}

private fun columns(orderingState: OrderingState) = listOf(
    Column(
        "Name",
        Visitor::name,
        sortable = true,
        sortWith = "/table/add-sort",
        sortableName = "name",
        sortTarget = "#visitorTable",
        sortDirection = orderingState.orderedBy.toSortDirection("name")
    ),
    Column(
        "Age",
        Visitor::age,
        sortable = true,
        sortableName = "age",
        sortWith = "/table/add-sort",
        sortTarget = "#visitorTable",
        sortDirection = orderingState.orderedBy.toSortDirection("age")
    ),
    Column("Knows Kotlin", Visitor::knowsKotlin),
    Column("Knows Java", Visitor::knowsJava),
    Column("Knows htmx", Visitor::knowsHtmx),
    Column("Dislikes JavaScript", Visitor::dislikesJavascript),
)

private fun List<OrderedBy>.toSortDirection(column: String): SortDirection {
    return firstOrNull { it.column == column }?.sort ?: SortDirection.NONE
}
