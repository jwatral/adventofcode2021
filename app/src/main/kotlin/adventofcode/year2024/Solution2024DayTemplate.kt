package adventofcode.year2024

private object Solution2024DayTemplate : Day2024<List<Nothing>>(dayNum = TODO()) {
    override fun inputConverter(input: String): List<Nothing> =
        input.trim().lines().map { } as Nothing

    class Part1 : CommonPartTest(
        exampleResult = "TODO",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: List<Nothing>): String = TODO()
    }

    class Part2 : CommonPartTest(
        exampleResult = "TODO",
        taskResult = "TODO",
    ) {
        override fun calculateResult(input: List<Nothing>): String = TODO()
    }

    // @formatter:off
    override val example =
"""
TODO
""".trimIndent()
    // @formatter:on
}
