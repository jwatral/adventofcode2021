package adventofcode.year2024

private object Solution2024Day3 : Day2024<String>(dayNum = 3) {
    override fun inputConverter(input: String): String =
        input.trim()

    class Part1 : CommonPartTest(
        exampleResult = "161",
        taskResult = "465",
    ) {
        override fun calculateResult(input: String): String =
            findMultiplications(input)
                .sumOf { it.first * it.second }.toString()
    }

    private fun findMultiplications(input: String) = regex.findAll(input)
        .map { it.groupValues[1].toInt() to it.groupValues[2].toInt() }.toList()

    class Part2 : CommonPartTest(
        exampleResult = "48",
        taskResult = "89349241",
    ) {
        override fun calculateResult(input: String): String =
            input.split("do()")
                .also { println(it) }
                .map { it.substringBefore("don't()") }
                .flatMap { findMultiplications(it) }
                .sumOf { it.first * it.second }.toString()

    }

    val regex = Regex("mul\\((\\d+),(\\d+)\\)")

    // @formatter:off
    override val example =
"""
xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
""".trimIndent()
    // @formatter:on
}
