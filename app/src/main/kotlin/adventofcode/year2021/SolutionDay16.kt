package adventofcode.year2021

import adventofcode.common.executeUntilEmpty
import adventofcode.common.readNBitsAsString
import adventofcode.common.readUntilFirst

private typealias Bits = Iterator<Char>

private object Day16 : Day2021<Bits>(dayNum = 16) {
        override fun inputConverter(input: String) =
        input.trim()
            .map { it.digitToInt(16).toString(2).padStart(4, '0') }
            .flatMap { it.toList() }
            .iterator()

    class Day16Part1 : CommonPartTest(
        exampleResult = "31",
        taskResult = "901",
    ) {
        override fun calculateResult(input: Bits): String = input.parsePacket().versions().sum().toString()
    }

    class Day16Part2 : CommonPartTest(
        exampleResult = "54",
        taskResult = "110434737925",
    ) {
        override fun calculateResult(input: Bits): String = input.parsePacket().value.toString()
    }

    fun Bits.readNBitsAsInt(numOfBits: Int): Int = this.readNBitsAsString(numOfBits).toInt(2)

    sealed class Packet(val version: Int) {
        abstract val value: Long
        abstract fun versions(): List<Int>
    }

    class Literal(version: Int, override val value: Long) : Packet(version) {
        override fun versions(): List<Int> = listOf(version)
    }

    fun Bits.parsePacket(): Packet {
        val version = this.readNBitsAsInt(3)
        return when (val type = this.readNBitsAsInt(3)) {
            4 -> Literal(version, parseValue(this))
            else -> Operator.of(version, type, this)
        }
    }

    fun parseValue(bits: Bits): Long =
        bits.readUntilFirst(5) { it.startsWith('0') }.joinToString("") { it.drop(1) }.toLong(2)


    class Operator(version: Int, type: Int, val subPackets: List<Packet>) : Packet(version) {
        override val value: Long = when (type) {
            0 -> subPackets.sumOf { it.value }
            1 -> subPackets.fold(1) { acc, packet -> acc * packet.value }
            2 -> subPackets.minOf { it.value }
            3 -> subPackets.maxOf { it.value }
            5 -> if (subPackets[0].value > subPackets[1].value) 1 else 0
            6 -> if (subPackets[0].value < subPackets[1].value) 1 else 0
            7 -> if (subPackets[0].value == subPackets[1].value) 1 else 0
            else -> 0
        }

        companion object {
            fun of(version: Int, type: Int, bits: Bits): Operator =
                when (bits.readNBitsAsInt(1)) {
                    0 -> {
                        val length = bits.readNBitsAsInt(15)
                        val subPackets =
                            bits.readNBitsAsString(length).iterator().executeUntilEmpty { it.parsePacket() }
                        Operator(version, type, subPackets)
                    }
                    1 -> {
                        val numOfPackets = bits.readNBitsAsInt(11)
                        val subPackets = (1..numOfPackets).map { bits.parsePacket() }
                        Operator(version, type, subPackets)
                    }
                    else -> throw RuntimeException("Error during operator parsing")
                }
        }

        override fun versions(): List<Int> = listOf(version) + subPackets.flatMap { it.versions() }
    }

    override val example = "A0016C880162017C3686B18A3D4780"
}
