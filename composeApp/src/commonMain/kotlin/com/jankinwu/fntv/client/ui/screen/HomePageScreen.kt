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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.convertor.convertMediaDbListResponseToMediaData
import com.jankinwu.fntv.client.data.convertor.convertPlayDetailToMediaData
import com.jankinwu.fntv.client.data.convertor.convertToMediaData
import com.jankinwu.fntv.client.enums.FnTvMediaType
import com.jankinwu.fntv.client.ui.component.MediaLibCardRow
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
import io.github.composefluent.gallery.component.ComponentNavigator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomePageScreen(navigator: ComponentNavigator) {
    val mediaDbListViewModel: MediaDbListViewModel = koinViewModel<MediaDbListViewModel>()
    val mediaDbUiState by mediaDbListViewModel.uiState.collectAsState()
    val playListViewModel: PlayListViewModel = koinViewModel<PlayListViewModel>()
    val playListUiState by playListViewModel.uiState.collectAsState()

    val lazyListState = rememberLazyListState()
    LaunchedEffect(Unit) {
        // 检查数据是否已加载，避免重复请求
        if (playListUiState !is UiState.Success) {
            playListViewModel.loadData()
        }
        if (mediaDbUiState !is UiState.Success) {
            mediaDbListViewModel.loadData()
        }
    }
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
                    when (val mediaDbState = mediaDbUiState) {
                        is UiState.Success -> {
                            // 转换 MediaItem 到 MediaData
                            val mediaData = mediaDbState.data.map { item ->
                                convertMediaDbListResponseToMediaData(item)
                            }
                            MediaLibCardRow(
                                mediaLibs = mediaData,
                                title = "媒体库"
                            )
                        }

                        else -> {}
                    }
                }
                item {
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
                            val mediaListViewModel: MediaListViewModel =
                                koinViewModel(key = mediaLib.guid)
                            val mediaListUiState = mediaListViewModel.uiState.collectAsState().value
                            // 只在数据未加载时请求数据
                            LaunchedEffect(mediaLib.guid) {
                                if (mediaListUiState !is UiState.Success) {
                                    mediaListViewModel.loadData(
                                        mediaLib.guid,
                                        FnTvMediaType.getAll()
                                    )
                                }
                            }

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