package de.fpietzko.javaland.html.htmx

import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.classes

fun CommonAttributeGroupFacade.hxBoost() {
    this.attributes["hx-boost"] = "true"
}

fun CommonAttributeGroupFacade.hxExt(extensions: String) {
    this.attributes["hx-ext"] = extensions
}

var CommonAttributeGroupFacade.hxPost: String?
    get() = attributes["hx-post"]
    set(value: String?) {
        this.attributes["hx-post"] = value ?: ""
    }

var CommonAttributeGroupFacade.hxSwap: String?
    get() = this.attributes["hx-swap"]
    set(value) {
        this.attributes["hx-swap"] = value ?: ""
    }

var CommonAttributeGroupFacade.hxGet: String
    get() = this.attributes["hx-get"] ?: ""
    set(value) {
        this.attributes["hx-get"] = value
    }

var CommonAttributeGroupFacade.hxTrigger: String
    get() = this.attributes["hx-trigger"] ?: ""
    set(value) {
        this.attributes["hx-trigger"] = value
    }

var CommonAttributeGroupFacade.hxTarget: String
    get() = this.attributes["hx-target"] ?: ""
    set(value) {
        this.attributes["hx-target"] = value
    }

var CommonAttributeGroupFacade.hxPut: String
    get() = this.attributes["hx-put"] ?: ""
    set(value) {
        this.attributes["hx-put"] = value
    }

fun CommonAttributeGroupFacade.hxIndicator() {
    this.classes += setOf("htmx-indicator")
}

var CommonAttributeGroupFacade.sseConnect: String
    get() = this.attributes["sse-connect"] ?: ""
    set(value) {
        this.attributes["sse-connect"] = value
    }

var CommonAttributeGroupFacade.sseSwap: String
    get() = this.attributes["sse-swap"] ?: ""
    set(value) {
        this.attributes["sse-swap"] = value
    }

var CommonAttributeGroupFacade.hxSelect: String
    get() = this.attributes["hx-select"] ?: ""
    set(value) {
        this.attributes["hx-select"] = value
    }

fun CommonAttributeGroupFacade.hxSwapOob() {
    this.attributes["hx-swap-oob"] = "true"
}

var CommonAttributeGroupFacade.wsConnect: String
    get() = this.attributes["ws-connect"] ?: ""
    set(value) {
        this.attributes["ws-connect"] = value
    }