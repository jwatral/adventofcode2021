package adventofcode2021.day02

internal object SolutionDay02 {

    fun calculatePart1(instructions: List<Instruction>): Int {
        val horizontalPosition = instructions.filter { it.direction == Direction.FORWARD }.sumOf { it.units }
        val depthIncrease = instructions.filter { it.direction == Direction.DOWN }.sumOf { it.units }
        val depthDecrease = instructions.filter { it.direction == Direction.UP }.sumOf { it.units }
        return horizontalPosition * (depthIncrease - depthDecrease)
    }

    fun calculatePart2(instructions: List<Instruction>): Int =
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
