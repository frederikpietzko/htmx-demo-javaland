package de.fpietzko.htmxdemo.html.templates

open class TemplateSlot<TTemplate> {
    private var content: (TTemplate.(TemplateSlot<TTemplate>) -> Unit)? = null

    val isEmpty: Boolean
        get() = content == null

    operator fun invoke(content: TTemplate.(TemplateSlot<TTemplate>) -> Unit) {
        this.content = content
    }

    fun apply(destination: TTemplate) {
        content?.let { destination.it(this) }
    }
}