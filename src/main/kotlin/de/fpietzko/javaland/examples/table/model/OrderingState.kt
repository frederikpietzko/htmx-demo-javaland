package de.fpietzko.javaland.examples.table.model

import de.fpietzko.javaland.components.datatable.SortDirection
import de.fpietzko.javaland.domain.visitors.JavalandVisitor

data class OrderingState(val orderedBy: MutableList<OrderedBy>) {
    companion object {
        fun default() = OrderingState(mutableListOf(OrderedBy("name", SortDirection.ASC)))
    }

    fun addOrder(column: String, sort: SortDirection) {
        val existing = orderedBy.firstOrNull { it.column == column }
        if (existing != null) {
            existing.sort = sort
        } else {
            orderedBy.add(OrderedBy(column, sort))
        }
    }

    fun toComparator(): Comparator<JavalandVisitor> {
        return orderedBy.fold(Comparator { _, _ -> 0 }) { acc, orderedBy ->
            if (orderedBy.sort == SortDirection.NONE) acc
            else acc.then(compareBy<JavalandVisitor> {
                when (orderedBy.column) {
                    "name" -> it.name
                    "age" -> it.age
                    else -> error("Unknown column: ${orderedBy.column}")
                }
            }.let { if (orderedBy.sort == SortDirection.ASC) it else it.reversed() })
        }
    }
}