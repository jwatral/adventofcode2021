package adventofcode.year2022

private object Solution2022DayTemplate : Day2022<List<Nothing>>(dayNum = TODO()) {
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

    override val example =
"""
TODO
""".trimIndent()
}
