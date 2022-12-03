package adventofcode.year2021

private object Day06 : Day2021<List<Int>>(dayNum = 6) {
        override fun inputConverter(input: String) =
        input.trim().split(",").map { it.toInt() }

    class Day06Part1 : CommonPartTest(
        exampleResult = "5934",
        taskResult = "387413",
    ) {
        override fun calculateResult(input: List<Int>): String = calculateNumOfFishes(input, 80)
    }

    class Day06Part2 : CommonPartTest(
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
