package com.kyant.pixelmusic.locals

import androidx.compose.runtime.*
import com.kyant.pixelmusic.api.API2
import com.kyant.pixelmusic.api.search.SearchResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.net.URL

val EmptySearchResult: SearchResult = SearchResult()

val LocalSearchResult = compositionLocalOf { EmptySearchResult }

@Composable
fun ProvideSearchResult(
    name: String,
    content: @Composable () -> Unit
) {
    val jsonParser = LocalJsonParser.current
    var result: SearchResult by remember(name) { mutableStateOf(EmptySearchResult) }
    if (name.isNotBlank()) {
        LaunchedEffect(name) {
            CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                result = jsonParser.parse(
                    URL("$API2/search?keywords=$name").readText()
                ) ?: EmptySearchResult
            }
        }
    }
    CompositionLocalProvider(LocalSearchResult provides result, content = content)
}