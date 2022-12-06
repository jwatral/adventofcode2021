package adventofcode.year2022

import java.util.*

private object Solution2022Day5 : Day2022<Solution2022Day5.Stacks>(dayNum = 5) {
    override fun inputConverter(input: String): Stacks {
        val lines = input.lines()
        val numOfStacks = (lines.first().length + 1) / 4
        val stacks = (1..numOfStacks).map { Stack<Char>() }
        val stackLines = lines.filter { it.contains("[") }.reversed()
        stackLines.forEach { line ->
            " $line".windowed(4, 4).map { it[2] }.forEachIndexed { i, c -> if (c != ' ') stacks[i].push(c) }
        }
        val actions = lines.filter { it.startsWith("move") }.map { it.split(" ") }
            .map { Action(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1) }
        return Stacks(stacks, actions)
    }

    class Part1 : CommonPartTest(
        exampleResult = "CMZ",
        taskResult = "TPGVQPFDH",
    ) {
        override fun calculateResult(input: Stacks): String {
            input.performAllActionsPart1()
            return input.stacks.joinToString("") { it.pop().toString() }
        }
    }

    class Part2 : CommonPartTest(
        exampleResult = "MCD",
        taskResult = "DMRDFRHHH",
    ) {
        override fun calculateResult(input: Stacks): String {
            input.performAllActionsPart2()
            return input.stacks.joinToString("") { it.pop().toString() }
        }
    }

    data class Stacks(val stacks: List<Stack<Char>>, val actions: List<Action>) {
        fun performAllActionsPart1() = actions.forEach { (howMany, fromIndex, toIndex) ->
            repeat(howMany) {
                stacks[toIndex].push(stacks[fromIndex].pop())
            }
        }

        fun performAllActionsPart2() = actions.forEach { (howMany, fromIndex, toIndex) ->
            val crates = mutableListOf<Char>()
            repeat(howMany) { crates.add(stacks[fromIndex].pop()) }
            crates.reversed().forEach { stacks[toIndex].push(it) }
        }
    }

    data class Action(val howMany: Int, val fromIndex: Int, val toIndex: Int)

    override val example =
"""
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
""".trimIndent()
}
