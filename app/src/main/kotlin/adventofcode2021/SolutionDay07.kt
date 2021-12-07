package adventofcode2021

import adventofcode2021.common.CommonTask
import kotlin.math.abs

class Day07Part1 : CommonTask<List<Int>, Int>(
    dayNum = 7,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 37,
    taskResult = 351901,
) {

    override fun calculateResult(initialPositions: List<Int>): Int {
        val minValue = initialPositions.minOrNull() ?: 0
        val maxValue = initialPositions.maxOrNull() ?: 0
        return (minValue..maxValue).minOfOrNull { checkedPosition ->
            initialPositions.sumOf { abs(it - checkedPosition) }
        } ?: 0
    }
}

class Day07Part2 : CommonTask<List<Int>, Int>(
    dayNum = 7,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 168,
    taskResult = 101079875,
) {

    override fun calculateResult(initialPositions: List<Int>): Int {
        val minValue = initialPositions.minOrNull() ?: 0
        val maxValue = initialPositions.maxOrNull() ?: 0
        return (minValue..maxValue).minOfOrNull { checkedPosition ->
            initialPositions.sumOf { sumOfArithmeticProgression(it, checkedPosition) }
        } ?: 0
    }

    private fun sumOfArithmeticProgression(start: Int, end: Int): Int =
        (1..abs(start - end)).fold(0){ acc, n -> acc + n }

}

private val inputConverter: (String) -> List<Int> = { s ->
    s.trim().split(",").map { it.toInt() }
}

private val example = "16,1,2,0,4,2,7,1,2,14"
