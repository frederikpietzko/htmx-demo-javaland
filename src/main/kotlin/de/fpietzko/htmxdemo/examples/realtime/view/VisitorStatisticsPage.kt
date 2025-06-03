package de.fpietzko.htmxdemo.examples.realtime.view

import de.fpietzko.htmxdemo.components.layout.CommonLayout
import de.fpietzko.htmxdemo.examples.realtime.model.VisitorStatistic
import de.fpietzko.htmxdemo.examples.realtime.view.components.stats
import de.fpietzko.htmxdemo.html.htmx.hxExt
import de.fpietzko.htmxdemo.html.htmx.wsConnect
import de.fpietzko.htmxdemo.html.templates.insert
import kotlinx.html.HTML
import kotlinx.html.div

fun HTML.visitorStatisticsPage(model: VisitorStatistic) = insert(CommonLayout("Realtime Example")) {
    children {
        div {
            hxExt("ws")
            wsConnect = "/realtime/ws"

            stats(model)
        }
    }
}