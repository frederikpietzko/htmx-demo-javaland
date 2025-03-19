package de.fpietzko.javaland.util

import kotlinx.html.CommonAttributeGroupFacade

fun CommonAttributeGroupFacade.hyperscript(script: String) {
    attributes["_"] = script
}
