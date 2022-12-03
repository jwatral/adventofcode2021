package adventofcode.year2022

private object Solution2022Day1 : Day2022<List<Solution2022Day1.Elf>>(dayNum = 1) {
    override fun inputConverter(input: String): List<Elf> {
        val lines = input.trim().lines().map { it.toIntOrNull() }
        val elves = mutableListOf(newElf())
        lines.forEach {
            if(it == null) elves.add(newElf())
            else elves.last().food.add(it)
        }
        return elves
    }

    class Day1Part1 : CommonPartTest(
        exampleResult = "24000",
        taskResult = "66616",
    ) {
        override fun calculateResult(input: List<Elf>): String = input.maxOfOrNull { it.food.sum() }.toString()
    }

    class Day1Part2 : CommonPartTest(
        exampleResult = "45000",
        taskResult = "199172",
    ) {
        override fun calculateResult(input: List<Elf>): String =
            input.map { it.food.sum() }.sortedDescending().take(3).sum().toString()
    }

    data class Elf(val food: MutableList<Int>)

    private fun newElf() = Elf(mutableListOf())


    override val example =
"""1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
""".trimIndent()
}
