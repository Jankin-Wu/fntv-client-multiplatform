package com.jankinwu.fntv.client.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalWindowSize
import com.jankinwu.fntv.client.data.model.PosterData
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentColor
import io.github.composefluent.component.Icon
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.filled.IosArrowLtr
import io.github.composefluent.icons.filled.IosArrowRtl
import kotlinx.coroutines.launch

/**
 * 媒体库组件, 使用 LazyRow 横向展示多个 MoviePoster.
 *
 * @param modifier The modifier to be applied to the component.
 * @param movies 要展示的电影列表.
 */
@Suppress("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MediaLibGallery(
    modifier: Modifier = Modifier,
    title: String,
    movies: List<PosterData>
) {
    val listState = rememberLazyListState()


    Column(modifier = modifier) {
        // 媒体库标题行
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
                style = FluentTheme.typography.title.copy(fontSize = 18.sp, fontWeight = FontWeight.Black),
                color = Color.White
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Filled.IosArrowRtl,
                contentDescription = "View More",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(10.dp)
            )
        }

        MediaLibScrollRow(movies, listState)

    }
}

@Suppress("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MediaLibScrollRow(
    movies: List<PosterData>,
    listState: LazyListState,
) {
    val scope = rememberCoroutineScope()
    var isHovered by remember { mutableStateOf(false) }

    // 1. State to store the measured height in pixels
    var posterHeightPx by remember { mutableStateOf(0) }
    // Convert pixels to Dp for the modifier
    val posterHeightDp = with(LocalDensity.current) { posterHeightPx.toDp() }

    val canScrollForward by remember { derivedStateOf { listState.canScrollForward } }
    val canScrollBackward by remember { derivedStateOf { listState.canScrollBackward } }
    // 定义一个可重用的动画规格，使用 "先快后慢" 的缓动曲线
    val animationSpec = tween<Float>(
        durationMillis = 500,
        easing = FastOutSlowInEasing
    )
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (posterHeightDp > 0.dp) Modifier.height(posterHeightDp) else Modifier)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
    ) {
        val rowWidth = maxWidth
        val itemSpacing = 16.dp
        val horizontalPadding = 32.dp

        val windowSize = LocalWindowSize.current
        val baseSize = min(windowSize.width, windowSize.height)
        val posterWidth = baseSize * 0.6f
        val totalContentWidth = (posterWidth * movies.size) + (itemSpacing * (movies.size - 1))

        val isScrollable = totalContentWidth > (rowWidth - horizontalPadding * 2)

        // 横向滚动的电影海报列表
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(itemSpacing),
            verticalAlignment = Alignment.Top
        ) {
            itemsIndexed(movies) { index, movie ->
                MoviePoster(
                    modifier = if (index == 0) {
                        Modifier.onSizeChanged { posterHeightPx = it.height }
                    } else {
                        Modifier
                    },
                    title = movie.title,
                    subtitle = movie.subtitle,
                    score = movie.score,
                    quality = movie.quality,
                    posterImg = movie.posterImg
                )
            }
        }

        val scrollAmount = with(LocalDensity.current) { (rowWidth - horizontalPadding).toPx() * 0.8f }
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

/**
 * 用于媒体库左右滚动的导航按钮.
 *
 * @param onClick 按钮点击事件.
 * @param isLeft 是否是左侧按钮 (决定渐变方向和图标).
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScrollButton(onClick: () -> Unit, isLeft: Boolean, modifier: Modifier = Modifier) {
    var isIconHovered by remember { mutableStateOf(false) }

    // icon 缩放动画
    val iconSize by animateDpAsState(
        targetValue = if (isIconHovered) 24.dp else 16.dp,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "iconSize"
    )


    Box(
        modifier = modifier
            .width(30.dp)
            .fillMaxHeight()
            .background(
                FluentTheme.colors.controlOnImage.default.copy(alpha = 0.9f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isLeft) Icons.Filled.IosArrowLtr else Icons.Filled.IosArrowRtl,
            contentDescription = if (isLeft) "Scroll Left" else "Scroll Right",
            tint = LocalContentColor.current,
            modifier = Modifier
                .size(iconSize)
                .onPointerEvent(PointerEventType.Enter) { isIconHovered = true }
                .onPointerEvent(PointerEventType.Exit) { isIconHovered = false }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null, // 移除点击时的涟漪效果
                    onClick = onClick
                )
        )
    }
}
