package de.fpietzko.htmxdemo.html.htmx

import kotlinx.html.CommonAttributeGroupFacade

fun CommonAttributeGroupFacade.hyperscript(script: String) {
    attributes["_"] = script
}
