package de.fpietzko.htmxdemo.examples.table.model

import de.fpietzko.htmxdemo.domain.visitors.Visitor

data class TableModel(
    val visitors: List<Visitor>,
    val orderingState: OrderingState,
)