package com.jankinwu.fntv.client.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalStore
import com.jankinwu.fntv.client.icons.Delete
import com.jankinwu.fntv.client.icons.Edit
import com.jankinwu.fntv.client.icons.HeartFilled
import com.jankinwu.fntv.client.icons.Lifted
import com.lt.load_the_image.rememberImagePainter
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.FlyoutPlacement
import io.github.composefluent.component.MenuFlyoutContainer
import io.github.composefluent.component.MenuFlyoutItem
import io.github.composefluent.component.MenuFlyoutSeparator
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.Checkmark
import io.github.composefluent.icons.regular.MoreHorizontal
import io.github.composefluent.icons.regular.PlayCircle

/**
 * 电影海报组件
 *
 * @param modifier The modifier to be applied to the component.
 * @param title 电影标题 (第一行文字)
 * @param subtitle 电影副标题或描述 (第二行文字)
 * @param score 电影评分
 * @param quality 视频画质, 如 "4K", "1080p"
 */
@Suppress("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    score: String,
    posterImg: String,
    isFavorite: Boolean = false,
    isAlreadyWatched: Boolean = false,
    resolutions: List<String> = listOf(),
) {
    val scaleFactor = LocalStore.current.scaleFactor

    var isPosterHovered by remember { mutableStateOf(false) }
    var isPlayButtonHovered by remember { mutableStateOf(false) }

    var normalPlayButtonSize by remember(scaleFactor) { mutableStateOf((48 * scaleFactor).dp) }
    var hoveredPlayButtonSize by remember(scaleFactor) { mutableStateOf((56 * scaleFactor).dp) }
    // 播放按钮动画大小状态
    val playButtonSize by animateDpAsState(
        targetValue = if (isPlayButtonHovered) hoveredPlayButtonSize else normalPlayButtonSize,
        animationSpec = tween(durationMillis = 200),
        label = "playButtonSize"
    )
    // 收藏状态
    var isFavorite by remember(isFavorite) { mutableStateOf(isFavorite) }
    var isAlreadyWatched by remember(isAlreadyWatched) { mutableStateOf(isAlreadyWatched) }

    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 海报图片和覆盖层的容器
        BoxWithConstraints(
            modifier = Modifier
                .aspectRatio(2f / 3f)
                .weight(1f)
                .clip(RoundedCornerShape((8 * scaleFactor).dp))
                .onPointerEvent(PointerEventType.Enter) { isPosterHovered = true }
                .onPointerEvent(PointerEventType.Exit) { isPosterHovered = false }
        ) {
            // 电影海报图片
            Image(
                painter = if (posterImg.isBlank()) ColorPainter(Color.Gray) else rememberImagePainter(
                    posterImg
                ),
                contentDescription = "$title Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 左上角评分
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = RoundedCornerShape((4 * scaleFactor).dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = score,
                    color = Color(0xFFFBBF24), // 黄色
                    fontSize = (12 * scaleFactor).sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            // 右下角画质
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = (6 * scaleFactor).dp, bottom = (6 * scaleFactor).dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = (4 * scaleFactor).dp, alignment = Alignment.End)
            ) {
                for ((_, resolution) in resolutions.withIndex()) {
                    if (resolution.endsWith("K")) {
                        Box(
                            modifier = Modifier
                                .alpha(if (isPosterHovered) 0f else 1f)
//                                .align(Alignment.BottomEnd)
//                                .padding((8 * scaleFactor).dp)
                                .background(
                                    color = Color.White.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape((4 * scaleFactor).dp)
                                )
                                .padding(
                                    horizontal = (6 * scaleFactor).dp,
                                    vertical = (1 * scaleFactor).dp
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = resolution,
                                color = Color.Black.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .alpha(if (isPosterHovered) 0f else 1f)
//                                .align(Alignment.BottomEnd)
//                                .padding((8 * scaleFactor).dp)
                                .border(
                                    1.dp,
                                    Color.White.copy(alpha = 0.6f),
                                    RoundedCornerShape((4 * scaleFactor).dp)
                                )
                                .padding(
                                    horizontal = (6 * scaleFactor).dp,
                                    vertical = (1 * scaleFactor).dp
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = resolution,
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
            // 半透明遮罩层
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FluentTheme.colors.controlOnImage.default.copy(alpha = if (isPosterHovered) 0.4f else 0f))
                    .alpha(if (isPosterHovered) 1f else 0f)
            )

            // 播放按钮
            Icon(
                imageVector = Icons.Regular.PlayCircle,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(playButtonSize)
                    .alpha(if (isPosterHovered) 1f else 0f)
                    .onPointerEvent(PointerEventType.Enter) { isPlayButtonHovered = true }
                    .onPointerEvent(PointerEventType.Exit) { isPlayButtonHovered = false }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        // TODO: 处理播放按钮点击事件
                    }
            )

            // 标记为已观看按钮
            BottomIconButton(
                modifier = Modifier
                    .alpha(if (isPosterHovered) 1f else 0f)
                    .padding((8 * scaleFactor).dp)
                    .align(Alignment.BottomStart),
                icon = Icons.Regular.Checkmark,
                contentDescription = "alreadyWatched",
                onClick = {
                    isAlreadyWatched = !isAlreadyWatched
                    // TODO: 处理标记为已观看按钮点击事件
                },
                scaleFactor = scaleFactor,
                iconTint = if (isAlreadyWatched) Color.Green else Color.White
            )

            // 收藏按钮
            BottomIconButton(
                modifier = Modifier
                    .alpha(if (isPosterHovered) 1f else 0f)
                    .padding((8 * scaleFactor).dp)
                    .align(Alignment.BottomCenter),
                icon = HeartFilled,
                contentDescription = "collection",
                onClick = {
                    isFavorite = !isFavorite
                    // TODO: 处理收藏按钮点击事件
                },
                scaleFactor = scaleFactor,
                iconTint = if (isFavorite) Color.Red else Color.White
            )

            Box(
                modifier = Modifier
                    .alpha(if (isPosterHovered) 1f else 0f)
                    .padding((8 * scaleFactor).dp)
                    .align(Alignment.BottomEnd)
            ) {
                MenuFlyoutContainer(
                    flyout = {
                        MenuFlyoutItem(
                            text = {
                                Text(
                                    "手动匹配影片",
                                    fontSize = (12 * scaleFactor).sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FluentTheme.colors.text.text.tertiary
                                )
                            },
                            onClick = {
                                isFlyoutVisible = false
                                // TODO: 处理手动匹配影片按钮点击事件
                            },
                            icon = {
                                Icon(
                                    Edit,
                                    contentDescription = "手动匹配影片",
                                    tint = FluentTheme.colors.text.text.tertiary,
                                    modifier = Modifier.requiredSize((20 * scaleFactor).dp)
                                )
                            })
                        MenuFlyoutItem(
                            text = {
                                Text(
                                    "解除匹配影片",
                                    fontSize = (12 * scaleFactor).sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FluentTheme.colors.text.text.tertiary
                                )
                            },
                            onClick = {
                                isFlyoutVisible = false
                                // TODO: 处理解除匹配影片按钮点击事件
                            },
                            icon = {
                                Icon(
                                    Lifted,
                                    tint = FluentTheme.colors.text.text.tertiary,
                                    contentDescription = "解除匹配影片",
                                    modifier = Modifier.requiredSize((20 * scaleFactor).dp)
                                )
                            })
                        MenuFlyoutSeparator(modifier = Modifier.padding(horizontal = 1.dp))
                        MenuFlyoutItem(
                            text = {
                                Text(
                                    "删除",
                                    fontSize = (12 * scaleFactor).sp,
                                    color = FluentTheme.colors.text.text.tertiary,
                                    fontWeight = FontWeight.Bold,
                                )
                            },
                            onClick = {
                                isFlyoutVisible = false
                                // TODO: 处理删除按钮点击事件
                            },
                            icon = {
                                Icon(
                                    Delete,
                                    tint = FluentTheme.colors.text.text.tertiary,
                                    contentDescription = "删除",
                                    modifier = Modifier.requiredSize((20 * scaleFactor).dp)
                                )
                            })
                    },
                    content = {
                        BottomIconButton(
                            icon = Icons.Regular.MoreHorizontal,
                            contentDescription = "more",
                            onClick = {
                                isFlyoutVisible = !isFlyoutVisible
                            },
                            scaleFactor = scaleFactor
                        )
                    },
                    adaptivePlacement = true,
                    placement = FlyoutPlacement.BottomAlignedEnd
                )
            }

        }

        // 图片下方的间距
        Spacer(Modifier.height((8 * scaleFactor).dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 电影标题
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = (14 * scaleFactor).sp,
                textAlign = TextAlign.Center,
                color = FluentTheme.colors.text.text.primary
            )

            // 副标题/描述
            Spacer(Modifier.height((4 * scaleFactor).dp))
            Text(
                text = subtitle,
                fontSize = (12 * scaleFactor).sp,
                textAlign = TextAlign.Center,
                color = FluentTheme.colors.text.text.tertiary
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BottomIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    scaleFactor: Float,
    iconTint: Color = Color.White
) {
    var isHovered by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
    ) {
        // 悬停时显示的圆形背景
        Box(
            modifier = Modifier
                .size((24 * scaleFactor).dp)
                .align(Alignment.Center)
                .background(
                    color = if (isHovered) Color.Gray.copy(alpha = 0.6f) else Color.Transparent,
                    shape = CircleShape
                )
        )

        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier
                .size((16 * scaleFactor).dp)
                .align(Alignment.Center)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onClick() }
                )
        )
    }
}
