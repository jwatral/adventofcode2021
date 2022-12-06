package adventofcode.year2022

import kotlin.streams.toList

private object Solution2022Day6 : Day2022<String>(dayNum = 6) {
    override fun inputConverter(input: String): String = input.trim()

    class Part1 : CommonPartTest(
        exampleResult = "11",
        taskResult = "1480",
    ) {
        override fun calculateResult(input: String): String = input.findMarkerPosition(4).toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "26",
        taskResult = "2746",
    ) {
        override fun calculateResult(input: String): String = input.findMarkerPosition(14).toString()
    }

    fun String.findMarkerPosition(markerLength: Int): Int =
        this.windowed(markerLength, 1)
            .map { it.chars().toList().toSet() }
            .indexOfFirst { it.size == markerLength }
            .let { it + markerLength }

    override val example =
"""
zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw
""".trimIndent()
}
