package de.fpietzko.htmxdemo.html.templates

// heavily inspired by the KTOR Server HTML DSL https://ktor.io/docs/server-html-dsl.html


fun <T> T.insert(slot: Slot<T>) {
    slot.apply(this)
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