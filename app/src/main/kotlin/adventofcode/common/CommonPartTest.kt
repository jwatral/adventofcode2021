package adventofcode.common

import org.junit.Test
import kotlin.reflect.KFunction1
import kotlin.test.assertEquals

abstract class CommonPartTest<INPUT>(
    day: Day<INPUT>,
    private val exampleResult: String,
    private val taskResult: String,
    private val inputConverter: KFunction1<String, INPUT> = day::inputConverter
) {

    private val exampleInput: INPUT = inputConverter(day.example)
    private val taskInput: INPUT = inputConverter(getInputForDay(day.yearNum, day.dayNum))

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
