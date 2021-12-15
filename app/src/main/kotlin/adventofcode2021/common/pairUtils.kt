package adventofcode2021.common

fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(predicate: (T) -> Boolean = {true}): Set<T> =
    this.map { (a, b) -> listOf(a, b) }
    .flatten()
    .filter(predicate)
    .toSet()
