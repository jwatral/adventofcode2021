package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day

private object Day03 : Day<List<String>> {
    override val dayNum = 3
    override fun inputConverter(input: String) = input.trim().lines()

    class Day03Part1 : CommonPartTest<List<String>>(
        day = this,
        exampleResult = "198",
        taskResult = "4191876",
    ) {
        override fun calculateResult(input: List<String>): String {
            val size = input.size
            val emptyCounter = List(input.first().length) { 0 }
            val gamma =
                input.fold(emptyCounter) { acc, s ->
                    acc.mapIndexed { index, i ->
                        i + s[index].toString().toInt()
                    }
                }.joinToString(separator = "") { if (2 * it > size) "1" else "0" }

            val epsilon = gamma.map { if (it == '1') "0" else "1" }.joinToString(separator = "")
            return (gamma.binaryStringToInt() * epsilon.binaryStringToInt()).toString()
        }
    }

    class Day03Part2 : CommonPartTest<List<String>>(
        day = this,
        exampleResult = "230",
        taskResult = "3414905",
    ) {
        override fun calculateResult(input: List<String>): String {
            val oxygenGeneratorRating = filterByMostCommonBitOnPosition(input).let { it.first().binaryStringToInt() }
            val co2ScrubberRating = filterByLeastCommonBitOnPosition(input).let { it.first().binaryStringToInt() }
            return (co2ScrubberRating * oxygenGeneratorRating).toString()
        }

        private fun filterByMostCommonBitOnPosition(input: List<String>, position: Int = 0): List<String> {
            val numOfOnes = input.count { it[position] == '1' }
            val mostCommonBit = if(2 * numOfOnes >= input.size) '1' else '0'
            val result = input.filter { it[position] == mostCommonBit }
            return if (result.size == 1) result else filterByMostCommonBitOnPosition(result, position + 1)
        }

        private fun filterByLeastCommonBitOnPosition(input: List<String>, position: Int = 0): List<String> {
            val numOfOnes = input.count { it[position] == '1' }
            val leastCommonBit = if(2 * numOfOnes >= input.size) '0' else '1'
            val result = input.filter { it[position] == leastCommonBit }
            return if (result.size == 1) result else filterByLeastCommonBitOnPosition(result, position + 1)
        }
    }

    private fun String.binaryStringToInt(): Int = Integer.parseInt(this, 2)

    override val example = """
    00100
    11110
    10110
    10111
    10101
    01111
    00111
    11100
    10000
    11001
    00010
    01010
""".trimIndent()
}
