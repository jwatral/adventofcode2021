package adventofcode2021

import adventofcode2021.common.CommonTask
import java.util.*

class Day10Part1 : CommonTask<List<String>, Int>(
    dayNum = 10,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 26397,
    taskResult = 215229,
) {

    override fun calculateResult(input: List<String>): Int {
        return input.mapNotNull { findFirstIllegalCharacter(it) }
            .mapNotNull { pointsForIllegalChar[it] }
            .sum()
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
}

private val pointsForIllegalChar = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137,
)

private val matchingBrackets = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)

private data class Bracket(val location: Int, val char: Char) {
    fun matches(other: Bracket): Boolean = matchingBrackets[char] == other.char
}

private val inputConverter: (String) -> List<String> = { s -> s.trim().lines() }

private val example = """
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
