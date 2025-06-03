package de.fpietzko.htmxdemo.examples.realtime.model

import de.fpietzko.htmxdemo.domain.visitors.Visitor

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
        fun fromVisitors(visitors: List<Visitor>) =
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