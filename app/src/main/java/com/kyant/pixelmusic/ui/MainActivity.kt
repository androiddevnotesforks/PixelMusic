package com.kyant.pixelmusic.ui

import android.content.*
import android.media.AudioManager
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.*
import com.kyant.inimate.layer.BackLayer
import com.kyant.inimate.layer.ForeLayer
import com.kyant.pixelmusic.R
import com.kyant.pixelmusic.locals.*
import com.kyant.pixelmusic.media.*
import com.kyant.pixelmusic.ui.component.BottomNav
import com.kyant.pixelmusic.ui.component.TopBar
import com.kyant.pixelmusic.ui.my.My
import com.kyant.pixelmusic.ui.nowplaying.NowPlaying
import com.kyant.pixelmusic.ui.screens.*
import com.kyant.pixelmusic.ui.search.Search
import com.kyant.pixelmusic.ui.theme.PixelMusicTheme
import com.kyant.pixelmusic.util.currentRoute

enum class Screens { HOME, EXPLORE }

class MainActivity : AppCompatActivity() {
    private val mediaButtonReceiver = MediaButtonReceiver()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        createNotificationChannel(Media.NOTIFICATION_CHANNEL_ID, getString(R.string.app_name))
        Media.init(this, connectionCallbacks)
        setContent {
            PixelMusicTheme(window) {
                val navController = rememberNavController()
                var state: SwipeableState<Boolean>? by remember { mutableStateOf(null) }
                val searchState = rememberSwipeableState(false).apply {
                    LaunchedEffect(progress.fraction == 1f) {
                        println("aaa")
                        if (progress.fraction != 1f) {
                            state = this@apply
                        }
                    }
                }
                val myState = rememberSwipeableState(false).apply {
                    LaunchedEffect(progress.fraction == 1f) {
                        if (progress.fraction != 1f) {
                            state = this@apply
                        }
                    }
                }
                val nowPlayingState = rememberSwipeableState(false).apply {
                    LaunchedEffect(progress.fraction == 1f) {
                        if (progress.fraction != 1f) {
                            state = this@apply
                        }
                    }
                }
                BackHandler(searchState.value or myState.value or nowPlayingState.value) {
                    if (searchState.value) searchState.animateTo(false)
                    if (myState.value)  myState.animateTo(false)
                    if (nowPlayingState.value) nowPlayingState.animateTo(false)
                }
                val items = listOf(
                    Triple(Screens.HOME.name, "Home", Icons.Outlined.Home),
                    Triple(Screens.EXPLORE.name, "Explore", Icons.Outlined.Explore)
                )
                ProvidePixelPlayer {
                    Media.player = LocalPixelPlayer.current
                    ProvideJsonParser {
                        BoxWithConstraints(Modifier.fillMaxSize()) {
                            BackLayer(state) {
                                NavHost(navController, Screens.HOME.name) {
                                    composable(Screens.HOME.name) { Home() }
                                    composable(Screens.EXPLORE.name) { Explore() }
                                }
                                TopBar(searchState, myState)
                            }
                            BottomNav(
                                items,
                                { navController.currentRoute() == it },
                                { navController.navigate(it) },
                                Modifier.align(Alignment.BottomCenter)
                            )
                            ForeLayer(searchState) {
                                Search()
                            }
                            ProvideNowPlaying(Media.nowPlaying) {
                                NowPlaying(nowPlayingState)
                            }
                            ForeLayer(myState) {
                                My()
                            }
                        }
                    }
                }
            }
        }
    }

    private var controllerCallback = object : MediaControllerCompat.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {}
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {}
    }

    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            Media.browser.sessionToken.also { token ->
                val mediaController = MediaControllerCompat(this@MainActivity, token)
                MediaControllerCompat.setMediaController(this@MainActivity, mediaController)
            }
            MediaControllerCompat.getMediaController(this@MainActivity)
                .registerCallback(controllerCallback)
        }

        override fun onConnectionSuspended() {}

        override fun onConnectionFailed() {}
    }

    public override fun onStart() {
        super.onStart()
        registerReceiver(mediaButtonReceiver, IntentFilter(Intent.ACTION_MEDIA_BUTTON))
        try {
            if (!Media.browser.isConnected) {
                Media.browser.connect()
            }
        } catch (e: IllegalStateException) {
            println(e)
        }
    }

    public override fun onResume() {
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mediaButtonReceiver)
        MediaControllerCompat.getMediaController(this)?.unregisterCallback(controllerCallback)
        Media.browser.disconnect()
        ContextCompat.getSystemService(this, MediaPlaybackService::class.java)?.stopSelf()
        Media.session?.isActive = false
        Media.player?.stop()
        Media.restore()
    }
}