package de.fpietzko.htmxdemo.examples.table.model

import de.fpietzko.htmxdemo.components.datatable.SortDirection

data class OrderedBy(val column: String, var sort: SortDirection = SortDirection.NONE)