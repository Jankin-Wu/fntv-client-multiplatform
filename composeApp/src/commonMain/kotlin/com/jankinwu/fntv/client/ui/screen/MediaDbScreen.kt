package com.jankinwu.fntv.client.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalRefreshState
import com.jankinwu.fntv.client.LocalStore
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.convertor.convertToScrollRowItemData
import com.jankinwu.fntv.client.enums.FnTvMediaType
import com.jankinwu.fntv.client.icons.ArrowUp
import com.jankinwu.fntv.client.ui.component.MoviePoster
import com.jankinwu.fntv.client.ui.component.ToastHost
import com.jankinwu.fntv.client.ui.component.rememberToastManager
import com.jankinwu.fntv.client.viewmodel.FavoriteViewModel
import com.jankinwu.fntv.client.viewmodel.MediaListViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import com.jankinwu.fntv.client.viewmodel.WatchedViewModel
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.FlyoutContainer
import io.github.composefluent.component.Icon
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
            var chipSelected by remember { mutableStateOf(false) }
            Row(

            ) {
                FlyoutContainer(
                    flyout = {
                        Column {


                        }
                    },
                    content = {
                        FilterButton(
                            isSelected = chipSelected,
                            onClick = {
                                chipSelected = !chipSelected
                                isFlyoutVisible = !isFlyoutVisible
                            })
                    }
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
                        // TODO: 添加错误处理UI
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


@Composable
fun FilterButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent
    )

    //    当 isSelected 为 true 时，目标角度是 180 度，否则是 0 度
    val rotationAngle by animateFloatAsState(
        targetValue = if (isSelected) 180f else 0f
    )

    // 4. 使用 Row 布局来水平排列文本和图标
    Row(
        modifier = Modifier
            // (a) 设置圆角矩形裁剪，使其成为一个胶囊形状
            .clip(CircleShape)
            // (b) 应用我们上面定义的动画背景色
            .background(backgroundColor)
            // (c) 添加点击事件，点击时翻转 isSelected 的状态
            .clickable(onClick = onClick)
            // (d) 添加内边距，让内容和边框之间有一些空间
            .padding(horizontal = 16.dp, vertical = 8.dp),
        // 垂直居中对齐 Row 里面的所有内容
        verticalAlignment = Alignment.CenterVertically,
        // 水平居中对齐
        horizontalArrangement = Arrangement.Center
    ) {
        // 显示文本
        Text(
            text = "筛选",
            color = Color.White,
            fontSize = 16.sp
        )
        // 在文本和图标之间添加一点点间距
        Spacer(modifier = Modifier.width(4.dp))
        // 显示图标
        Icon(
            imageVector = ArrowUp, // 使用 Material Design 的向上箭头图标
            contentDescription = "Filter Arrow", // 给辅助功能使用
            tint = Color.White, // 设置图标颜色为白色
            modifier = Modifier
                .size(24.dp)
                // (e) 应用我们上面定义的旋转动画
                .rotate(rotationAngle)
        )
    }
}
