package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day

private object Day14 : Day<Day14.Input> {
    override val dayNum = 14
    override fun inputConverter(input: String): Input =
        input.trim().lines().partition { it.contains(" -> ") }
        .let { (insertions, template) ->
            Input(template[0], insertions.map { it.split(" -> ") }.map{ Insertion(it[0][0] to it[0][1], it[1][0]) })
        }

    class Day14Part1 : CommonPartTest<Input>(
        day = this,
        exampleResult = "1588",
        taskResult = "3259",
    ) {
        override fun calculateResult(input: Input): String =
            (1..10).fold(input.polymerTemplate.toCharArray().toList()) { acc, _ -> step(acc, input.insertions) }
                .groupBy { it }
                .let {
                    val sorted = it.values.map { it.size }.sorted()
                    sorted.last() - sorted.first()
                }.toString()

        private fun step(input: List<Char>, insertions: List<Insertion>): List<Char> =
            input.zipWithNext().mapIndexed { index, pair -> insertions.find { it.pair == pair }?.afterInsertion(index) ?: pair.toList() }.flatten()
    }

    class Day14Part2 : CommonPartTest<Input>(
        day = this,
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: Input): String = TODO()
    }

    data class Input(val polymerTemplate: String, val insertions: List<Insertion>)

    data class Insertion(val pair: Pair<Char, Char>, val toInsert: Char) {
        fun afterInsertion(index: Int) = when(index) {
            0 -> listOf(pair.first, toInsert, pair.second)
            else -> listOf(toInsert, pair.second)
        }
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
