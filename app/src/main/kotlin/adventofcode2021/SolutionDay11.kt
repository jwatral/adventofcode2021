package adventofcode2021

import adventofcode2021.common.CommonPartTest
import adventofcode2021.common.Day

private object Day11 : Day<List<Day11.Octopus>> {
    override val dayNum = 11
    override fun inputConverter(input: String) =
        input.trim().lines().mapIndexed { y, row ->
            row.mapIndexed { x, initialEnergyLvl -> Octopus(x, y, initialEnergyLvl.toString().toInt()) }
        }.flatten()

    class Day11Part1 : CommonPartTest<List<Octopus>>(
        day = this,
        exampleResult = "1656",
        taskResult = "1601",
    ) {
        override fun calculateResult(input: List<Octopus>): String {
            var flashes = 0
            repeat(100) {
                step(input)
                flashes += input.count { it.alreadyFlashed }
            }
            return flashes.toString()
        }
    }

    private fun step(input: List<Octopus>) {
        input.forEach { it.energyLvl++; it.alreadyFlashed = false }
        var aboutToFlash = input.filter { it.energyLvl == 10 }
        while (aboutToFlash.isNotEmpty()) {
            aboutToFlash.forEach { it.allAdjacentOctopuses(input).forEach { it.energyLvl++ }; it.alreadyFlashed = true }
            aboutToFlash = input.filter { it.energyLvl > 9 && !it.alreadyFlashed }
        }
        input.filter { it.energyLvl > 9 }.forEach { it.energyLvl = 0 }
    }

    class Day11Part2 : CommonPartTest<List<Octopus>>(
        day = this,
        exampleResult = "195",
        taskResult = "368",
    ) {
        override fun calculateResult(input: List<Octopus>): String {
            var step = 1
            while(true) {
                step(input)
                if(input.count { it.alreadyFlashed } == 100) return step.toString()
                step++
            }
        }
    }

    fun List<Octopus>.print() = this.chunked(10).joinToString("\n") { it.map(Octopus::energyLvl).joinToString("") }

    class Octopus(val x: Int, val y: Int, var energyLvl: Int, var alreadyFlashed: Boolean = false) {
        fun allAdjacentOctopuses(octopuses: List<Octopus>): List<Octopus> =
            octopuses.filter {
                (it.x == x - 1 && it.y == y) ||
                (it.x == x + 1 && it.y == y) ||
                (it.x == x && it.y == y - 1) ||
                (it.x == x && it.y == y + 1) ||
                (it.x == x + 1 && it.y == y + 1) ||
                (it.x == x - 1 && it.y == y - 1) ||
                (it.x == x + 1 && it.y == y - 1) ||
                (it.x == x - 1 && it.y == y + 1)
            }
    }

    override val example = """
5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526
    """.trimIndent()
}
