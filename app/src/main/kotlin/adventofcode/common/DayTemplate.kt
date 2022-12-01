package adventofcode.common

private object Day99 : Day<List<String>> {
    override val dayNum = 99
    override val yearNum = 2021
    override fun inputConverter(input: String) =
        input.trim().lines()

    class Day99Part1 : CommonPartTest<List<String>>(
        day = this,
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<String>): String = TODO()
    }

    class Day99Part2 : CommonPartTest<List<String>>(
        day = this,
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
