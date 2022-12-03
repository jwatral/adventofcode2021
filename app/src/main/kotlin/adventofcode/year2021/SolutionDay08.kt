package adventofcode.year2021

private object Day08 : Day2021<List<Day08.Input>>(dayNum = 8) {
        override fun inputConverter(input: String) =
        input.trim().lines().map { it.split(" | ") }.map { Input(it[0], it[1]) }

    class Day08Part1 : CommonPartTest(
        exampleResult = "26",
        taskResult = "470",
    ) {
        private val uniqueNumOfSegments = listOf(2, 4, 3, 7)

        override fun calculateResult(input: List<Input>): String =
            input.map { it.output }.map { it.split(" ") }.flatten().count { uniqueNumOfSegments.contains(it.length) }.toString()
    }

    class Day08Part2 : CommonPartTest(
        exampleResult = "61229",
        taskResult = "989396",
    ) {

        private val realMapping = mapOf(
            "abcefg" to 0,
            "cf" to 1,
            "acdeg" to 2,
            "acdfg" to 3,
            "bcdf" to 4,
            "abdfg" to 5,
            "abdefg" to 6,
            "acf" to 7,
            "abcdefg" to 8,
            "abcdfg" to 9,
        ).mapKeys { it.key.toCharSet() }.mapValues { it.value.toString() }

        override fun calculateResult(input: List<Input>): String = input.sumOf(::decode).toString()

        private fun decode(input: Input): Int {
            val mapping = findMapping(input.signalPatterns)
            return input.output
                .split(" ")
                .map { it.toCharSet() }
                .map { it.map { mapping[it]!! } }
                .joinToString("") { realMapping[it.toSet()]!! }
                .toInt()
        }

        private fun findMapping(signalPatterns: String): Map<Char, Char> {
            val patterns = signalPatterns.split(" ")
            val one = patterns.find { it.length == 2 }.toCharSet()
            val four = patterns.find { it.length == 4 }.toCharSet()
            val seven = patterns.find { it.length == 3 }.toCharSet()
            val eight = patterns.find { it.length == 7 }.toCharSet()
            // 3a 3b 2c 2d 2e 3f 3g
            val zeroOrSixOrNine =
                patterns.filter { it.length == 6 }.joinToString("").groupBy { it }.map { it.key to it.value.size }.toMap()
            val twoOrThreeOrFive =
                patterns.filter { it.length == 5 }.joinToString("").groupBy { it }.map { it.key to it.value.size }.toMap()
            val a = (seven - one).first()
            val bd = (four - one)
            val eg = eight - (four + seven)
            val be = twoOrThreeOrFive.filter { it.value == 1 }.keys
            val b = be - eg
            val d = bd - b
            val e = be - b
            val g = eg - e
            val cde = zeroOrSixOrNine.filter { it.value == 2 }.keys
            val c = cde - (d + e)
            val f = one - c
            return mapOf(
                a to 'a',
                b.first() to 'b',
                c.first() to 'c',
                d.first() to 'd',
                e.first() to 'e',
                f.first() to 'f',
                g.first() to 'g',
            )
        }
    }

    private fun String?.toCharSet() = this!!.toCharArray().toSet()

    data class Input(val signalPatterns: String, val output: String)

    override val example = """
be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce 
    """.trimIndent()
}
