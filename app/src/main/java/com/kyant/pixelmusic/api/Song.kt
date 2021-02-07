package com.kyant.pixelmusic.api

import androidx.compose.runtime.*
import com.kyant.pixelmusic.api.song.SongResult
import com.kyant.pixelmusic.locals.JsonParser
import com.kyant.pixelmusic.locals.LocalJsonParser
import com.kyant.pixelmusic.util.launchedIOEffect
import java.net.URL

typealias SongId = Long

@Composable
fun SongId.findUrl(): String? {
    val jsonParser = LocalJsonParser.current
    return launchedIOEffect {
        jsonParser.parse<SongResult>(
            URL("$API2/song/url?id=${this@findUrl}").readText()
        )?.data?.get(0)?.url
    }
}

@Composable
fun List<SongId>.findUrls(): List<String?>? {
    val jsonParser = LocalJsonParser.current
    return launchedIOEffect {
        val result = jsonParser.parse<SongResult>(
            URL("$API2/song/url?id=${this@findUrls.joinToString()}").readText()
        )?.data?.map { it.id to it.url }?.toMap()
        val urls = mutableListOf<String?>()
        forEach {
            urls += result?.getValue(it)
        }
        urls
    }
}

fun List<SongId>.findUrls2(): List<String?> {
    val result = JsonParser().parse<SongResult>(
        URL("$API2/song/url?id=${this@findUrls2.joinToString()}").readText()
    )?.data?.map { it.id to it.url }?.toMap()
    val urls = mutableListOf<String?>()
    forEach {
        urls += result?.getValue(it)
    }
    return urls
}