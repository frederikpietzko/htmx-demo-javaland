package de.fpietzko.javaland.examples.realtime.view

import de.fpietzko.javaland.components.CommonLayout
import de.fpietzko.javaland.examples.realtime.model.VisitorStatistic
import de.fpietzko.javaland.html.htmx.hxExt
import de.fpietzko.javaland.html.htmx.wsConnect
import de.fpietzko.javaland.html.templates.insert
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