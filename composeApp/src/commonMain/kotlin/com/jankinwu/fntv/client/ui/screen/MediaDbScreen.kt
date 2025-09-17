package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.jankinwu.fntv.client.ui.component.FilterButton
import com.jankinwu.fntv.client.ui.component.MoviePoster
import com.jankinwu.fntv.client.ui.component.ToastHost
import com.jankinwu.fntv.client.ui.component.rememberToastManager
import com.jankinwu.fntv.client.viewmodel.FavoriteViewModel
import com.jankinwu.fntv.client.viewmodel.MediaListViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import com.jankinwu.fntv.client.viewmodel.WatchedViewModel
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.FlyoutContainer
import io.github.composefluent.component.FlyoutPlacement
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
    val posterMinWidth = (128 * scaleFactor).dp
    val spacing = 24.dp
    val posterHeight = (253 * scaleFactor).dp
    val refreshState = LocalRefreshState.current
    var isLoadingMore by remember { mutableStateOf(false) }
    var screenWidthPx by remember { mutableIntStateOf(0) } // 以像素为单位存储宽度
    val density = LocalDensity.current // 获取当前密度
    val toastManager = rememberToastManager()
    val favoriteViewModel: FavoriteViewModel = koinViewModel<FavoriteViewModel>()
    val favoriteUiState by favoriteViewModel.uiState.collectAsState()
    val watchedViewModel: WatchedViewModel = koinViewModel<WatchedViewModel>()
    val watchedUiState by watchedViewModel.uiState.collectAsState()
    var pendingCallbacks by remember { mutableStateOf<Map<String, (Boolean) -> Unit>>(emptyMap()) }
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
            // 重置滚动位置到顶部
            gridState.scrollToItem(0)
            // 执行当前页面的特定刷新逻辑
            mediaListViewModel.loadData(
                guid = mediaDbGuid,
                typeList = FnTvMediaType.getAll(),
                pageSize = 50
            )
        }
    }

    // 监听收藏操作结果并显示提示
    LaunchedEffect(favoriteUiState) {
        when (val state = favoriteUiState) {
            is UiState.Success -> {
                toastManager.showToast(state.data.message, state.data.success)
                // 调用对应的回调函数
                pendingCallbacks[state.data.guid]?.invoke(state.data.success)
                // 从 pendingCallbacks 中移除已处理的回调
                pendingCallbacks = pendingCallbacks - state.data.guid
            }

            is UiState.Error -> {
                // 显示错误提示
                toastManager.showToast("操作失败，${state.message}", false)
                state.operationId?.let {
                    pendingCallbacks[state.operationId]?.invoke(false)
                    // 从 pendingCallbacks 中移除已处理的回调
                    pendingCallbacks = pendingCallbacks - state.operationId
                }
            }

            else -> {}
        }

        // 清除状态
        if (favoriteUiState is UiState.Success || favoriteUiState is UiState.Error) {
            kotlinx.coroutines.delay(2000) // 2秒后清除状态
            favoriteViewModel.clearError()
        }
    }

    // 监听已观看操作结果并显示提示
    LaunchedEffect(watchedUiState) {
        when (val state = watchedUiState) {
            is UiState.Success -> {
                toastManager.showToast(state.data.message, state.data.success)
                // 调用对应的回调函数
                pendingCallbacks[state.data.guid]?.invoke(state.data.success)
                // 从 pendingCallbacks 中移除已处理的回调
                pendingCallbacks = pendingCallbacks - state.data.guid
            }

            is UiState.Error -> {
                // 显示错误提示
                toastManager.showToast("操作失败，${state.message}", false)
                state.operationId?.let {
                    pendingCallbacks[state.operationId]?.invoke(false)
                    // 从 pendingCallbacks 中移除已处理的回调
                    pendingCallbacks = pendingCallbacks - state.operationId
                }
            }

            else -> {}
        }

        // 清除状态
        if (watchedUiState is UiState.Success || watchedUiState is UiState.Error) {
            kotlinx.coroutines.delay(2000) // 2秒后清除状态
            watchedViewModel.clearError()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .onSizeChanged {
                    screenWidthPx = it.width
                },
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = LocalTypography.current.subtitle,
                color = FluentTheme.colors.text.text.tertiary,
                modifier = Modifier
                    .padding(top = 36.dp, start = 32.dp, bottom = 32.dp)
            )
            var isSelected by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier.padding(start = 32.dp, bottom = 16.dp)
            ) {
                FlyoutContainer(
                    flyout = {
                        Column {


                        }
                    },
                    content = {
                        FilterButton(
                            isSelected = isSelected,
                            onClick = {
                                isSelected = !isSelected
                                isFlyoutVisible = isSelected
                            }
                        )
                    },
                    placement = FlyoutPlacement.BottomAlignedEnd,
                    focusable = false
                )
            }
            ScrollbarContainer(
                adapter = rememberScrollbarAdapter(gridState)
            ) {
                when (val state = mediaListUiState) {
                    is UiState.Success -> {
                        val mediaItems = state.data.list

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(spanCount),
                            state = gridState,
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
//                            .padding(top = 12.dp)
                            ,
                            horizontalArrangement = Arrangement.spacedBy(spacing),
                            verticalArrangement = Arrangement.spacedBy(spacing)
                        ) {
                            items(mediaItems) { mediaItem ->
                                val itemData = convertToScrollRowItemData(mediaItem)
                                Box(
                                    modifier = Modifier
                                        .height(posterHeight)
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    MoviePoster(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        title = itemData.title,
                                        subtitle = itemData.subtitle,
                                        score = itemData.score,
                                        posterImg = itemData.posterImg,
                                        isFavorite = itemData.isFavourite,
                                        isAlreadyWatched = itemData.isAlreadyWatched,
                                        resolutions = itemData.resolutions,
                                        guid = itemData.guid,
                                        onFavoriteToggle = { guid, currentFavoriteState, resultCallback ->
                                            // 保存回调函数
                                            pendingCallbacks =
                                                pendingCallbacks + (guid to resultCallback)
                                            // 调用 ViewModel 方法
                                            favoriteViewModel.toggleFavorite(
                                                guid,
                                                currentFavoriteState
                                            )
                                        },
                                        onWatchedToggle = { guid, currentWatchedState, resultCallback ->
                                            // 保存回调函数
                                            pendingCallbacks =
                                                pendingCallbacks + (guid to resultCallback)
                                            // 调用 ViewModel 方法
                                            watchedViewModel.toggleWatched(
                                                guid,
                                                currentWatchedState
                                            )
                                        }
                                    )
                                }

                            }
                        }
                    }

                    is UiState.Error -> {
                        // 显示错误信息
                        toastManager.showToast("获取媒体列表失败, cause: ${state.message}", false, 10000)
                    }

                    else -> {
                        // 初始状态或其他状态
                    }
                }
            }
        }
        ToastHost(
            toastManager = toastManager,
            modifier = Modifier.fillMaxSize()
        )
    }
}
