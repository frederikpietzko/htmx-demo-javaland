package de.fpietzko.javaland.visitors

data class JavalandVisitor(
    val name: String,
    val age: Int,
    val knowsKotlin: Boolean = false,
    val knowsJava: Boolean = false,
    val knowsHtmx: Boolean = false,
    val dislikesJavascript: Boolean = false,
) {
    companion object {
        fun empty() = JavalandVisitor("", 0, false, false, false, false)

        fun iitsDefault(name: String, age: Int) = JavalandVisitor(name, age, true, true, true, true)
    }
}
