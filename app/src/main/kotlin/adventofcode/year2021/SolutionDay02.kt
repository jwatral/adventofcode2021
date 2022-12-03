package adventofcode.year2021

import adventofcode.year2021.Day02.Instruction.Companion.toInstruction

private object Day02 : Day2021<List<Day02.Instruction>>(dayNum = 2) {
    override fun inputConverter(input: String): List<Instruction> = input.trim().lines().map { it.toInstruction() }

    class Day02Part1 : CommonPartTest(
        exampleResult = "150",
        taskResult = "2272262",
    ) {

        override fun calculateResult(instructions: List<Instruction>): String {
            val horizontalPosition = instructions.filter { it.direction == Direction.FORWARD }.sumOf { it.units }
            val depthIncrease = instructions.filter { it.direction == Direction.DOWN }.sumOf { it.units }
            val depthDecrease = instructions.filter { it.direction == Direction.UP }.sumOf { it.units }
            return (horizontalPosition * (depthIncrease - depthDecrease)).toString()
        }
    }

    class Day02Part2 : CommonPartTest(
        exampleResult = "900",
        taskResult = "2134882034",
    ) {

        override fun calculateResult(instructions: List<Instruction>): String =
            instructions.fold(Position()) { position, instruction ->
                when (instruction.direction) {
                    Direction.FORWARD -> position.copy(
                        horizontal = position.horizontal + instruction.units,
                        depth = position.depth + position.aim * instruction.units
                    )
                    Direction.UP -> position.copy(aim = position.aim - instruction.units)
                    Direction.DOWN -> position.copy(aim = position.aim + instruction.units)
                }
            }.let { it.depth * it.horizontal }.toString()

        data class Position(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0)
    }

    override val example = """
    forward 5
    down 5
    forward 8
    up 3
    down 8
    forward 2
""".trimIndent()

    enum class Direction {
        UP, DOWN, FORWARD
    }

    data class Instruction(val direction: Direction, val units: Int) {
        companion object {
            fun String.toInstruction(): Instruction =
                this.split(" ").let { Instruction(Direction.valueOf(it[0].uppercase()), it[1].toInt()) }
        }
    }

}
