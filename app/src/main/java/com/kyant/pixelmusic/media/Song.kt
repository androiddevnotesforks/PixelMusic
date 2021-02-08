package com.kyant.pixelmusic.media

import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.net.toUri
import com.kyant.pixelmusic.api.AlbumId
import com.kyant.pixelmusic.api.SongId
import com.kyant.pixelmusic.api.findUrl
import com.kyant.pixelmusic.api.findUrls2
import com.kyant.pixelmusic.api.playlist.Track
import com.kyant.pixelmusic.util.loadCachedImage
import com.kyant.pixelmusic.util.loadCoverWithDiskCache
import java.io.Serializable

data class Song(
    val id: SongId? = null,
    val albumId: AlbumId? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val icon: ImageBitmap? = null,
    val mediaUrl: String? = null
)

data class SerializedSong(
    val id: SongId? = null,
    val albumId: AlbumId? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null
) : Serializable

fun Song.serialize(): SerializedSong = SerializedSong(
    id,
    albumId,
    title,
    subtitle,
    description
)

fun List<SerializedSong>.toSongs(context: Context): List<Song> {
    val urls = map { it.id!! }.findUrls2()
    return mapIndexed { index, song ->
        Song(
            song.id,
            song.albumId,
            song.title,
            song.subtitle,
            song.description,
            loadCachedImage(context, song.albumId.toString(), "covers"),
            urls[index]
        )
    }
}

fun Song.toMediaDescription(): MediaDescriptionCompat = MediaDescriptionCompat.Builder()
    .setMediaId(id.toString())
    .setTitle(title)
    .setSubtitle(subtitle)
    .setDescription(description)
    .setIconBitmap(icon?.asAndroidBitmap())
    .setExtras(Bundle().apply {
        albumId?.let { putLong("albumId", it) }
    })
    .setMediaUri(mediaUrl?.toUri())
    .build()

fun MediaDescriptionCompat.toSong(): Song = Song(
    mediaId?.toLong(),
    extras?.getLong("albumId"),
    title?.toString(),
    subtitle?.toString(),
    description?.toString(),
    iconBitmap?.asImageBitmap(),
    mediaUri.toString()
)

@Composable
fun com.kyant.pixelmusic.api.search.Song.toSong(): Song = Song(
    id,
    album?.id,
    name,
    artists?.map { it.name }?.joinToString(),
    album?.name,
    album?.id?.loadCoverWithDiskCache(),
    id?.findUrl()
)

@Composable
fun List<Track>.toSongs(): List<Song> {
    return map { track ->
        Song(
            track.id,
            track.al?.id,
            track.name,
            track.ar?.map { it.name }?.joinToString(),
            track.al?.name,
            track.al?.id?.loadCoverWithDiskCache(),
            track.id?.findUrl()
        )
    }
}