package de.fpietzko.htmxdemo.examples.table.model

import de.fpietzko.htmxdemo.components.datatable.SortDirection
import de.fpietzko.htmxdemo.domain.visitors.Visitor

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

    fun toComparator(): Comparator<Visitor> {
        return orderedBy.fold(Comparator { _, _ -> 0 }) { acc, orderedBy ->
            if (orderedBy.sort == SortDirection.NONE) acc
            else acc.then(compareBy<Visitor> {
                when (orderedBy.column) {
                    "name" -> it.name
                    "age" -> it.age
                    else -> error("Unknown column: ${orderedBy.column}")
                }
            }.let { if (orderedBy.sort == SortDirection.ASC) it else it.reversed() })
        }
    }
}