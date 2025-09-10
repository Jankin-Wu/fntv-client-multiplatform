package com.jankinwu.fntv.client.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.PlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.model.SystemAccountData
import io.github.composefluent.FluentTheme

/**
 * 一个可重用的媒体库卡片组件，用于展示拼接的海报、倒影和标题。
 *
 * @param posters 要展示的海报 Painter 列表。最多会显示前4张。
 * @param title 媒体库的标题，会显示在下方的半透明遮罩上。
 * @param modifier 应用于此组件的 Modifier。
 * @param cornerRadius 卡片的圆角半径。
 * @param elevation 卡片的阴影深度。
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MediaLibraryCard(
    modifier: Modifier = Modifier,
    posters: List<String>? = listOf(),
    title: String,
    cornerRadius: Dp = 12.dp,
    elevation: Dp = 8.dp
) {
    // 最多取前4张海报
    val visiblePosters = posters?.take(4)
    val posterCount = visiblePosters?.size

    // 如果没有海报，则不显示任何内容
    if (posterCount == 0) return

    Box(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(3f/2f)
            .background(Color.Transparent, RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .border(1.dp, Color.Gray, RoundedCornerShape(cornerRadius)),
    ) {
        var isPosterHovered by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onPointerEvent(PointerEventType.Enter) { isPosterHovered = true }
                .onPointerEvent(PointerEventType.Exit) { isPosterHovered = false }
                ,
            contentAlignment = Alignment.Center
        ) {
            // 包含上半部分图片和下半部分倒影的列布局
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .background(FluentTheme.colors.background.card.tertiary.copy(alpha = 0.1f), RoundedCornerShape(cornerRadius - 2.dp))
            ) {
                // 上半部分: 原始海报行
                PosterRow(
                    posters = visiblePosters,
                    modifier = Modifier.weight(1f)
                )
                // 下半部分: 垂直翻转的海报行 (倒影)
                PosterRow(
                    posters = visiblePosters,
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer {
                            // 沿Y轴缩放-1倍以实现垂直翻转
                            scaleY = -1f
                        }
                )
            }

            // 位于下半部分的半透明遮罩和标题
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .background(FluentTheme.colors.controlOnImage.default.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = LocalTypography.current.caption,
                    color = FluentTheme.colors.text.text.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            // 半透明遮罩层
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FluentTheme.colors.controlOnImage.default.copy(alpha = if (isPosterHovered) 0.4f else 0f))
                    .alpha(if (isPosterHovered) 1f else 0f)
            )
        }
    }
}

/**
 * 一个私有的辅助组件，用于渲染一行水平拼接的海报。
 */
@Composable
private fun PosterRow(posters: List<String>?, modifier: Modifier = Modifier) {
    val headers = NetworkHeaders.Builder()
        .set("Cookie", SystemAccountData.cookie)
        .set("Accept", "Accept: */*")
        .build()
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        posters?.forEach { poster ->
            println("whole poster url: ${SystemAccountData.fnOfficialBaseUrl}/v/api/v1/sys/img$poster")
            SubcomposeAsyncImage(
//                model = if (poster.isBlank()) "" else "${SystemAccountData.fnOfficialBaseUrl}/v/api/v1/sys/img$poster",
                model = ImageRequest.Builder(PlatformContext.INSTANCE)
                    .data("${SystemAccountData.fnOfficialBaseUrl}/v/api/v1/sys/img$poster")
                    .httpHeaders(headers)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f / 2f)
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop,
                onError = { result ->
//                    println("图片加载异常，错误原因: $result")
//                    println("AsyncImage Throwable: ${result.result.throwable}")
//                    result.result.throwable.printStackTrace()
                },
                onLoading = {
                    println("图片加载中...")
                },
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    println("图片加载失败")
                }
            )
    //            Image(
    //                painter = poster,
    //                contentDescription = null, // 装饰性图片，无需内容描述
    //                modifier = Modifier
    //                    .weight(1f)
    //                    .fillMaxHeight(),
    //                // FillBounds会拉伸图片填满分配的空间，确保无缝拼接
    //                contentScale = ContentScale.FillBounds
    //            )
        }
    }
}