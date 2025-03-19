package de.fpietzko.javaland.form.model

import de.fpietzko.javaland.visitors.JavalandVisitor

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