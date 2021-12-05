package adventofcode2021

import adventofcode2021.common.CommonTask


class Day01Part1 : CommonTask<List<Int>, Int>(
    dayNum = 1,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 7,
    taskResult = 1400,
) {

    override fun calculateResult(input: List<Int>): Int = input.zipWithNext { a, b -> b > a }.count { it }
}

class Day01Part2 : CommonTask<List<Int>, Int>(
    dayNum = 1,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 5,
    taskResult = 1429,
) {

    override fun calculateResult(input: List<Int>): Int =
        input.windowed(3).map { it.sum() }.zipWithNext { a, b -> b > a }.count { it }
}

private val inputConverter: (String) -> List<Int> = { stringInput -> stringInput.trim().lines().map { it.toInt() } }

private val example: String = """
199
200
208
210
200
207
240
269
260
263
""".trim()
