package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day
import adventofcode2021.common.Grid
import adventofcode2021.common.PointWithValue
import adventofcode2021.common.print
import adventofcode2021.common.repeatSquareAndTransform
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
        exampleResult = "315",
        taskResult = "2868",
    ) {
        override fun calculateResult(input: Grid<PointWithValue>): String =
            input
                .repeatSquareAndTransform(5) { value, x, y ->
                    val newValue = value + x + y
                    newValue.takeIf { it < 10 } ?: (newValue - 9)
                }
                .let {
                    it.print()
                    it.toGraph().shortestPath(it.first(), it.last())
                }
                .sumOf { it.value }
                .let { it - input.first().value }
                .toString()
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
