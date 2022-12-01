package adventofcode.common

interface Day<INPUT> {
    val dayNum: Int
    val yearNum: Int
    val example: String
    fun inputConverter(input: String): INPUT
}
