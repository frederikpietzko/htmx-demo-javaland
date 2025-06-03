package de.fpietzko.htmxdemo.components.datatable

data class Column<T>(
    val headerName: String,
    val value: (T) -> Any,
    val sortable: Boolean = false,
    val sortableName: String = "",
    val sortWith: String = "",
    val sortDirection: SortDirection = SortDirection.NONE,
    val sortTarget: String = ""
)