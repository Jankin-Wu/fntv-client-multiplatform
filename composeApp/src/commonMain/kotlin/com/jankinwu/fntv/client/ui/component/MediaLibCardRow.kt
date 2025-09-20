package com.jankinwu.fntv.client.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalStore
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.model.ScrollRowItemData
import io.github.composefluent.FluentTheme
import io.github.composefluent.gallery.component.ComponentNavigator
import kotlinx.coroutines.launch

@Composable
fun MediaLibCardRow(
    mediaLibs: List<ScrollRowItemData>,
    title: String,
    modifier: Modifier = Modifier,
    onItemClick: ((ScrollRowItemData) -> Unit)? = null,
    navigator: ComponentNavigator? = null
) {
    val scaleFactor = LocalStore.current.scaleFactor
    // 设置媒体库卡片行高度
    val mediaLibCardColumnHeight = (160 * scaleFactor).dp

    Column(
        modifier = modifier
            .height(mediaLibCardColumnHeight),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(start = 32.dp, bottom = 12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /* TODO: Handle navigation for this category */ }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = LocalTypography.current.title.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = FluentTheme.colors.text.text.tertiary
            )
        }

        MediaLibScrollRow(
            mediaLibs, { index, mediaLib, modifier, nav ->
                MediaLibraryCard(
                    title = mediaLib.title,
                    posters = mediaLib.posters,
                    modifier = modifier.clickable(
                        enabled = onItemClick != null,
                        onClick = { onItemClick?.invoke(mediaLib) }
                    ),
                    index = index,
                    guid = mediaLib.guid,
                    navigator = nav
                )
            },
            width = mediaLibCardColumnHeight / 2 * 3,
            navigator = navigator
        )

    }
}

@Suppress("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MediaLibScrollRow(
    itemsData: List<ScrollRowItemData>,
    item: @Composable (index: Int, movie: ScrollRowItemData, modifier: Modifier, navigator: ComponentNavigator?) -> Unit,
    width: Dp,
    navigator: ComponentNavigator? = null
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isHovered by remember { mutableStateOf(false) }
    val canScrollForward by remember { derivedStateOf { listState.canScrollForward } }
    val canScrollBackward by remember { derivedStateOf { listState.canScrollBackward } }
    // 定义一个可重用的动画规格，使用 "先快后慢" 的缓动曲线
    val animationSpec = tween<Float>(
        durationMillis = 100,
        easing = FastOutSlowInEasing
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
    ) {
        val rowWidth = maxWidth
        val itemSpacing = 24.dp
        val horizontalPadding = 32.dp

        val totalContentWidth by remember(width) { mutableStateOf((width * itemsData.size) + (itemSpacing * (itemsData.size - 1))) }

        val isScrollable by remember(
            totalContentWidth,
            rowWidth
        ) { mutableStateOf(totalContentWidth > (rowWidth - horizontalPadding * 2)) }

        // 横向滚动的电影海报列表
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = horizontalPadding),
//            horizontalArrangement = Arrangement.spacedBy(0.dp), // 使用0间距，手动控制间距
            verticalAlignment = Alignment.Top
        ) {
            itemsIndexed(
                items = itemsData,
                key = { _, item -> item.guid }// 使用 GUID 作为 key
            ) { index, movie ->
                val itemModifier = Modifier
                    .fillMaxHeight()
                    // 使用 visibleIndex 来决定间距，确保只有可见项目之间有间距
                    .padding(start = if (index > 0) itemSpacing else 0.dp)

                item(
                    index,
                    movie,
                    itemModifier,
                    navigator
                )
            }
        }

        val scrollAmount =
            with(LocalDensity.current) { (rowWidth - horizontalPadding).toPx() * 0.8f }
        // 定义滚动动画规格
        val scrollAnimationSpec = tween<Float>(
            durationMillis = 1000, // 1秒持续时间
            easing = FastOutSlowInEasing,
            delayMillis = 0
        )
        // 右侧滚动按钮
        AnimatedVisibility(
            visible = isHovered && canScrollForward && isScrollable,
            modifier = Modifier.align(Alignment.CenterEnd),
            enter = fadeIn(animationSpec = animationSpec),
            exit = fadeOut(animationSpec = animationSpec)
        ) {
            ScrollButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollBy(scrollAmount, scrollAnimationSpec)
                    }
                },
                isLeft = false,
                modifier = Modifier.fillMaxHeight()
            )
        }

        // 左侧滚动按钮
        AnimatedVisibility(
            visible = isHovered && canScrollBackward && isScrollable,
            modifier = Modifier.align(Alignment.CenterStart),
            enter = fadeIn(animationSpec = animationSpec),
            exit = fadeOut(animationSpec = animationSpec)
        ) {
            ScrollButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollBy(-scrollAmount, scrollAnimationSpec)
                    }
                },
                isLeft = true,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}