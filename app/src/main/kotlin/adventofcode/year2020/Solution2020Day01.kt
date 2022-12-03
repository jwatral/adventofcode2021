package adventofcode.year2020

private object Solution2020Day01 : Day2020<List<Int>>(dayNum = 99) {
    override fun inputConverter(input: String) =
        input.trim().lines().mapNotNull { it.toIntOrNull() }

    class Day99Part1 : CommonPartTest(
        exampleResult = "514579",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<Int>): String = TODO()
    }

    class Day99Part2 : CommonPartTest(
        exampleResult = "42",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<Int>): String = TODO()
    }

    override val example = """
        1721
        979
        366
        299
        675
        1456
    """.trimIndent()
}
