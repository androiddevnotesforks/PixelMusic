package com.kyant.pixelmusic.util

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okio.IOException

@Composable
inline fun Any?.LaunchedIOEffectUnit(crossinline block: suspend CoroutineScope.() -> Unit) {
    LaunchedEffect(this) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            block()
        }
    }
}

@Composable
inline fun <T> Any?.launchedIOEffect(crossinline block: suspend CoroutineScope.(T?) -> T?): T? {
    var result by remember(this) { mutableStateOf<T?>(null) }
    LaunchedEffect(this) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                result = block(result)
            } catch (e: IOException) {
                println(e)
            }
        }
    }
    return result
}

inline fun <T> Any?.launchedIO(crossinline block: suspend CoroutineScope.(T?) -> T?): T? {
    var result by mutableStateOf<T?>(null)
    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
        try {
            result = block(result)
        } catch (e: IOException) {
            println(e)
        }
    }
    return result
}