package adventofcode.year2020

import adventofcode.common.CommonPartTest
import adventofcode.common.Day

private object Solution2020Day01 : Day<List<Int>> {
    override val dayNum = 99
    override val yearNum = 2021
    override fun inputConverter(input: String) =
        input.trim().lines().mapNotNull { it.toIntOrNull() }

    class Day99Part1 : CommonPartTest<List<Int>>(
        day = this,
        exampleResult = "514579",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<Int>): String = TODO()
    }

    class Day99Part2 : CommonPartTest<List<Int>>(
        day = this,
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<Int>): String = TODO()
    }

    override val example = """
        1721
        979
        366
        299
        675
        1456
    """.trimIndent()
}
