package de.fpietzko.htmxdemo.examples.form.model

import de.fpietzko.htmxdemo.domain.visitors.Visitor

class RegisterFormSubmission(
    val name: String = "",
    val age: String = "",
    val knowsKotlin: Boolean = false,
    val knowsJava: Boolean = false,
    val knowsHtmx: Boolean = false,
    val dislikesJavascript: Boolean = false,
) {
    fun toVisitor() = Visitor(
        name,
        age.toInt(),
        knowsKotlin,
        knowsJava,
        knowsHtmx,
        dislikesJavascript
    )
}