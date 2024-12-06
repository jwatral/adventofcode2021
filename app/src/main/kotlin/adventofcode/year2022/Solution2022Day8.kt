package adventofcode.year2022

import adventofcode.common.Grid
import adventofcode.common.PointWithValue
import adventofcode.common.allPointsToTheBottom
import adventofcode.common.allPointsToTheLeft
import adventofcode.common.allPointsToTheRight
import adventofcode.common.allPointsToTheTop

private object Solution2022Day8 : Day2022<Grid<PointWithValue<Int>>>(dayNum = 8) {
    override fun inputConverter(input: String): Grid<PointWithValue<Int>> =
        input.trim().lines().let { PointWithValue.fromLinesInt(it) }

    class Part1 : CommonPartTest(
        exampleResult = "21",
        taskResult = "1700",
    ) {
        override fun calculateResult(input: Grid<PointWithValue<Int>>): String =
            input.map { it.isVisible(input) }.count { it }.toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "8",
        taskResult = "470596",
    ) {
        override fun calculateResult(input: Grid<PointWithValue<Int>>): String =
            input.maxOfOrNull { it.score(input) }.toString()
    }

    fun List<PointWithValue<Int>>.isVisible(tree: PointWithValue<Int>) = all { it.value < tree.value }

    fun PointWithValue<Int>.isVisible(grid: Grid<PointWithValue<Int>>): Boolean =
        grid.allPointsToTheLeft(this).isVisible(this) ||
                grid.allPointsToTheRight(this).isVisible(this) ||
                grid.allPointsToTheTop(this).isVisible(this) ||
                grid.allPointsToTheBottom(this).isVisible(this)

    fun List<PointWithValue<Int>>.score(tree: PointWithValue<Int>) =
        indexOfFirst { it.value >= tree.value }.let { if (it == -1) this.size else it + 1 }

    fun PointWithValue<Int>.score(grid: Grid<PointWithValue<Int>>): Int =
        grid.allPointsToTheLeft(this).score(this) *
                grid.allPointsToTheRight(this).score(this) *
                grid.allPointsToTheTop(this).score(this) *
                grid.allPointsToTheBottom(this).score(this)

    // @formatter:off
    override val example =
"""
30373
25512
65332
33549
35390
""".trimIndent()
    // @formatter:on
}
