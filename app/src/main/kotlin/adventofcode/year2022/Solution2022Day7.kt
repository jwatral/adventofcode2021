package adventofcode.year2022

private object Solution2022Day7 : Day2022<List<String>>(dayNum = 7) {
    override fun inputConverter(input: String): List<String> =
        input.trim().lines()

    class Part1 : CommonPartTest(
        exampleResult = "95437",
        taskResult = "1423358",
    ) {
        override fun calculateResult(input: List<String>): String =
            input.parseCommands().calculateDirSizes().flatten().filter { it.size <= 100000 }.sumOf { it.size }.toString()
    }

    class Part2 : CommonPartTest(
        exampleResult = "24933642",
        taskResult = "545729",
    ) {
        override fun calculateResult(input: List<String>): String {
            val rootDir = input.parseCommands().calculateDirSizes()
            val allDirs = rootDir.flatten()
            val sortedDirs = allDirs.sortedBy { it.size }
            val totalSize = rootDir.size
            val totalAvailable = 70000000
            val needed = 30000000
            val nowAvailable = totalAvailable - totalSize
            val stillNeeded = needed - nowAvailable
            val firstGraterThanStillNeeded = sortedDirs.first { it.size > stillNeeded }
            return firstGraterThanStillNeeded.size.toString()
        }
    }

    sealed class Node
    data class Directory(val name: String, val parent: Directory?, val children: MutableList<Node>, var size: Int = 0) :
        Node() {
        override fun toString(): String {
            return "Directory(name='$name', children=$children, size=$size)"
        }

        fun flatten(list: MutableList<Directory> = mutableListOf()): List<Directory> {
            list.addAll(children.filterIsInstance<Directory>())
            children.filterIsInstance<Directory>().forEach { it.flatten(list) }
            return list
        }
    }

    data class File(val name: String, val size: Int) : Node()

    fun Directory.calculateDirSizes(): Directory =
        this.also {
            children.forEach {
                size += when (it) {
                    is Directory -> it.calculateDirSizes().size
                    is File -> it.size
                }
            }
        }

    fun List<String>.parseCommands(): Directory {
        val rootDir = Directory("/", null, mutableListOf())
        var currentDir = rootDir
        drop(1).forEach { command ->
            when {
                command.startsWith("$ cd") -> currentDir = when (val param = command.split(" ")[2]) {
                    ".." -> currentDir.parent ?: rootDir
                    else -> {
                        val newDir = Directory(param, currentDir, mutableListOf())
                        currentDir.children.add(newDir)
                        newDir
                    }
                }
                command == "$ ls" || command.startsWith("dir") -> Unit
                else -> command.split(" ").let { (size, name) -> currentDir.children.add(File(name, size.toInt())) }
            }
        }
        return rootDir
    }

    // @formatter:off
    override val example =
"""
${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
""".trimIndent()
    // @formatter:on
}
