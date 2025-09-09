package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.model.MediaData
import com.jankinwu.fntv.client.data.model.response.MediaItem
import com.jankinwu.fntv.client.data.model.response.PlayDetailResponse
import com.jankinwu.fntv.client.enums.FnTvMediaType
import com.jankinwu.fntv.client.ui.component.MediaLibGallery
import com.jankinwu.fntv.client.ui.component.RecentlyWatched
import com.jankinwu.fntv.client.viewmodel.MediaDbListViewModel
import com.jankinwu.fntv.client.viewmodel.MediaListViewModel
import com.jankinwu.fntv.client.viewmodel.PlayListViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.ScrollbarContainer
import io.github.composefluent.component.Text
import io.github.composefluent.component.rememberScrollbarAdapter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomePageScreen() {
    val mediaDbListViewModel: MediaDbListViewModel = koinViewModel<MediaDbListViewModel>()
    val mediaDbUiState by mediaDbListViewModel.uiState.collectAsState()
    val playListViewModel: PlayListViewModel = koinViewModel<PlayListViewModel>()
    val playListUiState by playListViewModel.uiState.collectAsState()

    val sampleMovies = remember {
        listOf(
            MediaData(
                "黑袍纠察队",
                "共4季·2019~2024",
                "8.5",
                listOf("4K", "1080"),
                "",
                duration = 10822,
                ts = 2650
            ),
            MediaData("曼达洛人", "共3季·2019", "8.4", listOf("4K"), ""),
            MediaData("爱，死亡和机器人", "共4季·2019~2022", "8.3", listOf("1080"), ""),
            MediaData("洛基", "共2季·2021~2023", "8.2", listOf("4K"), ""),
            MediaData("最后生还者", "共2季·2023", "8.6", listOf("4K"), ""),
            MediaData("惩罚者", "共2季·2017~2019", "8.1", listOf("4K"), ""),
            MediaData("夜魔侠", "共4季·2015~2018", "8.2", listOf("720"), ""),
            MediaData("卢克·凯奇", "共2季·2016", "6.9", listOf("720"), ""),
            MediaData("斗篷与匕首", "共2季·2018", "7.1", listOf("1080p"), ""),
            MediaData("绝命毒师", "共5季·2008~2013", "8.9", listOf("4K"), ""),
            MediaData("夜魔侠：重生", "共1季·2025", "8.6", listOf("4K"), ""),
            MediaData("黑袍纠察队", "共4季·2019~2024", "8.5", listOf("4K"), ""),
            MediaData("曼达洛人", "共3季·2019", "8.4", listOf("4K"), ""),
            MediaData("爱，死亡和机器人", "共4季·2019~2022", "8.3", listOf("1080"), ""),
            MediaData("洛基", "共2季·2021~2023", "8.2", listOf("1080"), ""),
            MediaData("最后生还者", "共2季·2023", "8.6", listOf("4K"), ""),
            MediaData("惩罚者", "共2季·2017~2019", "8.1", listOf("4K"), ""),
            MediaData("夜魔侠", "共4季·2015~2018", "8.2", listOf("1080"), ""),
            MediaData("卢克·凯奇", "共2季·2016", "6.9", listOf("1080"), ""),
            MediaData("斗篷与匕首", "共2季·2018", "7.1", listOf("1080"), ""),
            MediaData("绝命毒师", "共5季·2008~2013", "8.9", listOf("4K"), ""),
            MediaData("夜魔侠：重生", "共1季·2025", "8.6", listOf("4K"), "")
        )
    }
    val lazyListState = rememberLazyListState()
    Column(horizontalAlignment = Alignment.Start) {
        ScrollbarContainer(
            adapter = rememberScrollbarAdapter(lazyListState)
        ) {
            LazyColumn(
                state = lazyListState,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                item {
                    Text(
                        text = "首页",
                        style = LocalTypography.current.subtitle,
                        color = FluentTheme.colors.text.text.tertiary,
                        modifier = Modifier
//                .alignHorizontalSpace()
                            .padding(top = 36.dp, start = 32.dp)
                    )
                }
                item {
                    LaunchedEffect(Unit) {
                        playListViewModel.loadData()
                    }
                    when (val playListState = playListUiState) {
                        is UiState.Success -> {
                            RecentlyWatched(
                                title = "继续观看",
                                movies = playListState.data.map { it ->
                                    convertPlayDetailToMediaData(it)
                                }
                            )
                        }
                        else -> {}
                    }
//                    RecentlyWatched(
//                        movies = sampleMovies,
//                        title = "继续观看"
//                    )
                }
                // 动态生成媒体库组件列表
                when (val mediaDbState = mediaDbUiState) {
                    is UiState.Success -> {
                        items(mediaDbState.data) { mediaLib ->
                            val mediaListViewModel: MediaListViewModel = koinViewModel(key = mediaLib.guid)
                            val mediaListUiState = mediaListViewModel.uiState.collectAsState().value
                            mediaListViewModel.loadData(mediaLib.guid, FnTvMediaType.getAll())

                            // 转换 MediaItem 到 MediaData
                            val mediaDataList = when (val listState = mediaListUiState) {
                                is UiState.Success -> {
                                    listState.data.list.map { item ->
                                        convertToMediaData(item)
                                    }
                                }
                                else -> emptyList()
                            }

                            MediaLibGallery(
                                movies = mediaDataList,
                                title = mediaLib.title
                            )

                        }
                    }

                    else -> {
                        // 请求失败时也创建媒体库组件，但子项为空
                    }
                }
            }
        }
    }

}

/**
 * 将 MediaItem 转换为 MediaData
 */
private fun convertToMediaData(item: MediaItem): MediaData {
    val subtitle = if (item.type == FnTvMediaType.TV.value) {
        if (!item.firstAirDate.isNullOrBlank() && !item.lastAirDate.isNullOrBlank()) {
            "共${item.numberOfSeasons}季·${item.firstAirDate.take(4)}~${item.lastAirDate.take(4)}"
        } else {
            "第${item.numberOfSeasons}季·${item.releaseDate.take(4)}"
        }
    } else {
        item.releaseDate
    }

    val score = try {
        item.voteAverage.toDoubleOrNull()?.toFloat()?.let { "%.1f".format(it) } ?: "0.0"
    } catch (e: Exception) {
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
        isAlreadyWatched = item.watched == 1
    )
}

private fun convertPlayDetailToMediaData(item: PlayDetailResponse): MediaData {
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
        isAlreadyWatched = item.watched == 1
    )
}