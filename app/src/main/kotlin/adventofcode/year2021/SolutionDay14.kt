package adventofcode.year2021

import adventofcode.common.CommonPartTest
import adventofcode.common.Day

private object Day14 : Day<Day14.Input> {
    override val dayNum = 14
    override val yearNum = 2021
    override fun inputConverter(input: String): Input =
        input.trim().lines().partition { it.contains(" -> ") }
            .let { (insertions, template) ->
                Input(
                    template[0],
                    insertions.map { it.split(" -> ") }.map { Insertion(it[0][0] to it[0][1], it[1][0]) })
            }

    class Day14Part1 : CommonPartTest<Input>(
        day = this,
        exampleResult = "1588",
        taskResult = "3259",
    ) {
        override fun calculateResult(input: Input): String = calculateResult(input, 10)
    }

    class Day14Part2 : CommonPartTest<Input>(
        day = this,
        exampleResult = "2188189693529",
        taskResult = "3459174981021",
    ) {
        override fun calculateResult(input: Input): String = calculateResult(input, 40)
    }

    fun calculateResult(input: Input, steps: Int): String {
        var freqMap =
            input.polymerTemplate.zipWithNext()
                .groupingBy { it }
                .eachCount()
                .mapValues { it.value.toLong() }
                .toMutableMap()

        repeat(steps) {
            val newFreqMap = mutableMapOf<Pair<Char, Char>, Long>()
            for (pair in freqMap.keys) {
                val (first, second) = input.insertions.find { it.pair == pair }?.afterInsertion()!!
                newFreqMap[first] = newFreqMap.getOrDefault(first, 0) + freqMap[pair]!!
                newFreqMap[second] = newFreqMap.getOrDefault(second, 0) + freqMap[pair]!!
            }
            freqMap = newFreqMap
        }

        return freqMap
            .map { it.key.first to it.value }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() + if (it.key == input.lastChar) 1 else 0 }
            .values.sorted().let { it.last() - it.first() }.toString()
    }

    data class Input(val polymerTemplate: String, val insertions: List<Insertion>, val lastChar: Char = polymerTemplate.last())

    data class Insertion(val pair: Pair<Char, Char>, val toInsert: Char) {
        fun afterInsertion() = listOf(pair.first to toInsert, toInsert to pair.second)
    }

    override val example = """
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C
    """.trimIndent()
}
