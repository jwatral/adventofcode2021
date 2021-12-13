package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day
import java.util.*

private object Day10 : Day<List<String>> {
    override val dayNum = 10
    override fun inputConverter(input: String) =
        input.trim().lines()

    class Day10Part1 : CommonPartTest<List<String>>(
        day = this,
        exampleResult = "26397",
        taskResult = "215229",
    ) {
        override fun calculateResult(input: List<String>): String  {
            return input.mapNotNull { findFirstIllegalCharacter(it) }
                .mapNotNull { pointsForIllegalChar[it] }
                .sum()
                .toString()
        }

        private fun findFirstIllegalCharacter(string: String): Char? {
            val stack = Stack<Bracket>()
            string.mapIndexed(::Bracket)
                .forEach { bracket ->
                    if(matchingBrackets.keys.contains(bracket.char)) stack.push(bracket)
                    else {
                        if(stack.peek().matches(bracket)) stack.pop()
                        else return bracket.char
                    }
                }
            return null
        }

        private val pointsForIllegalChar = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )
    }

    class Day10Part2 : CommonPartTest<List<String>>(
        day = this,
        exampleResult = "288957",
        taskResult = "1105996483",
    ) {
        override fun calculateResult(input: List<String>): String =
            input.mapNotNull { findUnmatchedCharacters(it) }
                .map { it.mapNotNull { matchingBrackets[it.char] } }
                .map { it.reversed().fold(0L) {acc, c -> (acc * 5) + (pointsForUnmatchedChar[c] ?: 0) } }
                .sorted()
                .let { it.elementAt(it.size/2) }
                .toString()

        private fun findUnmatchedCharacters(string: String): List<Bracket>? {
            val stack = Stack<Bracket>()
            string.mapIndexed(::Bracket)
                .forEach { bracket ->
                    if(matchingBrackets.keys.contains(bracket.char)) stack.push(bracket)
                    else {
                        if(stack.peek().matches(bracket)) stack.pop()
                        else return null
                    }
                }
            return stack.toList()
        }

        private val pointsForUnmatchedChar = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )
    }

    private val matchingBrackets = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )

    private data class Bracket(val location: Int, val char: Char) {
        fun matches(other: Bracket): Boolean = matchingBrackets[char] == other.char
    }

    override val example = """
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent()
}
