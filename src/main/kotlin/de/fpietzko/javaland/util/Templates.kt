package de.fpietzko.javaland.util
// heavily inspired by the KTOR Server HTML DSL https://ktor.io/docs/server-html-dsl.html

interface Template<in T> {
    fun T.render(): Unit
}


open class Slot<T> {
    private var content: T.(Slot<T>) -> Unit = { }

    operator fun invoke(content: T.(Slot<T>) -> Unit) {
        this.content = content
    }

    fun apply(destination: T) {
        destination.content(this)
    }
}

fun <T> T.insert(slot: Slot<T>) {
    slot.apply(this)
}

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

fun <TOuter, TTemplate : Template<TOuter>> TOuter.insert(template: TTemplate, build: TTemplate.() -> Unit) {
    template.build()
    with(template) {
        render()
    }
}

fun <TTemplate : Template<TOuter>, TOuter> TOuter.insert(template: TTemplate, slot: TemplateSlot<TTemplate>) {
    slot.apply(template)
    with(template) {
        render()
    }
}