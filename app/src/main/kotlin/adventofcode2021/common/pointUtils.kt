package adventofcode2021.common

import kotlin.math.abs

interface PointCoordinates {
    val x: Int
    val y: Int
}

typealias Grid<T> = List<T>

data class Point(override val x: Int, override val y: Int) : PointCoordinates

data class Line(val start: Point, val end: Point) {
    fun getAllPoints(): List<Point> {
        val numOfPoints = Math.max(abs(start.x - end.x), abs(start.y - end.y))
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

fun <T : PointCoordinates> Grid<T>.allAdjacentPoints(point: T): List<T> =
    this.filter {
        (it.x == point.x - 1 && it.y == point.y) ||
                (it.x == point.x + 1 && it.y == point.y) ||
                (it.x == point.x && it.y == point.y - 1) ||
                (it.x == point.x && it.y == point.y + 1) ||
                (it.x == point.x + 1 && it.y == point.y + 1) ||
                (it.x == point.x - 1 && it.y == point.y - 1) ||
                (it.x == point.x + 1 && it.y == point.y - 1) ||
                (it.x == point.x - 1 && it.y == point.y + 1)
    }
