package de.fpietzko.htmxdemo.html.templates

interface Template<in T> {
    fun T.render(): Unit
}