package adventofcode.year2022

private object Solution2022Day2 : Day2022<List<Solution2022Day2.RoundScore>>(dayNum = 2) {
    override fun inputConverter(input: String): List<Round> =
        input.trim().lines().map {
            val (their, my) = it.split(" ")
            Round(parseTheirShape(their), parseMyShape(my))
        }

    fun inputConverterPart2(input: String): List<Round2> =
        input.trim().lines().map {
            val (their, outcome) = it.split(" ")
            Round2(parseTheirShape(their), parseOutcome(outcome))
        }

    class Day2Part1 : CommonPartTest(
        exampleResult = "15",
        taskResult = "8933",
    ) {
        override fun calculateResult(input: List<RoundScore>): String = input.sumOf(RoundScore::calculateScore).toString()
    }

    class Day2Part2 : CommonPartTest(
        exampleResult = "12",
        taskResult = "11998",
        inputConverter = Solution2022Day2::inputConverterPart2
    ) {
        override fun calculateResult(input: List<RoundScore>): String = input.sumOf(RoundScore::calculateScore).toString()
    }

    data class Round2(val their: Shape, val outcome: Outcome): RoundScore {
        override fun calculateScore(): Int {
            val myShape = when(outcome) {
                Outcome.WON -> if(their == Shape.ROCK) Shape.PAPER else if (their == Shape.PAPER) Shape.SCISSORS else Shape.ROCK
                Outcome.LOST -> if(their == Shape.ROCK) Shape.SCISSORS else if (their == Shape.PAPER) Shape.ROCK else Shape.PAPER
                Outcome.DRAW -> their
            }
            return outcome.points + myShape.value()
        }
    }

    enum class Outcome(val points: Int) {
        WON(6), LOST(0), DRAW(3)
    }

    private fun parseOutcome(outcome: String): Outcome = when(outcome) {
        "X" -> Outcome.LOST
        "Y" -> Outcome.DRAW
        "Z" -> Outcome.WON
        else -> throw RuntimeException("Unknown outcome [$outcome]")
    }

    data class Round(val their: Shape, val my: Shape): RoundScore {

        override fun calculateScore(): Int = my.value() + outcomePoints()

        fun outcomePoints(): Int = when {
            their == my -> 3
            won() -> 6
            else -> 0
        }

        fun won() = (my == Shape.PAPER && their == Shape.ROCK) || (my == Shape.ROCK && their == Shape.SCISSORS) || (my == Shape.SCISSORS && their == Shape.PAPER)
    }

    interface RoundScore {
        fun calculateScore(): Int
    }

    private fun parseTheirShape(shape: String): Shape = when(shape) {
        "A" -> Shape.ROCK
        "B" -> Shape.PAPER
        "C" -> Shape.SCISSORS
        else -> throw RuntimeException("Unknown their shape [$shape]")
    }

    private fun parseMyShape(shape: String): Shape = when(shape) {
        "X" -> Shape.ROCK
        "Y" -> Shape.PAPER
        "Z" -> Shape.SCISSORS
        else -> throw RuntimeException("Unknown my shape [$shape]")
    }

    enum class Shape {
        ROCK, PAPER, SCISSORS;

        fun value() = ordinal + 1
    }


    override val example =
"""A Y
B X
C Z
""".trimIndent()
}
