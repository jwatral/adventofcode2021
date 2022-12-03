package adventofcode.year2021

import adventofcode.common.Line
import adventofcode.common.Point

private object Day05 : Day2021<List<Line>>(dayNum = 5) {
        override fun inputConverter(input: String) =
        input.trim().lines().map { it.split("->") }.map { Line(it[0].toPoint(), it[1].toPoint()) }

    class Day05Part1 : CommonPartTest(
        exampleResult = "5",
        taskResult = "8111",
    ) {
        override fun calculateResult(input: List<Line>): String {
            val pointOccurrences = PointOccurrences()
            input.filterNot { it.isDiagonal }.forEach(pointOccurrences::addLine)
            pointOccurrences.counter.filter { it.value >= 2 }.count()
            return pointOccurrences.counter.filter { it.value >= 2 }.count().toString()
        }
    }

    class Day05Part2 : CommonPartTest(
        exampleResult = "12",
        taskResult = "22088",
    ) {
        override fun calculateResult(input: List<Line>): String {
            val pointOccurrences = PointOccurrences()
            input.forEach(pointOccurrences::addLine)
            pointOccurrences.counter.filter { it.value >= 2 }.count()
            return pointOccurrences.counter.filter { it.value >= 2 }.count().toString()
        }
    }

    class PointOccurrences() {
        val counter: MutableMap<Point, Int> = mutableMapOf()
        fun addLine(line: Line) = line.getAllPoints().forEach(::addPoint)
        private fun addPoint(point: Point) = counter[point].also { counter[point] = (it ?: 0) + 1 }
    }

    private fun String.toPoint(): Point = this.trim().split(",").map { it.toInt() }.let { Point(it[0], it[1]) }

    override val example = """
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2
    """.trimIndent()
}
