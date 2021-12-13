package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day
import java.lang.Math.max
import kotlin.math.abs

private object Day05 : Day<List<Day05.Line>> {
    override val dayNum = 5
    override fun inputConverter(input: String) =
        input.trim().lines().map { it.split("->") }.map { Line(it[0].toPoint(), it[1].toPoint()) }

    class Day05Part1 : CommonPartTest<List<Line>>(
        day = this,
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

    class Day05Part2 : CommonPartTest<List<Line>>(
        day = this,
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


    data class Line(val start: Point, val end: Point) {
        fun getAllPoints(): List<Point> {
            val numOfPoints = max(abs(start.x - end.x), abs(start.y - end.y))
            val xProgression = progression(start.x, end.x)
            val yProgression = progression(start.y, end.y)
            return (0..numOfPoints).map { Point(start.x + xProgression * it, start.y + yProgression * it) }
        }

        val isDiagonal: Boolean = start.x != end.x && start.y != end.y

        private fun progression(start: Int, end: Int) = when {
            start == end -> 0
            start < end -> 1
            else -> -1
        }
    }

    data class Point(val x: Int, val y: Int)

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
