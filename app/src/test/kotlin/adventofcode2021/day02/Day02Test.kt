package adventofcode2021.day02

import adventofcode2021.common.getInputForDay
import adventofcode2021.day02.Instruction.Companion.toInstruction
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day02Test {

    private val exampleInput: List<Instruction> = example.lines().map { it.toInstruction() }
    private val taskInput = getInputForDay(2).trim().lines().map { it.toInstruction() }

    @Test
    fun examplePart1() {
        val result = SolutionDay02.calculatePart1(exampleInput)
        assertEquals(150, result)
    }

    @Test
    fun examplePart2() {
        val result = SolutionDay02.calculatePart2(exampleInput)
        assertEquals(900, result)
    }

    @Test
    fun taskPart1() {
        val result = SolutionDay02.calculatePart1(taskInput)
        assertEquals(2272262, result)
    }

    @Test
    fun taskPart2() {
        val result = SolutionDay02.calculatePart2(taskInput)
        assertEquals(2134882034, result)
    }
}

val example = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
""".trimIndent()
