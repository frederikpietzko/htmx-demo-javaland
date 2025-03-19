package de.fpietzko.javaland.util

import jakarta.servlet.http.HttpSession

inline fun <reified T> HttpSession.getAttributeOrDefault(name: String, default: T): T {
    return getAttribute(name) as? T ?: default
}
