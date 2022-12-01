package adventofcode.year2021

import adventofcode.common.CommonPartTest
import adventofcode.common.Day
import adventofcode.common.Grid
import adventofcode.common.PointCoordinates
import adventofcode.common.allAdjacentPoints

private object Day11 : Day<Grid<Day11.Octopus>> {
    override val dayNum = 11
    override val yearNum = 2021
    override fun inputConverter(input: String) =
        input.trim().lines().mapIndexed { y, row ->
            row.mapIndexed { x, initialEnergyLvl -> Octopus(x, y, initialEnergyLvl.toString().toInt()) }
        }.flatten()

    class Day11Part1 : CommonPartTest<Grid<Octopus>>(
        day = this,
        exampleResult = "1656",
        taskResult = "1601",
    ) {
        override fun calculateResult(input: Grid<Octopus>): String {
            var flashes = 0
            repeat(100) {
                step(input)
                flashes += input.count { it.alreadyFlashed }
            }
            return flashes.toString()
        }
    }

    private fun step(input: Grid<Octopus>) {
        input.forEach { it.energyLvl++; it.alreadyFlashed = false }
        var aboutToFlash = input.filter { it.energyLvl == 10 }
        while (aboutToFlash.isNotEmpty()) {
            aboutToFlash.forEach { input.allAdjacentPoints(it).forEach { it.energyLvl++ }; it.alreadyFlashed = true }
            aboutToFlash = input.filter { it.energyLvl > 9 && !it.alreadyFlashed }
        }
        input.filter { it.energyLvl > 9 }.forEach { it.energyLvl = 0 }
    }

    class Day11Part2 : CommonPartTest<Grid<Octopus>>(
        day = this,
        exampleResult = "195",
        taskResult = "368",
    ) {
        override fun calculateResult(input: Grid<Octopus>): String {
            var step = 1
            while (true) {
                step(input)
                if (input.count { it.alreadyFlashed } == 100) return step.toString()
                step++
            }
        }
    }

    fun Grid<Octopus>.print() = this.chunked(10).joinToString("\n") { it.map(Octopus::energyLvl).joinToString("") }

    class Octopus(override val x: Int, override val y: Int, var energyLvl: Int, var alreadyFlashed: Boolean = false) : PointCoordinates

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
