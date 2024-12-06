package adventofcode.year2024

import java.util.*

private object Solution2024Day5 : Day2024<Solution2024Day5.Input>(dayNum = 5) {
    override fun inputConverter(input: String): Input {
        val groups = input.trim().split("\n\n")
        val orderingRules = groups[0].lines().map { it.split("|").let { it[0].toInt() to it[1].toInt() } }
        val pages = groups[1].lines().map { it.split(",").map { it.toInt() } }
        return Input(orderingRules, pages)
    }

    class Part1 : CommonPartTest(
        exampleResult = "143",
        taskResult = "5329",
    ) {
        override fun calculateResult(input: Input): String =
            input.pages.filter { it.isSorted(input.orderingRules) }
                .sumOf { it.get(it.size / 2) }
                .toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "123",
        taskResult = "5833",
    ) {
        override fun calculateResult(input: Input): String =
            input.pages.filter { !it.isSorted(input.orderingRules) }
                .map { topologicalSortWithConstraints(it, input.orderingRules) }
                .sumOf { it.get(it.size / 2) }
                .toString()
    }

    data class Input(
        val orderingRules: List<Pair<Int, Int>>,
        val pages: List<List<Int>>,
    )

    // @formatter:off
    override val example =
"""
47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
""".trimIndent()
    // @formatter:on
}

private fun List<Int>.isSorted(orderingRules: List<Pair<Int, Int>>): Boolean {
    val indexMap = this.withIndex().associate { it.value to it.index }

    for ((a, b) in orderingRules) {
        val indexA = indexMap[a]
        val indexB = indexMap[b]

        if (indexA != null && indexB != null && indexA > indexB) {
            return false
        }
    }
    return true
}

fun topologicalSortWithConstraints(list: List<Int>, constraints: List<Pair<Int, Int>>): List<Int> {
    val graph = mutableMapOf<Int, MutableList<Int>>()
    val inDegree = mutableMapOf<Int, Int>()

    list.forEach { num ->
        graph[num] = mutableListOf()
        inDegree[num] = 0
    }

    for ((a, b) in constraints) {
        if (a in inDegree && b in inDegree) {
            graph[a]?.add(b)
            inDegree[b] = inDegree.getOrDefault(b, 0) + 1
        }
    }

    val queue = LinkedList<Int>()
    val sorted = mutableListOf<Int>()

    inDegree.forEach { (node, degree) ->
        if (degree == 0) queue.add(node)
    }

    while (queue.isNotEmpty()) {
        val node = queue.poll()
        sorted.add(node)

        graph[node]?.forEach { neighbor ->
            inDegree[neighbor] = inDegree.getOrDefault(neighbor, 0) - 1
            if (inDegree[neighbor] == 0) {
                queue.add(neighbor)
            }
        }
    }

    if (sorted.size != list.size) {
        throw IllegalArgumentException("Cycle detected!")
    }

    return sorted
}

