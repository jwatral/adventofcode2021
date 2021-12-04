package adventofcode2021.day02

import adventofcode2021.CommonTask
import adventofcode2021.day02.Instruction.Companion.toInstruction


internal class Day02Part1 : CommonTask<List<Instruction>, Int>(
    dayNum = 2,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 150,
    taskResult = 2272262,
) {

    override fun calculateResult(instructions: List<Instruction>): Int {
        val horizontalPosition = instructions.filter { it.direction == Direction.FORWARD }.sumOf { it.units }
        val depthIncrease = instructions.filter { it.direction == Direction.DOWN }.sumOf { it.units }
        val depthDecrease = instructions.filter { it.direction == Direction.UP }.sumOf { it.units }
        return horizontalPosition * (depthIncrease - depthDecrease)
    }
}

internal class Day02Part2 : CommonTask<List<Instruction>, Int>(
    dayNum = 2,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 900,
    taskResult = 2134882034,
) {

    override fun calculateResult(instructions: List<Instruction>): Int =
        instructions.fold(Position()) { position, instruction -> when (instruction.direction) {
            Direction.FORWARD -> position.copy(horizontal = position.horizontal + instruction.units, depth = position.depth + position.aim * instruction.units)
            Direction.UP -> position.copy(aim = position.aim - instruction.units)
            Direction.DOWN -> position.copy(aim = position.aim + instruction.units)
        } }.let { it.depth * it.horizontal }

    data class Position(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0)
}

internal enum class Direction {
    UP, DOWN, FORWARD
}

internal data class Instruction(val direction: Direction, val units: Int) {
    companion object {
        fun String.toInstruction(): Instruction =
            this.split(" ").let { Instruction(Direction.valueOf(it[0].uppercase()), it[1].toInt()) }
    }
}

private val inputConverter: (String) -> List<Instruction> = { s: String -> s.trim().lines().map { it.toInstruction() }}

private val example = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
""".trimIndent()
