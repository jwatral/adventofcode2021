package adventofcode.year2024

import kotlin.math.abs

private object Solution2024Day2 : Day2024<List<List<Int>>>(dayNum = 2) {
    override fun inputConverter(input: String): List<List<Int>> =
        input.trim().lines().map { it.split(" ").map { it.toInt() } }

    class Part1 : CommonPartTest(
        exampleResult = "2",
        taskResult = "411",
    ) {
        override fun calculateResult(input: List<List<Int>>): String =
            input.map {
                val levels = it.zipWithNext { a, b -> a - b }
                levels.isItSafe()
            }.filter { it }.size.toString()
    }

    private fun List<Int>.isItSafe(): Boolean {
        val allStepsSafe = all { abs(it) in 1..3 }
        val allStepsSameTrend = partition { it > 0 }.let { it.first.isEmpty() || it.second.isEmpty() }
        return allStepsSafe && allStepsSameTrend
    }

    class Part2 : CommonPartTest(
        exampleResult = "4",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: List<List<Int>>): String = input.map { i ->
            val levels = i.zipWithNext { a, b -> a - b }
            val dampened = i.mapIndexed { li, l -> i.filterIndexed { ci, c -> ci != li }.zipWithNext { a, b -> a - b }}
                .any { it.isItSafe() }
            levels.isItSafe() || dampened
        }.filter { it }.size.toString()
    }



    // @formatter:off
    override val example =
"""
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
""".trimIndent()
    // @formatter:on
}
