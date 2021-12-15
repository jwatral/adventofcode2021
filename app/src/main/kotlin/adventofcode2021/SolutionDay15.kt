package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day
import adventofcode2021.common.Grid
import adventofcode2021.common.PointWithValue
import adventofcode2021.common.toGraph

private object Day15 : Day<Grid<PointWithValue>> {
    override val dayNum = 15
    override fun inputConverter(input: String) =
        input.trim().lines().let { PointWithValue.fromLines(it) }

    class Day15Part1 : CommonPartTest<Grid<PointWithValue>>(
        day = this,
        exampleResult = "40",
        taskResult = "373",
    ) {
        override fun calculateResult(input: Grid<PointWithValue>): String =
            input.toGraph()
                .shortestPath(input.first(), input.last())
                .sumOf { it.value }
                .let { it - input.first().value }
                .toString()
    }

    class Day15Part2 : CommonPartTest<Grid<PointWithValue>>(
        day = this,
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: Grid<PointWithValue>): String = TODO()
    }

    override val example = """
1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581
    """.trimIndent()
}
