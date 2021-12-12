package adventofcode2021

import adventofcode2021.common.CommonTask

class Day09Part1 : CommonTask<Heightmap, Int>(
    dayNum = 9,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 15,
    taskResult = 575,
) {

    override fun calculateResult(input: Heightmap): Int =
        input.mapIndexed { y, row ->
            row.mapIndexed { x, cell ->
                if (isLowerThanAllAdjacentLocations(x, y, input)) cell + 1 else 0
            }
        }
            .flatten().sum()
}

class Day09Part2 : CommonTask<Heightmap, Int>(
    dayNum = 9,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 1134,
    taskResult = 1019700,
) {

    override fun calculateResult(input: Heightmap): Int {
        val lowPoints = input.mapIndexed { y, row ->
            row.mapIndexedNotNull { x, _ -> if (isLowerThanAllAdjacentLocations(x, y, input)) x to y else null }
        }.flatten()
        val basinSizes = lowPoints.map { visitedPoints = mutableListOf(); measureBasin(it.first, it.second, input); visitedPoints.size }

        return basinSizes.sortedByDescending { it }.take(3).fold(1) { acc, i -> acc * i }
    }

    private var visitedPoints: MutableList<Pair<Int, Int>> = mutableListOf()

    private fun measureBasin(x: Int, y: Int, input: List<List<Int>>) {
        if (x < 0 || y < 0 || x >= input[0].size || y >= input.size || input[y][x] == 9 || visitedPoints.contains(x to y))
            return
        visitedPoints += (x to y)
        measureBasin(x - 1, y, input)
        measureBasin(x + 1, y, input)
        measureBasin(x, y - 1, input)
        measureBasin(x, y + 1, input)
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

private typealias Heightmap = List<List<Int>>

private val inputConverter: (String) -> Heightmap = { s ->
    s.trim().lines().map { it.map { it.toString().toInt() } }
}

private val example = """
2199943210
3987894921
9856789892
8767896789
9899965678
""".trimIndent()
