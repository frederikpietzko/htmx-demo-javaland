package de.fpietzko.javaland.examples.realtime.controller

import de.fpietzko.javaland.config.websocket.WebsocketSessionHolder
import de.fpietzko.javaland.domain.visitors.JavalandVisitorRepository
import de.fpietzko.javaland.examples.realtime.model.VisitorStatistic
import de.fpietzko.javaland.examples.realtime.view.stats
import de.fpietzko.javaland.examples.realtime.view.visitorStatisticsPage
import de.fpietzko.javaland.html.mvc.HTMLResponseProvider
import de.fpietzko.javaland.html.mvc.respondHtml
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/realtime")
class RealtimeController(
    private val visitorRepository: JavalandVisitorRepository,
    private val websocketSessionHolder: WebsocketSessionHolder
) {

    init {
        visitorRepository.subscribe {
            websocketSessionHolder.broadcastHtmlSnippet {
                stats(VisitorStatistic.Companion.fromVisitors(visitorRepository.allVisitors()))
            }
        }
    }

    @GetMapping
    @ResponseBody
    fun realtime(): HTMLResponseProvider {
        val visitors = visitorRepository.allVisitors()
        val statistics = VisitorStatistic.fromVisitors(visitors)
        return respondHtml {
            visitorStatisticsPage(statistics)
        }
    }
}