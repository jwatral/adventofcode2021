package adventofcode2021.day01

import adventofcode2021.common.getInputForDay
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {
    private val exampleInput = example.lines().map { it.toInt() }
    private val taskInput = getInputForDay(1).lines().mapNotNull { it.toIntOrNull() }

    @Test
    fun examplePart1() {
        val result = SolutionDay01.calculatePart1(exampleInput)
        assertEquals(7, result)
    }

    @Test
    fun examplePart2() {
        val result = SolutionDay01.calculatePart2(exampleInput)
        assertEquals(5, result)
    }

    @Test
    fun taskPart1() {
        val result = SolutionDay01.calculatePart1(taskInput)
        assertEquals(1400, result)
    }

    @Test
    fun taskPart2() {
        val result = SolutionDay01.calculatePart2(taskInput)
        assertEquals(1429, result)
    }
}

val example = """
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
