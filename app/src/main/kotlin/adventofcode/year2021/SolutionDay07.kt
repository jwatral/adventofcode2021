package adventofcode.year2021

import adventofcode.common.CommonPartTest
import adventofcode.common.Day
import kotlin.math.abs

private object Day07 : Day<List<Int>> {
    override val dayNum = 7
    override val yearNum = 2021
    override fun inputConverter(input: String) =
        input.trim().split(",").map { it.toInt() }

    class Day07Part1 : CommonPartTest<List<Int>>(
        day = this,
        exampleResult = "37",
        taskResult = "351901",
    ) {
        override fun calculateResult(input: List<Int>): String {
            val minValue = input.minOrNull() ?: 0
            val maxValue = input.maxOrNull() ?: 0
            val result = (minValue..maxValue).minOfOrNull { checkedPosition ->
                input.sumOf { abs(it - checkedPosition) }
            } ?: 0
            return result.toString()
        }
    }

    class Day07Part2 : CommonPartTest<List<Int>>(
        day = this,
        exampleResult = "168",
        taskResult = "101079875",
    ) {
        override fun calculateResult(input: List<Int>): String {
            val minValue = input.minOrNull() ?: 0
            val maxValue = input.maxOrNull() ?: 0
            val result = (minValue..maxValue).minOfOrNull { checkedPosition ->
                input.sumOf { sumOfArithmeticProgression(it, checkedPosition) }
            } ?: 0
            return result.toString()
        }

        private fun sumOfArithmeticProgression(start: Int, end: Int): Int =
            (1..abs(start - end)).fold(0) { acc, n -> acc + n }
    }

    override val example = "16,1,2,0,4,2,7,1,2,14"
}
