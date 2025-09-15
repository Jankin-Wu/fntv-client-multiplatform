package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.jankinwu.fntv.client.LocalRefreshState
import com.jankinwu.fntv.client.LocalStore
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.convertor.convertToScrollRowItemData
import com.jankinwu.fntv.client.enums.FnTvMediaType
import com.jankinwu.fntv.client.ui.component.MoviePoster
import com.jankinwu.fntv.client.viewmodel.MediaListViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.ScrollbarContainer
import io.github.composefluent.component.Text
import io.github.composefluent.component.rememberScrollbarAdapter
import io.github.composefluent.gallery.component.ComponentNavigator
import org.koin.compose.viewmodel.koinViewModel

@Suppress("DefaultLocale")
@Composable
fun MediaDbScreen(mediaDbGuid: String, title: String, navigator: ComponentNavigator) {
    val mediaListViewModel: MediaListViewModel = koinViewModel<MediaListViewModel>()
    val mediaListUiState by mediaListViewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()
    val store = LocalStore.current
    val scaleFactor = store.scaleFactor
    var posterWidthPx by remember { mutableIntStateOf(0) }
    val posterWidthDp = with(LocalDensity.current) { posterWidthPx.toDp() }
    var posterHeightPx by remember { mutableIntStateOf(0) }
    val posterHeightDp = with(LocalDensity.current) { posterHeightPx.toDp() }
    val posterMinWidth = (128 * scaleFactor).dp
    val spacing = 24.dp
    val posterHeight = (240 * scaleFactor).dp
    val refreshState = LocalRefreshState.current
    var isLoadingMore by remember { mutableStateOf(false) }
    var screenWidthPx by remember { mutableIntStateOf(0) } // 以像素为单位存储宽度
    val density = LocalDensity.current // 获取当前密度

    // 计算 screenWidth（dp单位）
    var screenWidth by remember(screenWidthPx, density) {
        mutableStateOf(with(density) { screenWidthPx.toDp() })
    }

    // 计算每行显示的海报数量
    val spanCount = maxOf(1, (screenWidth / (posterMinWidth + spacing)).toInt())

    LaunchedEffect(mediaDbGuid) {
        // 初始加载第一页数据
        mediaListViewModel.loadData(
            guid = mediaDbGuid,
            typeList = FnTvMediaType.getAll(),
            pageSize = 50
        )
    }
    // 监听滚动位置，实现懒加载
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo }
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                // 当滚动到距离底部还有5个item时加载下一页
                if (totalItems > 0 && lastVisibleItemIndex >= totalItems - 5) {
                    // 检查是否已经在加载或已到最后一页
                    val currentState = mediaListUiState
                    if (currentState !is UiState.Loading && !isLoadingMore && !mediaListViewModel.isLastPage) {
                        isLoadingMore = true
                        mediaListViewModel.loadMoreData(
                            guid = mediaDbGuid,
                            typeList = FnTvMediaType.getAll(),
                            pageSize = 50
                        )
                        // 延迟重置加载状态，避免过于频繁的请求
                        kotlinx.coroutines.delay(500)
                        isLoadingMore = false
                    }
                }
            }
    }

    // 监听刷新状态变化
    LaunchedEffect(refreshState.refreshKey) {
        // 当刷新状态变化时执行刷新逻辑
        if (refreshState.refreshKey.isNotEmpty()) {
            refreshState.onRefresh()
            // 执行当前页面的特定刷新逻辑
            mediaListViewModel.loadData(
                guid = mediaDbGuid,
                typeList = FnTvMediaType.getAll(),
                pageSize = 50
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .onSizeChanged {
                screenWidthPx = it.width
            }
                ,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = LocalTypography.current.subtitle,
            color = FluentTheme.colors.text.text.tertiary,
            modifier = Modifier
                .padding(top = 36.dp, start = 32.dp)
        )

        when (val state = mediaListUiState) {
            is UiState.Loading -> {
                // 显示加载指示器
                // TODO: 添加加载指示器
            }

            is UiState.Success -> {
                val mediaItems = state.data.list

                LazyVerticalGrid(
                    columns = GridCells.Fixed(spanCount),
                    state = gridState,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    items(mediaItems) { mediaItem ->
                        val itemData = convertToScrollRowItemData(mediaItem)
                        Box(
                            modifier = Modifier
                                .height(posterHeight)
                                .fillMaxSize()
//                                .width(posterMinWidth)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            MoviePoster(
                                modifier = Modifier
                                    .fillMaxHeight()
//                                    .width(posterMinWidth)
                                    .onSizeChanged {
                                        if (posterWidthPx != it.width) {
                                            posterWidthPx = it.width
                                            posterHeightPx = it.height
                                        }
                                        println("scaleFactor: $scaleFactor")
                                        println("posterMinWidth: $posterMinWidth")
                                        println("posterActualWidthDp: $posterWidthDp")
                                        println("posterHeight: $posterHeight")
                                        println("posterActualHeight: $posterHeightDp")
                                        println("spanCount: $spanCount")
                                        println("screenWidth: $screenWidth")
                                    },
                                title = itemData.title,
                                subtitle = itemData.subtitle,
                                score = itemData.score,
                                posterImg = itemData.posterImg,
                                isFavorite = itemData.isFavourite,
                                isAlreadyWatched = itemData.isAlreadyWatched,
                                resolutions = itemData.resolutions,
                                guid = itemData.guid,
                                onFavoriteToggle = { guid, isFavorite, callback ->
                                    // TODO: 实现收藏切换逻辑
                                    callback(true)
                                },
                                onWatchedToggle = { guid, isWatched, callback ->
                                    // TODO: 实现观看状态切换逻辑
                                    callback(true)
                                }
                            )
                        }

                    }

                    // 添加加载更多指示器
                    if (state is UiState.Loading) {
                        item {
                            // TODO: 添加底部加载指示器
                        }
                    }
                }
            }

            is UiState.Error -> {
                // 显示错误信息
                // TODO: 添加错误处理UI
            }

            else -> {
                // 初始状态或其他状态
            }
        }
    }

}

