package adventofcode2021.common

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.readBytes


val RESOURCES_DIR = Path("src", "test", "resources")

val SESSION_TOKEN = Properties().apply {
    load(RESOURCES_DIR.resolve("secret.properties").readBytes().inputStream())
}["aoc-session-token"]

val httpClient: HttpClient = HttpClient.newBuilder().build()

fun getInputForDay(dayNum: Int): String {
    val request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("https://adventofcode.com/2021/day/$dayNum/input"))
        .setHeader("Cookie", "session=$SESSION_TOKEN")
        .build()

    return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body()
}
