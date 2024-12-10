package adventofcode.year2024

import adventofcode.common.*

private object Solution2024Day8 : Day2024<Grid<PointWithValue<Char>>>(dayNum = 8) {
    override fun inputConverter(input: String): Grid<PointWithValue<Char>> =
        input.trim().lines().let { PointWithValue.fromLinesChar(it) }

    class Part1 : CommonPartTest(
        exampleResult = "14",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: Grid<PointWithValue<Char>>): String =
            input.filter { it.value != '.' }
                .flatMap { firstAntenna ->
                    val otherAntennas = input.filter { it.value == firstAntenna.value && it != firstAntenna }
                    otherAntennas.map { otherAntenna -> antinode(firstAntenna, otherAntenna) }
                }
                .toSet()
                .count { antinode -> input.find { it.x == antinode.x && it.y == antinode.y  } != null }
                .toString()
        }

    fun antinode(firstAntenna: PointWithValue<Char>, otherAntenna: PointWithValue<Char>): Point {
        val xDiff = firstAntenna.x - otherAntenna.x
        val yDiff = firstAntenna.y - otherAntenna.y
        return Point(otherAntenna.x - xDiff, otherAntenna.y - yDiff)
    }

    class Part2 : CommonPartTest(
        exampleResult = "34",
        taskResult = "1131",
    ) {
        override fun calculateResult(input: Grid<PointWithValue<Char>>): String {
            val xBound = input.maxOf { it.x }
            val yBound = input.maxOf { it.y }
            return input.filter { it.value != '.' }
                .flatMap { firstAntenna ->
                    val otherAntennas = input.filter { it.value == firstAntenna.value && it != firstAntenna }
                    otherAntennas.flatMap { otherAntenna -> antinodes(firstAntenna, otherAntenna, xBound, yBound) }
                }
                .toSet()
                .count()
                .toString()
        }
    }

    fun antinodes(firstAntenna: PointWithValue<Char>, otherAntenna: PointWithValue<Char>, xBound: Int, yBound: Int): List<Point> {
        val xDiff = firstAntenna.x - otherAntenna.x
        val yDiff = firstAntenna.y - otherAntenna.y
        val antinodes = mutableListOf<Point>()
        var x = firstAntenna.x
        var y = firstAntenna.y
        while (x <= xBound && y <= yBound && x >= 0 && y >= 0) {
            antinodes.add(Point(x, y))
            x -= xDiff
            y -= yDiff
        }
        return antinodes
    }


    // @formatter:off
    override val example =
"""
............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............
""".trimIndent()
    // @formatter:on
}
