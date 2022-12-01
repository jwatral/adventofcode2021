package adventofcode.year2021

import adventofcode.common.CommonPartTest
import adventofcode.common.Day

private typealias Heightmap = List<List<Int>>

private object Day09 : Day<Heightmap> {
    override val dayNum = 9
    override val yearNum = 2021
    override fun inputConverter(input: String) =
        input.trim().lines().map { it.map { it.toString().toInt() } }

    class Day09Part1 : CommonPartTest<Heightmap>(
        day = this,
        exampleResult = "15",
        taskResult = "575",
    ) {
        override fun calculateResult(input: Heightmap): String =
            input.mapIndexed { y, row ->
                row.mapIndexed { x, cell ->
                    if (isLowerThanAllAdjacentLocations(x, y, input)) cell + 1 else 0
                }
            }
                .flatten().sum().toString()
    }

    class Day09Part2 : CommonPartTest<Heightmap>(
        day = this,
        exampleResult = "1134",
        taskResult = "1019700",
    ) {
        override fun calculateResult(input: Heightmap): String {
            val lowPoints = input.mapIndexed { y, row ->
                row.mapIndexedNotNull { x, _ -> if (isLowerThanAllAdjacentLocations(x, y, input)) x to y else null }
            }.flatten()
            val basinSizes = lowPoints.map {
                val visitedPoints: MutableList<Pair<Int, Int>> = mutableListOf()
                measureBasin(it.first, it.second, input, visitedPoints)
                visitedPoints.size
            }

            return basinSizes.sortedByDescending { it }.take(3).fold(1) { acc, i -> acc * i }.toString()
        }

        private fun measureBasin(x: Int, y: Int, input: List<List<Int>>, visitedPoints: MutableList<Pair<Int, Int>>) {
            if (x < 0 || y < 0 || x >= input[0].size || y >= input.size || input[y][x] == 9 || visitedPoints.contains(x to y))
                return
            visitedPoints += (x to y)
            measureBasin(x - 1, y, input, visitedPoints)
            measureBasin(x + 1, y, input, visitedPoints)
            measureBasin(x, y - 1, input, visitedPoints)
            measureBasin(x, y + 1, input, visitedPoints)
        }
    }

    private fun isLowerThanAllAdjacentLocations(x: Int, y: Int, heightmap: Heightmap): Boolean {
        val value = heightmap[y][x]
        val left = if (x > 0) heightmap[y][x - 1] else 10
        val right = if (x < heightmap[0].size - 1) heightmap[y][x + 1] else 10
        val top = if (y > 0) heightmap[y - 1][x] else 10
        val bottom = if (y < heightmap.size - 1) heightmap[y + 1][x] else 10
        return listOf(left, right, top, bottom).all { value < it }
    }

    override val example = """
2199943210
3987894921
9856789892
8767896789
9899965678
    """.trimIndent()
}
