package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day

private typealias Board = List<List<Int>>
private object Day04 : Day<Day04.Input> {
    override val dayNum = 4

    override fun inputConverter(input: String):  Input {
        val sections = input.trim().split("\n\n")
        val numbers = sections.first().split(",").map { it.toInt() }
        val boards = sections.drop(1).map { it.lines() }
            .map { it.map { it.split(" ").filter { it.isNotBlank() }.map { it.toInt() } } }
        return Input(numbers, boards)
    }

    class Day04Part1 : CommonPartTest<Input>(
        day = this,
        exampleResult = "4512",
        taskResult = "35670",
    ) {

        override fun calculateResult(input: Input): String {
            val flattenBoards = input.boards.map { it.allRowsAndColumns() }
            val (winningBoardIndex, sequence) = findWinningBoardAndSequence(input.numbers, flattenBoards)
            val sumOfUnmarkedNumbers = input.boards[winningBoardIndex].flatten().filterNot { sequence.contains(it) }.sum()
            return (sumOfUnmarkedNumbers * sequence.last()).toString()
        }

        private fun findWinningBoardAndSequence(numbers: List<Int>, flattenBoards: List<List<List<Int>>>, position: Int = 5): Pair<Int, List<Int>> {
            val sequence = numbers.take(position)
            flattenBoards.forEachIndexed { index, lists ->
                if(lists.any { sequence.containsAll(it) }) return (index to sequence)
            }
            return findWinningBoardAndSequence(numbers, flattenBoards, position + 1)
        }
    }

    class Day04Part2 : CommonPartTest<Input>(
        day = this,
        exampleResult = "1924",
        taskResult = "22704",
    ) {

        override fun calculateResult(input: Input): String {
            val flattenBoards = input.boards.map { it.allRowsAndColumns() }
            val (loosingBoardIndex, sequence) = findLoosingBoardAndSequence(input.numbers, flattenBoards)
            val sumOfUnmarkedNumbers = input.boards[loosingBoardIndex].flatten().filterNot { sequence.contains(it) }.sum()
            val result = sumOfUnmarkedNumbers * sequence.last()
            return result.toString()
        }

        private fun findLoosingBoardAndSequence(numbers: List<Int>, flattenBoards: List<List<List<Int>>>, position: Int = 5, winningBoardsAndSequences: MutableList<Pair<Int, List<Int>>> = mutableListOf()): Pair<Int, List<Int>> {
            val sequence = numbers.take(position)
            flattenBoards.forEachIndexed { index, lists ->
                if(lists.any { sequence.containsAll(it) } && !winningBoardsAndSequences.map {it.first}.contains(index)) winningBoardsAndSequences.add((index to sequence))
            }
            return if(position + 1 == numbers.size) winningBoardsAndSequences.last()
            else findLoosingBoardAndSequence(numbers, flattenBoards, position + 1, winningBoardsAndSequences)
        }
    }


    private fun Board.allRowsAndColumns(): List<List<Int>> =
        this + this.fold(List(5) { mutableListOf<Int>() }) { acc, ints -> ints.mapIndexed { index, i -> acc[index].add(i) }; acc }

    data class Input(val numbers: List<Int>, val boards: List<Board>)

    override val example = """
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7
""".trimIndent()
}
