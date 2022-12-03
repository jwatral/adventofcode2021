package adventofcode.common

import org.junit.Test
import kotlin.reflect.KFunction1
import kotlin.test.assertEquals

abstract class Day<INPUT>(open val dayNum: Int, val yearNum: Int) {
    abstract val example: String
    abstract fun inputConverter(input: String): INPUT

    abstract inner class CommonPartTest(
        private val exampleResult: String,
        private val taskResult: String,
        private val inputConverter: KFunction1<String, INPUT> = ::inputConverter
    ) {

        private val exampleInput: INPUT = inputConverter(example)
        private val taskInput: INPUT = inputConverter(getInputForDay(yearNum, dayNum))

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
}
