package de.fpietzko.htmxdemo.examples.realtime.controller

import de.fpietzko.htmxdemo.config.websocket.WebsocketSessionHolder
import de.fpietzko.htmxdemo.domain.visitors.VisitorRepository
import de.fpietzko.htmxdemo.examples.realtime.model.VisitorStatistic
import de.fpietzko.htmxdemo.examples.realtime.view.components.stats
import de.fpietzko.htmxdemo.examples.realtime.view.visitorStatisticsPage
import de.fpietzko.htmxdemo.html.mvc.HTMLResponseProvider
import de.fpietzko.htmxdemo.html.mvc.respondHtml
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/realtime")
class RealtimeController(
    private val visitorRepository: VisitorRepository,
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