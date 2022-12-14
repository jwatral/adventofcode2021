package adventofcode.year2022

import adventofcode.common.Point
import adventofcode.common.isAdjacent
import adventofcode.common.moveDown
import adventofcode.common.moveLeft
import adventofcode.common.moveRight
import adventofcode.common.moveUp

private object Solution2022Day9 : Day2022<List<Solution2022Day9.Operation>>(dayNum = 9) {
    override fun inputConverter(input: String): List<Operation> =
        input.trim().lines().map { it.split(" ") }.map { (direction, numOfSteps) -> Operation(direction, numOfSteps.toInt()) }

    class Part1 : CommonPartTest(
        exampleResult = "13",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: List<Operation>): String = run(input).toSet().count().toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "TODO",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: List<Operation>): String = TODO()
    }

    data class Operation(val direction: String, val numOfSteps: Int)

    fun run(operations: List<Operation>): MutableList<Point> {
        var head = Point(100, 100)
        var tail = Point(100, 100)
        var previousHeadPosition = head
        val pointsVisitedByTail: MutableList<Point> = mutableListOf(tail)

        fun moveTail() {
            if(!tail.isAdjacent(head) && tail != head) {
                tail = previousHeadPosition
                pointsVisitedByTail.add(tail)
            }
        }

        fun moveHead(direction: String) {
            previousHeadPosition = head
            when(direction) {
                "L" -> head = head.moveLeft()
                "R" -> head = head.moveRight()
                "U" -> head = head.moveUp()
                "D" -> head = head.moveDown()
            }
        }

        operations.forEach { (direction, numOfSteps) -> repeat(numOfSteps) { moveHead(direction); moveTail() }  }
        return pointsVisitedByTail
    }

    // @formatter:off
    override val example =
"""
R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
""".trimIndent()
    // @formatter:on
}
