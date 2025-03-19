package de.fpietzko.javaland.realtime

import de.fpietzko.javaland.shared.CommonLayout
import de.fpietzko.javaland.util.HtmlController
import de.fpietzko.javaland.util.hxExt
import de.fpietzko.javaland.util.respondTemplate
import de.fpietzko.javaland.util.wsConnect
import de.fpietzko.javaland.visitors.JavalandVisitor
import de.fpietzko.javaland.visitors.JavalandVisitorRepository
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.id
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@HtmlController
@RequestMapping("/realtime")
class RealtimeController(
    private val visitorRepository: JavalandVisitorRepository,
    private val websocketSessionHolder: WebsocketSessionHolder
) {

    init {
        visitorRepository.subscribe {
            websocketSessionHolder.broadcastHtmlSnippet {
                stats(getVisitorStatistic(visitorRepository.allVisitors()))
            }
        }
    }

    @GetMapping
    @ResponseBody
    fun realtime() = respondTemplate(CommonLayout("Realtime example")) {
        val visitors = visitorRepository.allVisitors()
        children {
            div {
                hxExt("ws")
                wsConnect = "/realtime/ws"

                stats(getVisitorStatistic(visitors))
            }
        }
    }

    private fun getVisitorStatistic(visitors: List<JavalandVisitor>) =
        VisitorStatistic(
            visitors.size,
            visitors.map { it.age }.average().toInt(),
            visitors.count { it.knowsKotlin },
            visitors.count { it.knowsJava },
            visitors.count { it.knowsHtmx },
            visitors.count { it.dislikesJavascript }
        )
}

private data class VisitorStatistic(
    val totalVisitors: Int,
    val averageAge: Int,
    val knowsKotlin: Int,
    val knowsJava: Int,
    val knowsHtmx: Int,
    val dislikesJavascript: Int,
    val likesJavascript: Int = totalVisitors - dislikesJavascript,
)

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

private fun FlowContent.stats(statistic: VisitorStatistic) = div("stat shadow grid grid-cols-4") {
    id = "stats"
    stat("Javaland Visitors", statistic.totalVisitors, "Total visitors")
    stat("Average Age", statistic.averageAge, "Average age of visitors")
    stat("Know Kotlin", statistic.knowsKotlin, "Visitors who know Kotlin", "text-secondary")
    stat("Know Java", statistic.knowsJava, "Visitors who know Java")
    stat("Knows HTMX", statistic.knowsHtmx, "Visitors who know HTMX")
    stat("Dislike JavaScript", statistic.dislikesJavascript, "Totally relatable people", "text-success")
    stat("Like JavaScript", statistic.likesJavascript, "People who are wrong", "text-error")
}