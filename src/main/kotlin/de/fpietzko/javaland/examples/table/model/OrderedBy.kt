package de.fpietzko.javaland.examples.table.model

import de.fpietzko.javaland.components.SortDirection

data class OrderedBy(val column: String, var sort: SortDirection = SortDirection.NONE)