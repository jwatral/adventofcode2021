package adventofcode.year2022

private object Solution2022Day4 : Day2022<List<Solution2022Day4.ElfPair>>(dayNum = 4) {
    override fun inputConverter(input: String): List<ElfPair> =
        input.trim().lines().map { it.split(",") }
            .map { (first, second) -> ElfPair(first.toRange(), second.toRange()) }

    class Part1 : CommonPartTest(
        exampleResult = "2",
        taskResult = "500",
    ) {
        override fun calculateResult(input: List<ElfPair>): String = input.count { it.fullyOverlaps() }.toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "4",
        taskResult = "815",
    ) {
        override fun calculateResult(input: List<ElfPair>): String = input.count { it.overlaps() }.toString()
    }

    data class ElfPair(val firstRange: IntRange, val secondRange: IntRange) {

        fun overlaps() = firstRange.intersect(secondRange).isNotEmpty()
        fun fullyOverlaps() = intersectSize == firstRange.toSet().size || intersectSize == secondRange.toSet().size

        val intersectSize = firstRange.intersect(secondRange).size
    }

    fun String.toRange() = this.split("-").map { it.toInt() }.let { (start, end) -> start..end }

    override val example =
"""
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
""".trimIndent()
}
