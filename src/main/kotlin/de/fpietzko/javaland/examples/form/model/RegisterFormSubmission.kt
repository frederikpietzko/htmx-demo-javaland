package de.fpietzko.javaland.examples.form.model

import de.fpietzko.javaland.domain.visitors.JavalandVisitor

class RegisterFormSubmission(
    val name: String = "",
    val age: String = "",
    val knowsKotlin: Boolean = false,
    val knowsJava: Boolean = false,
    val knowsHtmx: Boolean = false,
    val dislikesJavascript: Boolean = false,
) {
    fun toVisitor() = JavalandVisitor(
        name,
        age.toInt(),
        knowsKotlin,
        knowsJava,
        knowsHtmx,
        dislikesJavascript
    )
}