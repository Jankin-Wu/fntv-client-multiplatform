package com.jankinwu.fntv.client.data.convertor

import com.jankinwu.fntv.client.data.model.ScrollRowItemData
import com.jankinwu.fntv.client.data.model.response.MediaDbListResponse
import com.jankinwu.fntv.client.data.model.response.MediaItem
import com.jankinwu.fntv.client.data.model.response.PlayDetailResponse
import com.jankinwu.fntv.client.enums.FnTvMediaType

fun convertMediaDbListResponseToScrollRowItem(item: MediaDbListResponse): ScrollRowItemData {
    return ScrollRowItemData(
        posters = item.posters,
        title = item.title,
        guid = item.guid,
    )
}

/**
 * 将 MediaItem 转换为 MediaData
 */
fun convertToScrollRowItemData(item: MediaItem): ScrollRowItemData {
    val subtitle = if (item.type == FnTvMediaType.TV.value) {
        if (!item.firstAirDate.isNullOrBlank() && !item.lastAirDate.isNullOrBlank()) {
            "共 ${item.numberOfSeasons} 季 · ${item.firstAirDate.take(4)}~${item.lastAirDate.take(4)}"
        } else if (item.numberOfSeasons == 1 && item.status == "Ended") {
            "共 ${item.numberOfEpisodes} 集${if (!item.releaseDate.isNullOrBlank()) " · " else ""}${item.releaseDate?.take(4)}"
        } else if (item.numberOfSeasons != null && !item.releaseDate.isNullOrBlank()) {
            "第 ${item.seasonNumber} 季 · ${item.releaseDate.take(4)}"
        } else {
            item.releaseDate?.take(4)
        }
    } else if (item.releaseDate.isNullOrBlank() && !item.type.isNullOrBlank()) {
        FnTvMediaType.getByValue(item.type).description
    } else if (item.status == "1" && !item.type.isNullOrBlank() && item.type == FnTvMediaType.VIDEO.value) {
        ""
    } else {
        item.releaseDate?.take(4)
    }

    val score = try {
        item.voteAverage?.toDoubleOrNull()?.toFloat()?.let { "%.1f".format(it) } ?: "0.0"
    } catch (_: Exception) {
        "0.0"
    }

    return ScrollRowItemData(
        title = item.title,
        subtitle = subtitle,
        posterImg = item.poster,
        duration = item.duration,
        score = score,
        resolutions = item.mediaStream.resolutions?.distinct(),
        isFavourite = item.isFavorite == 1,
        isAlreadyWatched = item.watched == 1,
        guid = item.guid,
        posterWidth = item.posterWidth?: 0,
        posterHeight = item.posterHeight?: 0,
        status = item.status,
    )
}

fun convertPlayDetailToScrollRowItemData(item: PlayDetailResponse): ScrollRowItemData {
    val subtitle = when (item.type) {
        "Episode" -> {
            "第 ${item.seasonNumber} 季 · 第 ${item.episodeNumber} 集"
        }
        "Video" -> {
            " "
        }
        else -> {
            FnTvMediaType.getDescByValue(item.type)
        }
    }
    val title = when (item.type) {
        "Episode" -> item.tvTitle?: item.title
        else -> item.title
    }

    return ScrollRowItemData(
        title = title,
        subtitle = subtitle,
        posterImg = item.poster,
        duration = item.duration,
        resolutions = item.mediaStream.resolutions?.distinct(),
        isFavourite = item.isFavorite == 1,
        isAlreadyWatched = item.watched == 1,
        ts = item.ts,
        guid = item.guid,
        status = item.status,
    )
}