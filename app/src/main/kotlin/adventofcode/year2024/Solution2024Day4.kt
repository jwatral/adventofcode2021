package adventofcode.year2024

import adventofcode.common.*

private object Solution2024Day4 : Day2024<Grid<PointWithValue<Char>>>(dayNum = 4) {
    override fun inputConverter(input: String): Grid<PointWithValue<Char>> =
        input.trim().lines().let { PointWithValue.fromLinesChar(it) }

    class Part1 : CommonPartTest(
        exampleResult = "18",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: Grid<PointWithValue<Char>>): String {
            val startingPoints = input.filter { it.value == 'X' }

            return startingPoints.flatMap {
                listOf(
                    input.allPointsToTheTop(it).map { it.value }.joinToString(""),
                    input.allPointsToTheTopRight(it).map { it.value }.joinToString(""),
                    input.allPointsToTheRight(it).map { it.value }.joinToString(""),
                    input.allPointsToTheBottomRight(it).map { it.value }.joinToString(""),
                    input.allPointsToTheBottom(it).map { it.value }.joinToString(""),
                    input.allPointsToTheBottomLeft(it).map { it.value }.joinToString(""),
                    input.allPointsToTheLeft(it).map { it.value }.joinToString(""),
                    input.allPointsToTheTopLeft(it).map { it.value }.joinToString(""),
                )
            }
                .count { it.startsWith("MAS") }
                .toString()
        }

    }

    class Part2 : CommonPartTest(
        exampleResult = "9",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: Grid<PointWithValue<Char>>): String {
            val startingPoints = input.filter { it.value == 'A' }

            return startingPoints.map {
                listOf(
                    input.allPointsToTheTopLeft(it).firstOrNull()?.value,
                    input.allPointsToTheBottomRight(it).firstOrNull()?.value
                ).containsAll(setOf('M', 'S')) &&
                        listOf(
                            input.allPointsToTheTopRight(it).firstOrNull()?.value,
                            input.allPointsToTheBottomLeft(it).firstOrNull()?.value
                        ).containsAll(setOf('M', 'S'))
            }
                .count { it }
                .toString()
        }
    }

    fun List<PointWithValue<Char>>.hasValue(value: Char): Boolean = firstOrNull()?.value == value

    // @formatter:off
    override val example =
"""
MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
""".trimIndent()
    // @formatter:on
}
