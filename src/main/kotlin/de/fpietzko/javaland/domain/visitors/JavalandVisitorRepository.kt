package de.fpietzko.javaland.domain.visitors

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JavalandVisitorRepository {
    private val visitors =
        Collections.synchronizedList(
            mutableListOf<JavalandVisitor>(
                JavalandVisitor.iitsDefault("Victor", 36),
                JavalandVisitor.iitsDefault("Markus", 38),
                JavalandVisitor.iitsDefault("Frederik", 27),
            )
        )

    private val observers = Collections.synchronizedSet(mutableSetOf<Observer>())

    fun add(visitor: JavalandVisitor): JavalandVisitor {
        visitors.add(visitor)
        observers.forEach { it(visitors) }
        return visitor
    }

    fun allVisitors(): List<JavalandVisitor> {
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

typealias Observer = (List<JavalandVisitor>) -> Unit
typealias Unsubscribe = () -> Unit
