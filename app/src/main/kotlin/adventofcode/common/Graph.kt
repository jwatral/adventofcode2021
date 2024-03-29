package adventofcode.common

data class Graph<T>(val weights: Map<Pair<T, T>, Int>) {

    private val vertices: Set<T> = weights.keys.toList().getUniqueValuesFromPairs()
    private val edges: Map<T, Set<T>> = weights.keys
        .groupBy { it.first }
        .mapValues { it.value.getUniqueValuesFromPairs { x -> x !== it.key } }
        .withDefault { emptySet() }

    fun shortestPath(start: T, end: T): List<T> {
        val shortestPathTree: Map<T, T?> = produceShortestPathTreeUsingDijkstraAlg(start)
        fun pathTo(start: T, end: T): List<T> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(start, end)
    }

    private fun produceShortestPathTreeUsingDijkstraAlg(start: T): Map<T, T?> {
        val verticesWithKnownDistance: MutableSet<T> = mutableSetOf()
        val delta = vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0
        val previous: MutableMap<T, T?> = vertices.associateWith { null }.toMutableMap()

        while (verticesWithKnownDistance != vertices) {
            val closestNotVisitedVertex: T = delta
                .filter { !verticesWithKnownDistance.contains(it.key) }
                .minByOrNull { it.value }!!
                .key
            edges.getValue(closestNotVisitedVertex).minus(verticesWithKnownDistance).forEach { neighbor ->
                val newPath =
                    delta.getValue(closestNotVisitedVertex) + weights.getValue(Pair(closestNotVisitedVertex, neighbor))
                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = closestNotVisitedVertex
                }
            }
            verticesWithKnownDistance.add(closestNotVisitedVertex)
        }
        return previous.toMap()
    }
}
