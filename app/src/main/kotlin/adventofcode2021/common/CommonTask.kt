package adventofcode2021.common

import org.junit.Test
import kotlin.test.assertEquals

abstract class CommonTask<INPUT, RESULT>(
    dayNum: Int,
    example: String,
    inputConverter: (String) -> INPUT,
    private val exampleResult: RESULT,
    private val taskResult: RESULT? = null,
) {

    private val exampleInput: INPUT = inputConverter(example)
    private val taskInput: INPUT = inputConverter(getInputForDay(dayNum))

    abstract fun calculateResult(input: INPUT): RESULT

    @Test
    fun testExample() {
        val result = calculateResult(exampleInput)
        assertEquals(exampleResult, result)
    }

    @Test
    fun testTask() {
        val result = calculateResult(taskInput)
        assertEquals(taskResult, result)
    }
}
