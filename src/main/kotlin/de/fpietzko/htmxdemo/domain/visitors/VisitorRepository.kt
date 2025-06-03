package de.fpietzko.htmxdemo.domain.visitors

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class VisitorRepository {
    private val visitors =
        Collections.synchronizedList(
            mutableListOf<Visitor>(
                Visitor.iitsDefault("Victor", 36),
                Visitor.iitsDefault("Markus", 38),
                Visitor.iitsDefault("Frederik", 27),
            )
        )

    private val observers = Collections.synchronizedSet(mutableSetOf<Observer>())

    fun add(visitor: Visitor): Visitor {
        visitors.add(visitor)
        observers.forEach { it(visitors) }
        return visitor
    }

    fun allVisitors(): List<Visitor> {
        return visitors.toList()
    }

    fun visitorCount(): Int {
        return visitors.size
    }

    fun subscribe(observer: Observer): Unsubscribe {
        observers.add(observer)
        return { observers.remove(observer) }
    }
}

typealias Observer = (List<Visitor>) -> Unit
typealias Unsubscribe = () -> Unit
