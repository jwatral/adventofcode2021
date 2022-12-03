package adventofcode.common

private object Day99 : Day<List<String>>(dayNum = 99, yearNum = 2021) {
    override fun inputConverter(input: String) =
        input.trim().lines()

    class Day99Part1 : CommonPartTest(
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<String>): String = TODO()
    }

    class Day99Part2 : CommonPartTest(
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<String>): String = TODO()
    }

    override val example = """
        some
        multiline
        example
    """.trimIndent()
}
