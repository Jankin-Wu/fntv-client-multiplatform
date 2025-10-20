package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.jankinwu.fntv.client.data.model.response.PlayInfoResponse
import com.jankinwu.fntv.client.icons.ArrowLeft
import com.jankinwu.fntv.client.ui.component.CastScrollRow
import com.jankinwu.fntv.client.ui.component.ComponentNavigator
import com.jankinwu.fntv.client.viewmodel.PlayInfoViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailScreen(
    guid: String,
    navigator: ComponentNavigator
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1C))
    ) {
        // 返回按钮
        IconButton(
            onClick = { navigator.navigateUp() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .pointerHoverIcon(PointerIcon.Hand)
        ) {
            Icon(
                imageVector = ArrowLeft,
                contentDescription = "返回",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        CastScrollRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            guid
        )


        // 电影详情内容
//        playDetail?.let { detail ->
////            MovieDetailContent(detail)
//        } ?: run {
//            // 加载状态
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "加载中...",
//                    color = Color.White,
//                    fontSize = 18.sp
//                )
//            }
//        }
    }
}

//@Composable
//fun MovieDetailContent(
//    detail: PlayDetailResponse
//) {
//    val scrollState = rememberScrollState()
//    val store = LocalStore.current
//    val scaleFactor = store.scaleFactor
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 64.dp)
//            .verticalScroll(scrollState)
//            .padding(16.dp)
//    ) {
//        // 电影海报和基本信息
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            // 电影海报
//            SubcomposeAsyncImage(
//                model = ImageRequest.Builder(PlatformContext.INSTANCE)
//                    .data("${AccountDataCache.getFnOfficialBaseUrl()}/v/api/v1/sys/img${detail.poster}${Constants.FN_IMG_URL_PARAM}")
//                    .httpHeaders(store.fnImgHeaders)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = detail.title,
//                modifier = Modifier
//                    .width(200.dp)
//                    .height(300.dp),
//                contentScale = ContentScale.Crop
//            )
//
//            // 基本信息
//            Column(
//                modifier = Modifier.weight(1f),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Text(
//                    text = detail.title,
//                    color = Color.White,
//                    fontSize = (24 * scaleFactor).sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                detail.tvTitle?.let { tvTitle ->
//                    if (tvTitle != detail.title) {
//                        Text(
//                            text = "来自: $tvTitle",
//                            color = Color.Gray,
//                            fontSize = (14 * scaleFactor).sp
//                        )
//                    }
//                }
//
//                // 评分
////                detail.mediaStream.score?.let { score ->
////                    if (score != "0.0") {
////                        Row(
////                            verticalAlignment = Alignment.CenterVertically,
////                            horizontalArrangement = Arrangement.spacedBy(4.dp)
////                        ) {
////                            Icon(
////                                imageVector = Icons.Regular.Star,
////                                contentDescription = "评分",
////                                tint = Color(0xFFFBBF24),
////                                modifier = Modifier.size(16.dp)
////                            )
////                            Text(
////                                text = score,
////                                color = Color(0xFFFBBF24),
////                                fontSize = (14 * scaleFactor).sp,
////                                fontWeight = FontWeight.SemiBold
////                            )
////                        }
////                    }
////                }
//
//                // 类型
//                Text(
//                    text = "类型: ${detail.type}",
//                    color = Color.LightGray,
//                    fontSize = (14 * scaleFactor).sp
//                )
//
//                // 时长
//                detail.runtime?.let { runtime ->
//                    Text(
//                        text = "时长: ${runtime} 分钟",
//                        color = Color.LightGray,
//                        fontSize = (14 * scaleFactor).sp
//                    )
//                }
//
//                // 季数和集数信息
//                if (detail.type == "episode" && detail.seasonNumber != null && detail.episodeNumber != null) {
//                    Text(
//                        text = "第 ${detail.seasonNumber} 季 第 ${detail.episodeNumber} 集",
//                        color = Color.LightGray,
//                        fontSize = (14 * scaleFactor).sp
//                    )
//                }
//
//                // 状态
//                detail.status?.let { status ->
//                    Text(
//                        text = "状态: $status",
//                        color = Color.LightGray,
//                        fontSize = (14 * scaleFactor).sp
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // 操作按钮
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    // 收藏按钮
//                    Row(
//                        modifier = Modifier
//                            .clickable { /* TODO: 实现收藏功能 */ }
//                            .pointerHoverIcon(PointerIcon.Hand)
//                            .padding(8.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(4.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Regular.Heart,
//                            contentDescription = "收藏",
//                            tint = if (detail.isFavorite == 1) Color.Red else Color.White,
//                            modifier = Modifier.size(16.dp)
//                        )
//                        Text(
//                            text = if (detail.isFavorite == 1) "已收藏" else "收藏",
//                            color = Color.White,
//                            fontSize = (12 * scaleFactor).sp
//                        )
//                    }
//
//                    // 观看按钮
//                    Row(
//                        modifier = Modifier
//                            .clickable { /* TODO: 实现播放功能 */ }
//                            .pointerHoverIcon(PointerIcon.Hand)
//                            .padding(8.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(4.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Regular.Info,
//                            contentDescription = "播放",
//                            tint = Color.White,
//                            modifier = Modifier.size(16.dp)
//                        )
//                        Text(
//                            text = "播放",
//                            color = Color.White,
//                            fontSize = (12 * scaleFactor).sp
//                        )
//                    }
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 简介
//        detail.overview?.let { overview ->
//            Column {
//                Text(
//                    text = "简介",
//                    color = Color.White,
//                    fontSize = (18 * scaleFactor).sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    text = overview,
//                    color = Color.LightGray,
//                    fontSize = (14 * scaleFactor).sp,
//                    textAlign = TextAlign.Justify
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // 技术信息
////        Column {
////            Text(
////                text = "技术信息",
////                color = Color.White,
////                fontSize = (18 * scaleFactor).sp,
////                fontWeight = FontWeight.Bold
////            )
////
////            Spacer(modifier = Modifier.height(8.dp))
////
////            detail.mediaStream.videoStreams.firstOrNull()?.let { videoStream ->
////                Text(
////                    text = "视频: ${videoStream.codecName} ${videoStream.width}x${videoStream.height}",
////                    color = Color.LightGray,
////                    fontSize = (14 * scaleFactor).sp
////                )
////            }
////
////            detail.mediaStream.audioStreams.firstOrNull()?.let { audioStream ->
////                Text(
////                    text = "音频: ${audioStream.codecName} ${audioStream.language}",
////                    color = Color.LightGray,
////                    fontSize = (14 * scaleFactor).sp
////                )
////            }
////
////            detail.mediaStream.subtitleStreams.firstOrNull()?.let { subtitleStream ->
////                Text(
////                    text = "字幕: ${subtitleStream.language}",
////                    color = Color.LightGray,
////                    fontSize = (14 * scaleFactor).sp
////                )
////            }
////        }
//    }
//}