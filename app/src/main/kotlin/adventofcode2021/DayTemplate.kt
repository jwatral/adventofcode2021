package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day

private object Day99 : Day<List<String>> {
    override val dayNum = 99
    override fun inputConverter(input: String) =
        input.trim().lines()

    class Day99Part1 : CommonPartTest<List<String>>(
        day = this,
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<String>): String = TODO()
    }

    class Day99Part2 : CommonPartTest<List<String>>(
        day = this,
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<String>): String = TODO()
    }

    override val example = """
        some
        multiline
        example
    """.trimIndent()
}
