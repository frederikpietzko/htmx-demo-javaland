package de.fpietzko.htmxdemo.domain.visitors

data class Visitor(
    val name: String,
    val age: Int,
    val knowsKotlin: Boolean = false,
    val knowsJava: Boolean = false,
    val knowsHtmx: Boolean = false,
    val dislikesJavascript: Boolean = false,
) {
    companion object {
        fun empty() = Visitor("", 0, false, false, false, false)

        fun iitsDefault(name: String, age: Int) = Visitor(name, age, true, true, true, true)
    }
}
