package com.kyant.pixelmusic.media

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.net.toUri
import com.kyant.pixelmusic.api.AlbumId
import com.kyant.pixelmusic.api.findUrl
import com.kyant.pixelmusic.util.loadCover

typealias SongId = Long

data class Song(
    val id: SongId? = null,
    val albumId: AlbumId? = null,
    val title: CharSequence? = null,
    val subtitle: CharSequence? = null,
    val description: CharSequence? = null,
    val icon: Bitmap? = null,
    val iconUri: Uri? = null,
    val extras: Bundle? = null,
    val mediaUri: Uri? = null
)

fun Song.toMediaDescription(): MediaDescriptionCompat = MediaDescriptionCompat.Builder()
    .setMediaId(id.toString())
    .setTitle(title)
    .setSubtitle(subtitle)
    .setDescription(description)
    .setIconBitmap(icon)
    .setIconUri(iconUri)
    .setExtras(Bundle().apply {
        albumId?.let { putLong("albumId", it) }
    })
    .setMediaUri(mediaUri)
    .build()

fun MediaDescriptionCompat.toSong(): Song = Song(
    mediaId?.toLong(),
    extras?.getLong("albumId"),
    title,
    subtitle,
    description,
    iconBitmap,
    iconUri,
    extras,
    mediaUri
)

@Composable
fun com.kyant.pixelmusic.api.search.Song.toMediaItem(): Song = Song(
    id,
    album?.id,
    name,
    artists?.map { it.name }?.joinToString(),
    album?.name,
    icon = album?.id?.loadCover()?.asAndroidBitmap(),
    mediaUri = id?.findUrl()?.toUri()
)