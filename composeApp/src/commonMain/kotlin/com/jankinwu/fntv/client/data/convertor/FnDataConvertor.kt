package com.jankinwu.fntv.client.data.convertor

import com.jankinwu.fntv.client.data.model.ScrollRowItemData
import com.jankinwu.fntv.client.data.model.response.MediaDbListResponse
import com.jankinwu.fntv.client.data.model.response.MediaItem
import com.jankinwu.fntv.client.data.model.response.PersonList
import com.jankinwu.fntv.client.data.model.response.PlayDetailResponse
import com.jankinwu.fntv.client.enums.FnTvMediaType
import java.util.concurrent.TimeUnit

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
    val resolutions = item.mediaStream.resolutions?.filter { it != "Others" }?.distinct()

    return ScrollRowItemData(
        title = item.title,
        subtitle = subtitle,
        posterImg = item.poster,
        duration = item.duration,
        score = score,
        resolutions = resolutions,
        isFavourite = item.isFavorite == 1,
        isAlreadyWatched = item.watched == 1,
        guid = item.guid,
        posterWidth = item.posterWidth?: 0,
        posterHeight = item.posterHeight?: 0,
        status = item.status,
        type = item.type
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
        type = item.type,
    )
}

fun convertPersonToScrollRowItemData(personList: List<PersonList>): List<ScrollRowItemData> {
    // 按照指定的job顺序和order排序
    val sortedPersonList = personList.sortedWith(
        compareBy<PersonList> { person ->
            when (person.job) {
                "Director" -> 0
                "Actor" -> 1
                "Writer" -> 2
                else -> 3
            }
        }.thenBy { person ->
            person.order
        }
    )
    
    val scrollRowList = sortedPersonList.map {
        val description = when (it.job) {
            "Director" -> "导演"
            "Actor" -> {
                "饰演 ${it.role}"
            }

            "Writer" -> "编剧"
            else -> "其他"
        }
        ScrollRowItemData(
            title = it.name,
            subtitle = description,
            posterImg = it.profilePath,
            guid = it.personGuid,
        )
    }.filter { it.title.isNotBlank() }

    return scrollRowList
}

/**
 * 将秒数格式化为 m 分钟 s 秒或 H 小时 m 分钟格式的字符串
 * 当时间不满一小时时，不显示小时位
 */
@Suppress("DefaultLocale")
fun formatSeconds(seconds: Int): String {
    val hours = TimeUnit.SECONDS.toHours(seconds.toLong())
    val minutes = TimeUnit.SECONDS.toMinutes(seconds.toLong()) % 60
    val remainingSeconds = seconds % 60
    return if (hours > 0) {
        String.format("%d 小时 %d 分钟", hours, minutes)
    } else {
        String.format("%d 分钟 %d 秒", minutes, remainingSeconds)
    }
}