package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.compositionLocalOf
import com.beust.klaxon.Klaxon

typealias JsonParser = Klaxon

val LocalJsonParser = compositionLocalOf { Klaxon() }

@Composable
fun ProvideJsonParser(content: @Composable () -> Unit) {
    Providers(LocalJsonParser provides JsonParser(), content = content)
}