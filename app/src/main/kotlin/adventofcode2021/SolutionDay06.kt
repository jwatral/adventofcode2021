package adventofcode2021

import adventofcode2021.common.CommonTask

class Day06Part1 : CommonTask<List<Int>, Int>(
    dayNum = 6,
    example = example,
    inputConverter = inputConverter,
    exampleResult = 5934,
    taskResult = 387413,
) {

    override fun calculateResult(initialState: List<Int>): Int {
        val mutableState = initialState.toMutableList()
        repeat(80) { tick(mutableState) }
        return mutableState.size
    }

    private fun tick(state: MutableList<Int>) {
        var fishesToCreate = 0
        state.forEachIndexed() { index, stateValue ->
            state[index] = (when(stateValue) {
                0 -> { fishesToCreate++; 6 }
                else -> stateValue - 1
            })
        }
        repeat(fishesToCreate){ state.add(8) }
    }
}

private val inputConverter: (String) -> List<Int> = { s ->
    s.trim().split(",").map { it.toInt() }
}
private fun String.toPoint(): Point = this.trim().split(",").map { it.toInt() }.let { Point(it[0], it[1]) }

private val example = "3,4,3,1,2"
