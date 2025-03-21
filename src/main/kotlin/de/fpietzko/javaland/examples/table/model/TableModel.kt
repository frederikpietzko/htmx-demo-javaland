package de.fpietzko.javaland.examples.table.model

import de.fpietzko.javaland.domain.visitors.JavalandVisitor

data class TableModel(
    val visitors: List<JavalandVisitor>,
    val orderingState: OrderingState,
)