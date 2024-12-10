package adventofcode.year2024

private const val FREE_SPACE = -2

private object Solution2024Day9 : Day2024<List<Solution2024Day9.Block>>(dayNum = 9) {
    override fun inputConverter(input: String): List<Block> {
        var sumBlockLength = 0
        return input.trim().mapIndexed { i, c ->
            val blockLength = c.toString().toInt()
            val block = when (i % 2) {
                0 -> Block(i / 2, blockLength, sumBlockLength)
                1 -> Block(FREE_SPACE, blockLength, sumBlockLength)
                else -> throw IndexOutOfBoundsException()
            }
            sumBlockLength += blockLength
            block
        }
    }

    class Part1 : CommonPartTest(
        exampleResult = "1928",
        taskResult = "6398252054886",
    ) {
        override fun calculateResult(input: List<Block>): String {
            var compactedDisk = input.flatMap { it.toSingleBlocks() }.toMutableList()
            var nextFreeSlot = compactedDisk.indexOfFirst { it == FREE_SPACE }
            var nextFileToMove = compactedDisk.indexOfLast { it != FREE_SPACE }
            while (nextFreeSlot < nextFileToMove) {
                compactedDisk[nextFreeSlot] = compactedDisk[nextFileToMove]
                compactedDisk[nextFileToMove] = FREE_SPACE
                nextFreeSlot = compactedDisk.indexOfFirst { it == FREE_SPACE }
                nextFileToMove = compactedDisk.indexOfLast { it != FREE_SPACE }
            }
            return compactedDisk.filter { it != FREE_SPACE }
                .mapIndexed { i, f -> f to i }
                .sumOf { (fileId, index) -> fileId.toLong() * index }
                .toString()
        }
    }

    class Part2 : CommonPartTest(
        exampleResult = "2858",
        taskResult = "6415666220005",
    ) {
        override fun calculateResult(input: List<Block>): String {
            var compactedDisk = input.toMutableList()
            val filesToMove = compactedDisk.filter { !it.isFreeSpace() }.reversed()
            filesToMove.forEach { fileBlock ->
                val matchingFreeBlockIndex = compactedDisk.indexOfFirst { it.length >= fileBlock.length && it.isFreeSpace() }
                if (matchingFreeBlockIndex != -1 && matchingFreeBlockIndex < fileBlock.blockStart) {
                    val freeBlockLength = compactedDisk[matchingFreeBlockIndex].length
                    compactedDisk[matchingFreeBlockIndex] = fileBlock
                    val newIndex = compactedDisk.indexOfLast { it.fileId == fileBlock.fileId }
                    compactedDisk[newIndex] = Block(FREE_SPACE, fileBlock.length, 0)
                    if (freeBlockLength > fileBlock.length) {
                        compactedDisk.add(matchingFreeBlockIndex + 1, Block(FREE_SPACE, freeBlockLength - fileBlock.length, 0))
                    }
                }
            }
            return compactedDisk
                .flatMap { it.toSingleBlocks() }
                .mapIndexed { i, f -> f to i }
                .sumOf { (block, index) -> (if(block == FREE_SPACE) 0 else block).toLong() * index }
                .toString()
        }
    }

    data class Block(val fileId: Int, val length: Int, val blockStart: Int)

    fun Block.toSingleBlocks() = List(length) { fileId }
    fun Block.isFreeSpace() = fileId == FREE_SPACE

    // @formatter:off
    override val example =
"""
2333133121414131402
""".trimIndent()
    // @formatter:on
}
