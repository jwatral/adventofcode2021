package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day
import adventofcode2021.common.Point

private object Day13 : Day<Day13.Input> {
    override val dayNum = 13
    override fun inputConverter(input: String) =
        input.trim().lines()
            .partition { it.startsWith("fold along") }
            .let { (folds, points) ->
                Input(
                    points.filter { it.isNotBlank() }.map { it.split(",") }.map { Point(it[0].toInt(), it[1].toInt()) },
                    folds.map { it.split(" ")[2] }.map { it.split("=") }.map { FoldPoint(it[0], it[1].toInt()) }
                )
            }

    class Day13Part1 : CommonPartTest<Input>(
        day = this,
        exampleResult = "17",
        taskResult = "647",
    ) {
        override fun calculateResult(input: Input): String = fold(input.folds.take(1), input.points).count().toString()
    }

    private fun fold(folds: List<FoldPoint>, initialPoints: List<Point>): List<Point> =
    folds.fold(initialPoints){ points, (axis, index) ->
        points.map { (x, y) ->
            when{
                axis == "y" && y > index -> Point(x, index - (y - index))
                axis == "x" && x > index -> Point(index - (x - index), y)
                else -> Point(x, y)
            }
        }.distinct()
    }

    class Day13Part2 : CommonPartTest<Input>(
        day = this,
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: Input): String {
            fold(input.folds, input.points).print()
            return "42"
        }
    }

    data class FoldPoint(val axis: String, val index: Int)
    data class Input(val points: List<Point>, val folds: List<FoldPoint>)
    fun List<Point>.print() {
        val maxX = this.maxOf { it.x }
        val maxY = this.maxOf { it.y }
        for(y in 0..maxY) {
            for(x in 0..maxX) {
                print(if(this.contains(Point(x,y))) "█" else " " )
            }
            print("\n")
        }
    }
    val result = "HEJHJRCJ"

    override val example = """
6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5
    """.trimIndent()
}
