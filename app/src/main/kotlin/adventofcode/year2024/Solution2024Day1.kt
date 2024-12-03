package adventofcode.year2024

import kotlin.math.abs

private object Solution2024Day1 : Day2024<Solution2024Day1.Data>(dayNum = 1) {
    override fun inputConverter(input: String): Data =
        input.trim().lines()
            .map { it.split("   ") }
            .map { it[0] to it[1] }
            .let { Data(it.map {it.first.toInt()}, it.map { it.second.toInt() }) }

    class Part1 : CommonPartTest(
        exampleResult = "11",
        taskResult = "2086478",
    ) {
        override fun calculateResult(input: Data): String =
            input.left.sorted().zip(input.right.sorted())
                .sumOf { abs(it.first - it.second) }.toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "31",
        taskResult = "24941624",
    ) {
        override fun calculateResult(input: Data): String =
            input.left.sumOf { l -> input.right.filter { it == l }.size * l }
                .toString()
    }

    data class Data(
        val left: List<Int>,
        val right: List<Int>,
    )

    // @formatter:off
    override val example =
"""
3   4
4   3
2   5
1   3
3   9
3   3
""".trimIndent()
    // @formatter:on
}