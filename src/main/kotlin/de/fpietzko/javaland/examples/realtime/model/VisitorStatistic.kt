package de.fpietzko.javaland.examples.realtime.model

import de.fpietzko.javaland.domain.visitors.JavalandVisitor

data class VisitorStatistic(
    val totalVisitors: Int,
    val averageAge: Int,
    val knowsKotlin: Int,
    val knowsJava: Int,
    val knowsHtmx: Int,
    val dislikesJavascript: Int,
    val likesJavascript: Int = totalVisitors - dislikesJavascript,
) {
    companion object {
        fun fromVisitors(visitors: List<JavalandVisitor>) =
            VisitorStatistic(
                visitors.size,
                visitors.map { it.age }.average().toInt(),
                visitors.count { it.knowsKotlin },
                visitors.count { it.knowsJava },
                visitors.count { it.knowsHtmx },
                visitors.count { it.dislikesJavascript }
            )
    }
}