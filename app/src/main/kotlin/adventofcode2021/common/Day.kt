package adventofcode2021.common

interface Day<INPUT> {
    val dayNum: Int
    val example: String
    fun inputConverter(input: String): INPUT
}
