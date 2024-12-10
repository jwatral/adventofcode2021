package adventofcode.year2024

private object Solution2024Day7 : Day2024<List<Solution2024Day7.Input>>(dayNum = 7) {
    override fun inputConverter(input: String): List<Input> =
        input.trim().lines().map { it.split(":") }
            .map { Input(it[0].toLong(), it[1].trim().split(" ").map { it.toLong() }) }

    class Part1 : CommonPartTest(
        exampleResult = "3749",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: List<Input>): String =
            input.map { i -> calculatePermutations(i.numbers, listOf('+', '*')).any { i.result == it } to i.result }
                .filter { it.first }
                .sumOf { it.second }
                .toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "11387",
        taskResult = "271691107779347",
    ) {
        override fun calculateResult(input: List<Input>): String =
            input.map { i -> calculatePermutations(i.numbers, listOf('+', '*', '|')).any { i.result == it } to i.result }
                .filter { it.first }
                .sumOf { it.second }
                .toString()
    }

    fun calculatePermutations(numbers: List<Long>, operators: List<Char>): List<Long> {
        val numOperators = numbers.size - 1
        val operatorCombinations = generateOperatorCombinations(operators, numOperators)
        return operatorCombinations.map { buildExpression(numbers, it) }
            .map { evaluateExpression(it) }
    }

    fun generateOperatorCombinations(operators: List<Char>, numOperators: Int): List<List<Char>> {
        val result = mutableListOf<List<Char>>()
        generateOperatorCombinationsRecursive(operators, numOperators, mutableListOf(), result)
        return result
    }

    fun generateOperatorCombinationsRecursive(
        operators: List<Char>,
        numOperators: Int,
        current: MutableList<Char>,
        result: MutableList<List<Char>>
    ) {
        if (current.size == numOperators) {
            result.add(ArrayList(current))  // Add a copy of the current combination
            return
        }

        for (operator in operators) {
            current.add(operator)
            generateOperatorCombinationsRecursive(operators, numOperators, current, result)
            current.removeAt(current.size - 1)
        }
    }

    // Function to build the expression from numbers and operators
    fun buildExpression(numbers: List<Long>, operators: List<Char>): String {
        val sb = StringBuilder()
        for (i in numbers.indices) {
            sb.append(numbers[i])
            if (i < operators.size) {
                sb.append(" ${operators[i]} ")
            }
        }
        return sb.toString()
    }

    fun evaluateExpression(expression: String): Long {
        val tokens = expression.split(" ")
        var result = tokens[0].toLong()

        for (i in 1 until tokens.size step 2) {
            val operator = tokens[i]
            val operand = tokens[i + 1].toLong()

            result = when (operator) {
                "+" -> result + operand
                "*" -> result * operand
                "|" -> "$result$operand".toLong()
                else -> result
            }
        }

        return result
    }

    data class Input(
        val result: Long,
        val numbers: List<Long>,
    )

    // @formatter:off
    override val example =
"""
190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
""".trimIndent()
    // @formatter:on
}
