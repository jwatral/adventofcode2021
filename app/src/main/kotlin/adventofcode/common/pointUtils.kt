package adventofcode.common

import kotlin.math.abs

interface PointCoordinates {
    val x: Int
    val y: Int
}

typealias Grid<T> = List<T>

data class Point(override val x: Int, override val y: Int) : PointCoordinates

data class PointWithValue(override val x: Int, override val y: Int, val value: Int) : PointCoordinates {
    companion object {
        fun fromLines(lines: List<String>): List<PointWithValue> =
            lines.mapIndexed { y, row -> row.mapIndexed { x, value -> PointWithValue(x, y, value.toString().toInt()) } }
                .flatten()
    }
}

fun Grid<PointWithValue>.toGraph(): Graph<PointWithValue> =
    Graph(
        this.map { point ->
            this.allStraightAdjacentPoints(point).map { neigbour -> (point to neigbour) to neigbour.value }
        }
            .flatten()
            .toMap()
    )

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

fun <T : PointCoordinates> Grid<T>.allStraightAdjacentPoints(point: T): List<T> =
    this.filter {
        (it.x == point.x - 1 && it.y == point.y) ||
                (it.x == point.x + 1 && it.y == point.y) ||
                (it.x == point.x && it.y == point.y - 1) ||
                (it.x == point.x && it.y == point.y + 1)
    }

fun <T : PointCoordinates> Grid<T>.width() = this.maxOf { it.x }
fun <T : PointCoordinates> Grid<T>.height() = this.maxOf { it.y }

fun Grid<PointWithValue>.repeatSquareAndTransform(
    times: Int,
    transform: (value: Int, x: Int, y: Int) -> Int
): Grid<PointWithValue> = this.let { grid ->
    val width = grid.width() + 1
    val height = grid.height() + 1
    grid.map { originalPoint ->
        (0 until times).map { xMultiply ->
            (0 until times).map { yMultiply ->
                PointWithValue(
                    originalPoint.x + xMultiply * width,
                    originalPoint.y + yMultiply * height,
                    transform(originalPoint.value, xMultiply, yMultiply)
                )
            }
        }
    }.flatten().flatten()
}

fun Grid<PointWithValue>.print() {
    val maxX = width()
    val maxY = height()
    for(y in 0..maxY) {
        for(x in 0..maxX) {
            print(this.find { it.x == x && it.y == y }?.value)
        }
        print("\n")
    }
}

