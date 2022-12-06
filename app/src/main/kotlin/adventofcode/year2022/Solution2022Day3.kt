package adventofcode.year2022

private object Solution2022Day3 : Day2022<List<Solution2022Day3.Rucksack>>(dayNum = 3) {
    override fun inputConverter(input: String): List<Rucksack> =
        input.trim().lines()
            .map { it.substring(0 until it.length/2) to it.substring((it.length/2))}
            .map { Rucksack(it.first.toCharArray().toSet(), it.second.toCharArray().toSet()) }

    class Day2Part1 : CommonPartTest(
        exampleResult = "157",
        taskResult = "7817",
    ) {
        override fun calculateResult(input: List<Rucksack>): String =
            input.map { it.common().first() }.sumOf { it.toValue() }.toString()
    }

    class Day2Part2 : CommonPartTest(
        exampleResult = "70",
        taskResult = "2444",
    ) {
        override fun calculateResult(input: List<Rucksack>): String =
            input.windowed(3, 3)
                .map { it[0].commonWithOther(it[1]).intersect(it[2].allItems()).first() }
                .sumOf { it.toValue() }
                .toString()
    }

    data class Rucksack(val compartment1: Set<Char>, val compartment2: Set<Char>) {
        fun common() = compartment1.intersect(compartment2)

        fun allItems() = compartment1 + compartment2

        fun commonWithOther(other: Rucksack) = allItems().intersect(other.allItems())
    }

    fun Char.toValue() = if(this.isUpperCase()) this.code - 38 else this.code - 96

    override val example =
        """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()
}
