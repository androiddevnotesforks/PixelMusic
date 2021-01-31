package com.kyant.pixelmusic.api

import androidx.compose.runtime.Composable
import com.kyant.pixelmusic.api.toplist.TopListResult
import com.kyant.pixelmusic.locals.LocalJsonParser
import com.kyant.pixelmusic.util.launchedIOEffect
import java.net.URL

@Composable
fun findTopList(): TopListResult? {
    val jsonParser = LocalJsonParser.current
    return Unit.launchedIOEffect {
        jsonParser.parse<TopListResult>(URL("$API2/toplist").readText())
    }
}