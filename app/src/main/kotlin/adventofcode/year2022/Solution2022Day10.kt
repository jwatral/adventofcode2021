package adventofcode.year2022

private object Solution2022Day10 : Day2022<List<Solution2022Day10.Instruction>>(dayNum = 10) {
    override fun inputConverter(input: String): List<Instruction> =
        input.trim().lines().map {
            when {
                it == "noop" -> Noop()
                it.startsWith("addx") -> Addx(it.split(" ")[1].toInt())
                else -> throw RuntimeException("Unknown instruction: $it")
            }
        }

    class Part1 : CommonPartTest(
        exampleResult = "13140",
        taskResult = "15260",
    ) {
        override fun calculateResult(input: List<Instruction>): String =
            input.toCycles().let {
                (it.singalStrengthAt(20) +
                        it.singalStrengthAt(60) +
                        it.singalStrengthAt(100) +
                        it.singalStrengthAt(140) +
                        it.singalStrengthAt(180) +
                        it.singalStrengthAt(220)
                        ).toString()
            }
    }

    class Part2 : CommonPartTest(
        exampleResult = exampleResult,
        taskResult = taskResult,
    ) {
        override fun calculateResult(input: List<Instruction>): String {
            val cycles = input.toCycles()
            val result = mutableListOf<String>()
            cycles.forEachIndexed { index, i ->
                val sprites = listOf(i - 1, i, i + 1)
                val currentPixel = index % 40
                result.add(if (sprites.contains(currentPixel)) "#" else ".")
            }
            return result.windowed(40, 40).joinToString("\n") { it.joinToString("") { it }  }.toString()
        }
    }

    fun MutableList<Int>.singalStrengthAt(cycle: Int) = this[cycle - 1] * cycle

    fun List<Instruction>.toCycles(): MutableList<Int> {
        var currValue = 1
        var nextValue = 1
        val cycles = mutableListOf<Int>()
        this.forEach {
            when (it) {
                is Addx -> {
                    currValue = nextValue
                    nextValue = currValue + it.valueChange
                    repeat(2) { cycles.add(currValue) }
                }

                is Noop -> {
                    currValue = nextValue
                    cycles.add(currValue)
                }
            }
        }
        return cycles
    }


    sealed class Instruction(val cycles: Int, val valueChange: Int)
    class Noop() : Instruction(0, 0)
    class Addx(valueChange: Int) : Instruction(2, valueChange)

    // @formatter:off
    override val example =
"""
addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop
""".trimIndent()

    val exampleResult = """
##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....
""".trimIndent()

    val taskResult = """
###...##..#..#.####..##..#....#..#..##..
#..#.#..#.#..#.#....#..#.#....#..#.#..#.
#..#.#....####.###..#....#....#..#.#....
###..#.##.#..#.#....#.##.#....#..#.#.##.
#....#..#.#..#.#....#..#.#....#..#.#..#.
#.....###.#..#.#.....###.####..##...###.
""".trimIndent()
    // @formatter:on
}
