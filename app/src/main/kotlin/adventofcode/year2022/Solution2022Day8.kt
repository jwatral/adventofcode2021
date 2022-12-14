package adventofcode.year2022

import adventofcode.common.Grid
import adventofcode.common.PointWithValue
import adventofcode.common.allPointsToTheBottom
import adventofcode.common.allPointsToTheLeft
import adventofcode.common.allPointsToTheRight
import adventofcode.common.allPointsToTheTop

private object Solution2022Day8 : Day2022<Grid<PointWithValue>>(dayNum = 8) {
    override fun inputConverter(input: String): Grid<PointWithValue> =
        input.trim().lines().let { PointWithValue.fromLines(it) }

    class Part1 : CommonPartTest(
        exampleResult = "21",
        taskResult = "1700",
    ) {
        override fun calculateResult(input: Grid<PointWithValue>): String =
            input.map { it.isVisible(input) }.count { it }.toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "8",
        taskResult = "470596",
    ) {
        override fun calculateResult(input: Grid<PointWithValue>): String =
            input.maxOfOrNull { it.score(input) }.toString()
    }

    fun List<PointWithValue>.isVisible(tree: PointWithValue) = all { it.value < tree.value }

    fun PointWithValue.isVisible(grid: Grid<PointWithValue>): Boolean =
        grid.allPointsToTheLeft(this).isVisible(this) ||
                grid.allPointsToTheRight(this).isVisible(this) ||
                grid.allPointsToTheTop(this).isVisible(this) ||
                grid.allPointsToTheBottom(this).isVisible(this)

    fun List<PointWithValue>.score(tree: PointWithValue) =
        indexOfFirst { it.value >= tree.value }.let { if (it == -1) this.size else it + 1 }

    fun PointWithValue.score(grid: Grid<PointWithValue>): Int =
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
