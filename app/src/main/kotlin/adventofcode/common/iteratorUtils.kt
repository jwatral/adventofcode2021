package adventofcode.common

fun <T,R> Iterator<T>.executeUntilEmpty(function: (Iterator<T>) -> R): List<R> {
    val output = mutableListOf<R>()
    while (this.hasNext()) {
        output.add(function(this))
    }
    return output
}

fun <T> Iterator<T>.readNBitsAsString(numOfBits: Int): String = (1..numOfBits).map { next() }.joinToString("")

fun <T> Iterator<T>.readUntilFirst(size: Int, untilCondition: (String) -> Boolean): List<String> {
    val output = mutableListOf<String>()
    do {
        val readValue = readNBitsAsString(size)
        output.add(readValue)
    } while (!untilCondition(readValue))
    return output
}
