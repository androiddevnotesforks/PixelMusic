package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.kyant.pixelmusic.util.CacheDataStore
import com.kyant.pixelmusic.util.DataStore

val LocalDataStore = compositionLocalOf<DataStore> { error("No DataStore was provided.") }
val LocalCacheDataStore =
    compositionLocalOf<CacheDataStore> { error("No CacheDataStore was provided.") }

@Composable
fun ProvideDataStore(name: String, content: @Composable () -> Unit) {
    val dataStore = DataStore(LocalContext.current, name)
    Providers(LocalDataStore provides dataStore, content = content)
}

@Composable
fun ProvideCacheDataStore(name: String, content: @Composable () -> Unit) {
    val dataStore = CacheDataStore(LocalContext.current, name)
    Providers(LocalCacheDataStore provides dataStore, content = content)
}