package com.kyant.pixelmusic.media

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import androidx.media.session.MediaButtonReceiver

class MediaButtonReceiver : MediaButtonReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        when ((intent?.extras?.get("android.intent.extra.KEY_EVENT") as KeyEvent).keyCode) {
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
            }
        }
    }
}