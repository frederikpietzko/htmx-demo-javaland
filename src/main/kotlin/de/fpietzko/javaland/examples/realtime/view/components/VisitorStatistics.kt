package de.fpietzko.javaland.examples.realtime.view.components

import de.fpietzko.javaland.examples.realtime.model.VisitorStatistic
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.id

fun FlowContent.stats(statistic: VisitorStatistic) = div("stat shadow grid grid-cols-4") {
    id = "stats"
    stat("Javaland Visitors", statistic.totalVisitors, "Total visitors")
    stat("Average Age", statistic.averageAge, "Average age of visitors")
    stat("Know Kotlin", statistic.knowsKotlin, "Visitors who know Kotlin", "text-secondary")
    stat("Know Java", statistic.knowsJava, "Visitors who know Java")
    stat("Knows HTMX", statistic.knowsHtmx, "Visitors who know HTMX")
    stat("Dislike JavaScript", statistic.dislikesJavascript, "Totally relatable people", "text-success")
    stat("Like JavaScript", statistic.likesJavascript, "People who are wrong", "text-error")
}

private fun FlowContent.stat(title: String, value: Int, desc: String, highlight: String = "text-primary") =
    div("stat") {
        div("stat-title") {
            +title
        }
        div("stat-value $highlight") {
            +value.toString()
        }
        div("stat-desc") {
            +desc
        }
    }
