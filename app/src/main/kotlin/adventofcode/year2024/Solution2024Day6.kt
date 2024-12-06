package adventofcode.year2024

import adventofcode.common.*

private object Solution2024Day6 : Day2024<Grid<PointWithValue<Char>>>(dayNum = 6) {
    override fun inputConverter(input: String): Grid<PointWithValue<Char>> =
        input.trim().lines().let { PointWithValue.fromLinesChar(it) }

    class Part1 : CommonPartTest(
        exampleResult = "41",
        taskResult = "5101",
    ) {
        override fun calculateResult(input: Grid<PointWithValue<Char>>): String {
            val startingPoint = input.find { it.value == '^' }!!
            return input.guardPath(startingPoint).size.toString()
        }
    }

    class Part2 : CommonPartTest(
        exampleResult = "6",
        taskResult = "1951",
    ) {
        override fun calculateResult(input: Grid<PointWithValue<Char>>): String {
            val startingPoint = input.find { it.value == '^' }!!
            return input.guardPath(startingPoint)
                .drop(1)
                .count { point ->
                    input.guardPath(startingPoint, point).last().value == 'X'
                }.toString()
        }
    }

    enum class Direction {

        UP, RIGHT, DOWN, LEFT;

        fun turn90degrees() =
            Direction.values()[(this.ordinal + 1) % 4]
    }

    fun Grid<PointWithValue<Char>>.guardPath(
        startingPoint: PointWithValue<Char>,
        additionalObstacle: PointCoordinates? = null,
    ): MutableSet<PointWithValue<Char>> {
        var currentPoint: PointWithValue<Char>? = startingPoint
        val path = mutableSetOf(startingPoint)
        val cycleDetection = mutableSetOf<Pair<PointWithValue<Char>, Direction>>()
        var direction = Direction.UP
        while (currentPoint != null) {
            if ((currentPoint to direction) in cycleDetection) {
                path.add(PointWithValue(-1, -1, 'X'))
                break
            }
            cycleDetection.add(currentPoint to direction)
            var pointAhead = when (direction) {
                Direction.UP -> pointUp(currentPoint)
                Direction.DOWN -> pointDown(currentPoint)
                Direction.LEFT -> pointLeft(currentPoint)
                Direction.RIGHT -> pointRight(currentPoint)
            }
            if (pointAhead?.x == additionalObstacle?.x && pointAhead?.y == additionalObstacle?.y) {
                pointAhead = pointAhead?.copy(value = '#')
            }
            when (pointAhead?.value) {
                '.', '^' -> {
                    currentPoint = pointAhead; path.add(currentPoint)
                }

                '#' -> {
                    direction = direction.turn90degrees()
                }

                null -> currentPoint = null
            }
        }
        return path
    }

    // @formatter:off
    override val example =
"""
....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
""".trimIndent()
    // @formatter:on
}
