package de.fpietzko.javaland.html.htmx

import kotlinx.html.CommonAttributeGroupFacade

fun CommonAttributeGroupFacade.hyperscript(script: String) {
    attributes["_"] = script
}
