package adventofcode.common

import kotlin.math.abs

interface PointCoordinates {
    val x: Int
    val y: Int
}

typealias Grid<T> = List<T>

data class Point(override val x: Int, override val y: Int) : PointCoordinates

fun Point.moveLeft(n: Int = 1) = Point(x = x - n, y = y)
fun Point.moveRight(n: Int = 1) = Point(x = x + n, y = y)
fun Point.moveUp(n: Int = 1) = Point(x = x, y = y + n)
fun Point.moveDown(n: Int = 1) = Point(x = x, y = y - n)

data class PointWithValue<T>(override val x: Int, override val y: Int, val value: T) : PointCoordinates {
    companion object {
        fun fromLinesInt(lines: List<String>): List<PointWithValue<Int>> =
            fromLinesGeneric(lines) { it.toString().toInt() }

        fun fromLinesChar(lines: List<String>): List<PointWithValue<Char>> =
            fromLinesGeneric(lines) { it }

        private fun <T> fromLinesGeneric(lines: List<String>, transform: (Char) -> T): List<PointWithValue<T>> =
            lines.mapIndexed { y, row -> row.mapIndexed { x, value -> PointWithValue(x, y, transform(value)) } }
                .flatten()
    }
}

fun Grid<PointWithValue<Int>>.toGraph(): Graph<PointWithValue<Int>> =
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

fun <T : PointCoordinates> Grid<T>.allPointsToTheLeft(point: T): List<T> =
    this.filter { it.y == point.y && it.x < point.x }.sortedByDescending { it.x }

fun <T : PointCoordinates> Grid<T>.allPointsToTheRight(point: T): List<T> =
    this.filter { it.y == point.y && it.x > point.x }.sortedBy { it.x }

fun <T : PointCoordinates> Grid<T>.allPointsToTheTop(point: T): List<T> =
    this.filter { it.x == point.x && it.y < point.y }.sortedByDescending { it.y }

fun <T : PointCoordinates> Grid<T>.allPointsToTheBottom(point: T): List<T> =
    this.filter { it.x == point.x && it.y > point.y }.sortedBy { it.y }

fun <T : PointCoordinates> Grid<T>.allPointsToTheTopLeft(point: T): List<T> =
    this.filter { (it.x - point.x) == (it.y - point.y) && it.x < point.x }.sortedByDescending { it.y }

fun <T : PointCoordinates> Grid<T>.allPointsToTheTopRight(point: T): List<T> =
    this.filter { (it.x - point.x) == -(it.y - point.y) && it.x > point.x }.sortedByDescending { it.y }

fun <T : PointCoordinates> Grid<T>.allPointsToTheBottomLeft(point: T): List<T> =
    this.filter { (it.x - point.x) == -(it.y - point.y) && it.x < point.x }.sortedBy { it.y }

fun <T : PointCoordinates> Grid<T>.allPointsToTheBottomRight(point: T): List<T> =
    this.filter { (it.x - point.x) == (it.y - point.y) && it.x > point.x }.sortedBy { it.y }

fun <T : PointCoordinates> Grid<T>.allAdjacentPoints(point: T): List<T> =
    this.filter { it.isAdjacent(point) }

fun <T : PointCoordinates> Grid<T>.pointUp(point: T): T? =
    this.find { it.x == point.x && it.y == point.y - 1 }

fun <T : PointCoordinates> Grid<T>.pointDown(point: T): T? =
    this.find { it.x == point.x && it.y == point.y + 1 }

fun <T : PointCoordinates> Grid<T>.pointLeft(point: T): T? =
    this.find { it.x == point.x - 1 && it.y == point.y }

fun <T : PointCoordinates> Grid<T>.pointRight(point: T): T? =
    this.find { it.x == point.x + 1 && it.y == point.y }

fun <T:PointCoordinates> T.isAdjacent(point: T): Boolean = (this.x == point.x - 1 && this.y == point.y) ||
        (this.x == point.x + 1 && this.y == point.y) ||
        (this.x == point.x && this.y == point.y - 1) ||
        (this.x == point.x && this.y == point.y + 1) ||
        (this.x == point.x + 1 && this.y == point.y + 1) ||
        (this.x == point.x - 1 && this.y == point.y - 1) ||
        (this.x == point.x + 1 && this.y == point.y - 1) ||
        (this.x == point.x - 1 && this.y == point.y + 1)

fun <T : PointCoordinates> Grid<T>.allStraightAdjacentPoints(point: T): List<T> =
    this.filter {
        (it.x == point.x - 1 && it.y == point.y) ||
                (it.x == point.x + 1 && it.y == point.y) ||
                (it.x == point.x && it.y == point.y - 1) ||
                (it.x == point.x && it.y == point.y + 1)
    }

fun <T : PointCoordinates> Grid<T>.width() = this.maxOf { it.x }
fun <T : PointCoordinates> Grid<T>.height() = this.maxOf { it.y }

fun Grid<PointWithValue<Int>>.repeatSquareAndTransform(
    times: Int,
    transform: (value: Int, x: Int, y: Int) -> Int
): Grid<PointWithValue<Int>> = this.let { grid ->
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

fun <T> Grid<PointWithValue<T>>.print() {
    val maxX = width()
    val maxY = height()
    for(y in 0..maxY) {
        for(x in 0..maxX) {
            print(this.find { it.x == x && it.y == y }?.value)
        }
        print("\n")
    }
}

