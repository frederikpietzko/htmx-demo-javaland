package de.fpietzko.htmxdemo.html.templates

open class Slot<T> {
    private var content: T.(Slot<T>) -> Unit = { }

    operator fun invoke(content: T.(Slot<T>) -> Unit) {
        this.content = content
    }

    fun apply(destination: T) {
        destination.content(this)
    }
}