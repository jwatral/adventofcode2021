package adventofcode2021

import adventofcode2021.common.CommonTask

class Day08Part1 : CommonTask<List<Day08Input>, Int>(
    dayNum = 8,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 26,
    taskResult = 470,
) {
    private val uniqueNumOfSegments = listOf(2, 4, 3, 7)

    override fun calculateResult(input: List<Day08Input>): Int =
        input.map { it.output }
            .map { it.split(" " ) }
            .flatten()
            .count { uniqueNumOfSegments.contains(it.length) }
}

data class Day08Input(val signalPatterns: String, val output: String)

private val inputConverter: (String) -> List<Day08Input> = { s ->
    s.trim().lines().map { it.split(" | ") }.map { Day08Input(it[0], it[1]) }
}

private val example = """
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
