package com.jankinwu.fntv.client.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalWindowSize

/**
 * 电影海报组件
 *
 * @param modifier The modifier to be applied to the component.
 * @param title 电影标题 (第一行文字)
 * @param subtitle 电影副标题或描述 (第二行文字)
 * @param score 电影评分
 * @param quality 视频画质, 如 "4K", "1080p"
 */
@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    score: String,
    quality: String,
) {
    // 从 CompositionLocal 中获取当前窗口大小
    val windowSize = LocalWindowSize.current
    // 根据窗口的较小维度计算一个基础尺寸
    val baseSize = windowSize.width

    // 根据基础尺寸动态计算海报的宽度和高度，保持 2:3 的宽高比
    val posterWidth = if (baseSize * 0.08f >= 150.dp) baseSize * 0.08f else 150.dp
    val posterHeight = posterWidth * 1.5f

    // 根据海报宽度计算缩放比例，用于动态调整字体、圆角等
    val scaleFactor = (posterWidth / 200.dp).coerceAtLeast(0.5f)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 海报图片和覆盖层的容器
        Box(
            modifier = Modifier
                .width(posterWidth)
                .height(posterHeight)
                .clip(RoundedCornerShape((8 * scaleFactor).dp)) // 给图片容器添加圆角
        ) {
            // 电影海报图片
            // 在实际应用中，您会使用 painterResource 或像 Coil/Kamel 这样的库来加载网络图片
            Image(
                // 这里使用一个灰色占位符代替真实图片
                painter = ColorPainter(Color.Gray),
                contentDescription = "$title Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 左上角评分
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = RoundedCornerShape((4 * scaleFactor).dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = score,
                    color = Color(0xFFFBBF24), // 黄色
                    fontSize = (14 * scaleFactor).sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 右下角画质
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding((8 * scaleFactor).dp)
                    .background(
                        color = Color.White.copy(alpha = 0.6f),
                        shape = RoundedCornerShape((4 * scaleFactor).dp)
                    )
                    .padding(horizontal = (6 * scaleFactor).dp, vertical = (2 * scaleFactor).dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quality,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // 图片下方的间距
        Spacer(Modifier.height((8 * scaleFactor).dp))

        // 电影标题
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = (16 * scaleFactor).sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        // 副标题/描述
        Spacer(Modifier.height((4 * scaleFactor).dp))
        Text(
            text = subtitle,
            fontSize = (14 * scaleFactor).sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}


// --- Main function for demonstration ---
// 您可以将此部分代码复制到您的 Compose Multiplatform 项目中来预览效果
//fun main() = application {
//    Window(
//        onCloseRequest = ::exitApplication,
//        title = "Movie Poster Demo",
//        state = rememberWindowState(
//            width = 300.dp,
//            height = 500.dp,
//            position = WindowPosition(Alignment.Center)
//        )
//    ) {
//        MaterialTheme {
//            Surface(
//                modifier = Modifier.fillMaxSize(),
//                color = Color(0xFFF0F0F0) // 设置一个浅灰色背景
//            ) {
//                // 将组件居中显示以供预览
//                Box(
//                    contentAlignment = Alignment.Center,
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    MoviePoster(
//                        title = "电影标题",
//                        subtitle = "一些简短的描述",
//                        score = "9.1",
//                        quality = "4K"
//                    )
//                }
//            }
//        }
//    }
//}
