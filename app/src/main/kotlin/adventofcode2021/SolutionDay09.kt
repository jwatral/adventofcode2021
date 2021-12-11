package adventofcode2021

import adventofcode2021.common.CommonTask

class Day09Part1 : CommonTask<Heightmap, Int>(
    dayNum = 9,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 15,
    taskResult = 575,
) {

    override fun calculateResult(input: Heightmap): Int {
        var sum = 0
        for(x in input[0].indices) {
            for(y in input.indices) {
                if(isLowerThanAllAdjacentLocations(x, y, input)) sum += input[y][x] + 1
            }
        }
        return sum
    }

    private fun isLowerThanAllAdjacentLocations(x: Int, y: Int, heightmap: Heightmap): Boolean {
        val value = heightmap[y][x]
        val left = if(x > 0) heightmap[y][x-1] else 10
        val right = if(x < heightmap[0].size - 1) heightmap[y][x+1] else 10
        val top = if(y > 0) heightmap[y-1][x] else 10
        val bottom = if(y < heightmap.size - 1) heightmap[y+1][x] else 10
        return listOf(left, right, top, bottom).all { value < it }
    }
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
