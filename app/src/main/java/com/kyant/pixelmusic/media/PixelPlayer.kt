package com.kyant.pixelmusic.media

import android.content.Context
import android.support.v4.media.MediaDescriptionCompat
import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.kyant.pixelmusic.locals.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer

class PixelPlayer(context: Context) : SimpleExoPlayer(Builder(context)) {
    private val animationPeriod = AnimationConstants.DefaultDurationMillis.toLong()
    var isPlayingState: Boolean by mutableStateOf(false)
    var durationState: Long by mutableStateOf(1)
    lateinit var position: Animatable<Float, AnimationVector1D>
    lateinit var bufferedPositionState: Animatable<Float, AnimationVector1D>
    inline val progress: Float get() = position.value / durationState.toFloat()
    inline val bufferedProgress: Float get() = bufferedPositionState.value / durationState.toFloat()

    init {
        setThrowsWhenUsingWrongThread(false)
        val mediaSessionConnector = Media.session?.let { MediaSessionConnector(it) }
        mediaSessionConnector?.setPlayer(this)
        Media.session?.let {
            mediaSessionConnector?.setQueueNavigator(object : TimelineQueueNavigator(it) {
                override fun getMediaDescription(
                    player: Player,
                    windowIndex: Int
                ): MediaDescriptionCompat {
                    return Media.songs[windowIndex].toMediaDescription()
                }
            })
        }
        prepare()
        addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                isPlayingState = playbackState == STATE_READY && playWhenReady
                when (playbackState) {
                    STATE_IDLE -> {
                    }
                    STATE_BUFFERING -> {
                        fixedRateTimer(
                            "buffered_position", false,
                            animationPeriod, animationPeriod
                        ) {
                            CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                                bufferedPositionState.animateTo(
                                    bufferedPosition.toFloat(),
                                    tween(animationPeriod.toInt(), easing = LinearEasing)
                                )
                            }
                        }
                    }
                    STATE_READY -> {
                        durationState = duration
                        if (playWhenReady) {
                            fixedRateTimer(
                                "position", false,
                                animationPeriod, animationPeriod
                            ) {
                                CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                                    position.animateTo(
                                        contentPosition.toFloat(),
                                        tween(animationPeriod.toInt(), easing = LinearEasing)
                                    )
                                }
                            }
                        } else {
                            CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                                position.stop()
                            }
                        }
                    }
                    STATE_ENDED -> {
                    }
                }
            }
        })
    }

    fun playOrPause() {
        playWhenReady = !isPlayingState
    }
}