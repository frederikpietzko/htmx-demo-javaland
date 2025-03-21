package de.fpietzko.javaland.components.datatable

import de.fpietzko.javaland.html.htmx.hxGet
import de.fpietzko.javaland.html.htmx.hxTarget
import de.fpietzko.javaland.html.htmx.hxTrigger
import kotlinx.html.*

fun <T> FlowContent.dataTable(data: List<T>, columns: List<Column<T>>, id: String? = null, block: DIV.() -> Unit = {}) {
    div("overflow-x-auto rounded-box border border-base-content/5 bg-base-100") {
        id?.let { this.id = it }
        block()
        table("table") {
            thead {
                tr {
                    for (column in columns) {
                        th {
                            if (column.sortable) {
                                a(classes = "p-1 cursor-pointer") {
                                    role = "button"
                                    hxTarget = column.sortTarget
                                    hxTrigger = "click"
                                    hxGet =
                                        column.sortWith + "?sort=" + column.sortDirection.next().name + "&columnName=" + column.sortableName
                                    when (column.sortDirection) {
                                        SortDirection.ASC -> i("h-1[em] fa fa-solid fa-caret-up")
                                        SortDirection.DESC -> i("h-1[em] fa fa-solid fa-caret-down")
                                        SortDirection.NONE -> i("h-1[em] fa fa-solid fa-caret-right")
                                    }
                                    span("pl-2") { +column.headerName }
                                }
                            } else {
                                +column.headerName
                            }
                        }
                    }
                }
            }
            tbody {
                for (row in data) {
                    tr {
                        for (column in columns) {
                            val value = column.value(row)
                            td {
                                when (value) {
                                    is String -> +value
                                    is Number -> +value.toString()
                                    is Boolean -> input(InputType.checkBox, classes = "checkbox checkbox-success") {
                                        disabled = true
                                        checked = value
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}