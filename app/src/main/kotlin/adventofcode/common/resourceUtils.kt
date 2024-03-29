package adventofcode.common

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readBytes
import kotlin.io.path.readText
import kotlin.io.path.writeText


val RESOURCES_DIR = Path("src", "main", "resources")

val SESSION_TOKEN = Properties().apply {
    load(RESOURCES_DIR.resolve("secret.properties").readBytes().inputStream())
}["aoc-session-token"]

val httpClient: HttpClient = HttpClient.newBuilder().build()

fun getInputForDay(yearNum: Int, dayNum: Int): String = pathForInputFile(yearNum, dayNum).let { inputPath ->
    if(inputPath.exists()) inputPath.readText()
    else downloadInputForDay(yearNum, dayNum).also { saveInputFile(inputPath, it) }
}

private fun saveInputFile(path: Path, input: String): String = input.also { path.parent.createDirectories(); path.writeText(it) }

private fun pathForInputFile(yearNum: Int, dayNum: Int): Path =
    RESOURCES_DIR.resolve("task-inputs").resolve(yearNum.toString()).resolve("$dayNum.txt")

private fun downloadInputForDay(yearNum: Int, dayNum: Int): String {
    val request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("https://adventofcode.com/$yearNum/day/$dayNum/input"))
        .setHeader("Cookie", "session=$SESSION_TOKEN")
        .build()

    return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body()
}
