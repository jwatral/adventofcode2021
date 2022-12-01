package adventofcode.common

import org.junit.Test
import kotlin.test.assertEquals

abstract class CommonPartTest<INPUT>(
    day: Day<INPUT>,
    private val exampleResult: String,
    private val taskResult: String,
) {

    private val exampleInput: INPUT = day.inputConverter(day.example)
    private val taskInput: INPUT = day.inputConverter(getInputForDay(day.yearNum, day.dayNum))

    abstract fun calculateResult(input: INPUT): String

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
