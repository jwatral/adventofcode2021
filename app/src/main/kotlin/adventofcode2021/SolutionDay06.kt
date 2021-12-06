package adventofcode2021

import adventofcode2021.common.CommonTask

class Day06Part1 : CommonTask<List<Int>, Long>(
    dayNum = 6,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 5934,
    taskResult = 387413,
) {

    override fun calculateResult(initialState: List<Int>): Long = calculateNumOfFishes(initialState, 80)
}

class Day06Part2 : CommonTask<List<Int>, Long>(
    dayNum = 6,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 26984457539,
    taskResult = 1738377086345,
) {

    override fun calculateResult(initialState: List<Int>): Long = calculateNumOfFishes(initialState, 256)
}

private fun calculateNumOfFishes(initialState: List<Int>, days: Int): Long {
    val fishesPerDay = (0..8).map { day -> initialState.count { day == it }.toLong() }.toMutableList()
    repeat(days) {
        val newFishes = fishesPerDay[0]
        (0..7).forEach { fishesPerDay[it] = fishesPerDay[it+1] }
        fishesPerDay[6] += newFishes
        fishesPerDay[8] = newFishes
    }
    return fishesPerDay.sum()
}

private val inputConverter: (String) -> List<Int> = { s ->
    s.trim().split(",").map { it.toInt() }
}

private val example = "3,4,3,1,2"
