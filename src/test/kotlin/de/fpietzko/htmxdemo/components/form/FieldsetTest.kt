package de.fpietzko.htmxdemo.components.form

import de.fpietzko.htmxdemo.components.layout.Fragment
import kotlinx.html.FlowContent
import kotlinx.html.consumers.delayed
import kotlinx.html.stream.HTMLStreamBuilder
import org.junit.jupiter.api.Test

class FieldsetTest {

    @Test
    fun `it renders a fieldset properly`() {
        val rendered = renderSnippet {
            fieldset(
                FieldsetProps(
                    label = "Name",
                    name = "name",
                    placeholder = "Enter your name",
                    helperText = "Please enter your full name",
                )
            )
        }
        rendered shouldMatch """
            <fieldset class="fieldset w-full">
                <legend>Name</legend>
                <input type="text" name="name" class="w-full input" placeholder="Enter your name">
                <p class="fieldset-label">Please enter your full name</p>
            </fieldset>
        """.trimIndent()
    }
}

fun renderSnippet(snippet: FlowContent.() -> Unit): String = buildString {
    HTMLStreamBuilder(out =this, prettyPrint = false, xhtmlCompatible = false)
        .delayed()
        .let { Fragment(it).snippet() }
}

fun String.prettyfy(): String = this.replace("\\s{4}".toRegex(), "").replace("\n", "").replace("> <", "><")

infix fun String.shouldMatch(other: String) {
    val prettyThis = prettyfy()
    val prettyOther = other.prettyfy()
    assert(prettyThis == prettyOther) { "Expected: $prettyOther, but was: $prettyThis" }
}