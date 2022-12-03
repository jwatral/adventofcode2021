package adventofcode.year2021

object Day01 : Day2021<List<Int>>(dayNum = 1) {
    override fun inputConverter(input: String) = input.trim().lines().map { it.toInt() }

    class Day01Part1 : CommonPartTest(
        exampleResult = "7",
        taskResult = "1400",
    ) {
        override fun calculateResult(input: List<Int>) = input.zipWithNext { a, b -> b > a }.count { it }.toString()
    }

    class Day01Part2 : CommonPartTest(
        exampleResult = "5",
        taskResult = "1429",
    ) {
        override fun calculateResult(input: List<Int>) =
            input.windowed(3).map { it.sum() }.zipWithNext { a, b -> b > a }.count { it }.toString()
    }

    override val example = """
199
200
208
210
200
207
240
269
260
263
""".trim()

}
