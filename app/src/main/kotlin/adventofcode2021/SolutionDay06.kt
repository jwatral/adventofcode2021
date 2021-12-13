package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day

private object Day06 : Day<List<Int>> {
    override val dayNum = 6
    override fun inputConverter(input: String) =
        input.trim().split(",").map { it.toInt() }

    class Day06Part1 : CommonPartTest<List<Int>>(
        day = this,
        exampleResult = "5934",
        taskResult = "387413",
    ) {
        override fun calculateResult(input: List<Int>): String = calculateNumOfFishes(input, 80)
    }

    class Day06Part2 : CommonPartTest<List<Int>>(
        day = this,
        exampleResult = "26984457539",
        taskResult = "1738377086345",
    ) {
        override fun calculateResult(input: List<Int>): String = calculateNumOfFishes(input, 256)
    }

    private fun calculateNumOfFishes(initialState: List<Int>, days: Int): String {
        val fishesPerDay = (0..8).map { day -> initialState.count { day == it }.toLong() }.toMutableList()
        repeat(days) {
            val newFishes = fishesPerDay[0]
            (0..7).forEach { fishesPerDay[it] = fishesPerDay[it + 1] }
            fishesPerDay[6] += newFishes
            fishesPerDay[8] = newFishes
        }
        return fishesPerDay.sum().toString()
    }

    override val example = "3,4,3,1,2"
}
