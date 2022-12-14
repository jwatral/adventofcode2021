package adventofcode.year2020

private object Solution2020Day01 : Day2020<List<Int>>(dayNum = 1) {
    override fun inputConverter(input: String) =
        input.trim().lines().mapNotNull { it.toIntOrNull() }

    class Part1 : CommonPartTest(
        exampleResult = "514579",
        taskResult = "1014624",
    ) {
        override fun calculateResult(input: List<Int>): String {
            input.forEachIndexed { x, valueX ->
                input.forEachIndexed { y, valueY ->
                    if (x != y && valueX + valueY == 2020) return (valueX * valueY).toString()
                }
            }
            return ""
        }
    }

    fun <T> permutations(list: List<T>, num: Int): Sequence<T> = TODO()

    class Part2 : CommonPartTest(
        exampleResult = "241861950",
        taskResult = "42",
    ) {
        override fun calculateResult(input: List<Int>): String {
            input.forEachIndexed { x, valueX ->
                input.forEachIndexed { y, valueY ->
                    if (x != y) {
                        input.forEachIndexed { z, valueZ ->
                            if (z != y && z != x && valueX + valueY + valueZ == 2020) return (valueX * valueY * valueZ).toString()
                        }
                    }
                }
            }
            return ""
        }
    }

    // @formatter:off
    override val example =
"""
1721
979
366
299
675
1456
""".trimIndent()
// @formatter:on
}
