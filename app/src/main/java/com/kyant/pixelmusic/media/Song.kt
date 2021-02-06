package com.kyant.pixelmusic.media

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.net.toUri
import com.kyant.pixelmusic.api.AlbumId
import com.kyant.pixelmusic.api.SongId
import com.kyant.pixelmusic.api.findUrl
import com.kyant.pixelmusic.api.playlist.Track
import com.kyant.pixelmusic.util.loadCover

data class Song(
    val id: SongId? = null,
    val albumId: AlbumId? = null,
    val title: CharSequence? = null,
    val subtitle: CharSequence? = null,
    val description: CharSequence? = null,
    val icon: Bitmap? = null,
    val iconUrl: String? = null,
    val extras: Bundle? = null,
    val mediaUrl: String? = null
)

fun Song.toMediaDescription(): MediaDescriptionCompat = MediaDescriptionCompat.Builder()
    .setMediaId(id.toString())
    .setTitle(title)
    .setSubtitle(subtitle)
    .setDescription(description)
    .setIconBitmap(icon)
    .setIconUri(iconUrl?.toUri())
    .setExtras(Bundle().apply {
        albumId?.let { putLong("albumId", it) }
    })
    .setMediaUri(mediaUrl?.toUri())
    .build()

fun MediaDescriptionCompat.toSong(): Song = Song(
    mediaId?.toLong(),
    extras?.getLong("albumId"),
    title,
    subtitle,
    description,
    iconBitmap,
    iconUri.toString(),
    extras,
    mediaUri.toString()
)

@Composable
fun com.kyant.pixelmusic.api.search.Song.toSong(): Song = Song(
    id,
    album?.id,
    name,
    artists?.map { it.name }?.joinToString(),
    album?.name,
    icon = album?.id?.loadCover()?.asAndroidBitmap(),
    mediaUrl = id?.findUrl()
)

@Composable
fun Track.toSong(): Song = Song(
    id,
    al?.id,
    name,
    ar?.map { it.name }?.joinToString(),
    al?.name,
    icon = al?.id?.loadCover()?.asAndroidBitmap(),
    mediaUrl = id?.findUrl()
)