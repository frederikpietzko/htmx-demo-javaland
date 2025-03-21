package de.fpietzko.javaland.html.templates

interface Template<in T> {
    fun T.render(): Unit
}