package com.jankinwu.fntv.client.data.convertor

import com.jankinwu.fntv.client.data.model.MediaData
import com.jankinwu.fntv.client.data.model.response.MediaDbListResponse
import com.jankinwu.fntv.client.data.model.response.MediaItem
import com.jankinwu.fntv.client.data.model.response.PlayDetailResponse
import com.jankinwu.fntv.client.enums.FnTvMediaType

fun convertMediaDbListResponseToMediaData(item: MediaDbListResponse): MediaData {
    return MediaData(
        posters = item.posters,
        title = item.title,
        guid = item.guid,
    )
}

/**
 * 将 MediaItem 转换为 MediaData
 */
fun convertToMediaData(item: MediaItem): MediaData {
    val subtitle = if (item.type == FnTvMediaType.TV.value) {
        if (!item.firstAirDate.isNullOrBlank() && !item.lastAirDate.isNullOrBlank()) {
            "共${item.numberOfSeasons}季·${item.firstAirDate.take(4)}~${item.lastAirDate.take(4)}"
        } else if (item.numberOfSeasons != null && !item.releaseDate.isNullOrBlank()) {
            "第${item.numberOfSeasons}季·${item.releaseDate.take(4)}"
        } else {
            item.releaseDate
        }
    } else {
        item.releaseDate
    }

    val score = try {
        item.voteAverage?.toDoubleOrNull()?.toFloat()?.let { "%.1f".format(it) } ?: "0.0"
    } catch (_: Exception) {
        "0.0"
    }

    return MediaData(
        title = item.title,
        subtitle = subtitle,
        posterImg = item.poster,
        duration = item.duration,
        score = score,
        resolutions = item.mediaStream.resolutions,
        isFavourite = item.isFavorite == 1,
        isAlreadyWatched = item.watched == 1,
        guid = item.guid
    )
}

fun convertPlayDetailToMediaData(item: PlayDetailResponse): MediaData {
    val subtitle = if (item.type == "Episode") {
        "第${item.seasonNumber}季·第${item.episodeNumber}集"
    } else {
        FnTvMediaType.getDescByValue(item.type)
    }

    return MediaData(
        title = item.title,
        subtitle = subtitle,
        posterImg = item.poster,
        duration = item.duration,
        resolutions = item.mediaStream.resolutions,
        isFavourite = item.isFavorite == 1,
        isAlreadyWatched = item.watched == 1,
        guid = item.guid
    )
}