package adventofcode2021.day01

object Solution {

    fun calculatePart1(input: List<Int>): Int = input.zipWithNext { a, b -> b > a }.count { it }

    fun calculatePart2(input: List<Int>): Int =
        input.windowed(3).map { it.sum() }.zipWithNext { a, b -> b > a }.count { it }
}
