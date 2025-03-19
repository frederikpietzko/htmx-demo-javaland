package de.fpietzko.javaland.components

import de.fpietzko.javaland.util.hxGet
import de.fpietzko.javaland.util.hxTarget
import de.fpietzko.javaland.util.hxTrigger
import kotlinx.html.*

enum class SortDirection {
    ASC, DESC, NONE;

    fun next() = when (this) {
        ASC -> DESC
        DESC -> NONE
        NONE -> ASC
    }
}

data class Column<T>(
    val headerName: String,
    val value: (T) -> Any,
    val sortable: Boolean = false,
    val sortableName: String = "",
    val sortWith: String = "",
    val sortDirection: SortDirection = SortDirection.NONE,
    val sortTarget: String = ""
)

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