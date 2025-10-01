package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.model.SystemAccountData
import com.jankinwu.fntv.client.data.model.request.PlayPlayRequest
import com.jankinwu.fntv.client.icons.ArrowLeft
import com.jankinwu.fntv.client.ui.component.player.VideoPlayerProgressBar
import com.jankinwu.fntv.client.ui.component.player.formatDuration
import com.jankinwu.fntv.client.viewmodel.PlayInfoViewModel
import com.jankinwu.fntv.client.viewmodel.PlayPlayViewModel
import com.jankinwu.fntv.client.viewmodel.StreamListViewModel
import io.github.composefluent.FluentTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.openani.mediamp.MediampPlayer
import org.openani.mediamp.PlaybackState
import org.openani.mediamp.compose.MediampPlayerSurface
import org.openani.mediamp.source.MediaExtraFiles
import org.openani.mediamp.source.UriMediaData


data class PlayerState(
    val isVisible: Boolean = false,
    val mediaGuid: String = "",
    val mediaTitle: String = "",
    val duration: Long = 0L
)

class PlayerManager {
    var playerState: PlayerState by mutableStateOf(PlayerState())

    fun showPlayer(mediaGuid: String, mediaTitle: String, duration: Long = 0L) {
        playerState = PlayerState(
            isVisible = true,
            mediaGuid = mediaGuid,
            mediaTitle = mediaTitle,
            duration = duration
        )
    }

    fun hidePlayer() {
        playerState = playerState.copy(isVisible = false)
    }
}

val LocalPlayerManager = staticCompositionLocalOf<PlayerManager> {
    error("PlayerManager not provided")
}


// 简化的播放器覆盖层组件
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlayerOverlay(
    mediaGuid: String,
    mediaTitle: String,
    onBack: () -> Unit,
    mediaPlayer: MediampPlayer
) {
    // 控制UI可见性的状态
    var uiVisible by remember { mutableStateOf(true) }
    var isCursorVisible by remember { mutableStateOf(true) }
    var lastMouseMoveTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    var isProgressBarHovered by remember { mutableStateOf(false) }

    val currentPosition by mediaPlayer.currentPositionMillis.collectAsState()
    val playerManager = LocalPlayerManager.current
    val totalDuration = playerManager.playerState.duration
    val videoProgress = if (totalDuration > 0) {
        (currentPosition.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    val videoBuffered by remember { mutableFloatStateOf(0f) }
    // 鼠标静止检测协程
    LaunchedEffect(uiVisible, lastMouseMoveTime, isProgressBarHovered) {
        if (uiVisible && !isProgressBarHovered) {
            launch {
                while (true) {
                    delay(100) // 每100ms检查一次
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastMouseMoveTime >= 2000) {
                        uiVisible = false
                        isCursorVisible = false
                        break
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 48.dp)
            .onPointerEvent(PointerEventType.Move) {
                // 鼠标移动时更新时间并显示UI
                lastMouseMoveTime = System.currentTimeMillis()
                uiVisible = true
                isCursorVisible = true
            }
    ) {
        // 视频层
        MediampPlayerSurface(mediaPlayer, Modifier.fillMaxSize())
        // 播放器 UI
        if (uiVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            if (mediaPlayer.getCurrentPlaybackState() == PlaybackState.PLAYING) {
                                mediaPlayer.pause()
                            } else if (mediaPlayer.getCurrentPlaybackState() == PlaybackState.PAUSED){
                                mediaPlayer.resume()
                            }
                        })
                    .hoverable(interactionSource)
                    .pointerHoverIcon(if (isCursorVisible) PointerIcon.Hand else PointerIcon.Default,  true),
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 20.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ArrowLeft,
                        contentDescription = "返回",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(onClick = {
                                onBack()
                                mediaPlayer.stopPlayback()
                            })
                    )
                    Text(
                        text = mediaTitle,
                        style = LocalTypography.current.title,
                        color = FluentTheme.colors.text.text.primary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onPointerEvent(PointerEventType.Enter) {
                                isProgressBarHovered = true
                            }
                            .onPointerEvent(PointerEventType.Exit) {
                                isProgressBarHovered = false
                                // 重新开始鼠标静止检测
                                lastMouseMoveTime = System.currentTimeMillis()
                            }
                    ) {
                        VideoPlayerProgressBar(
                            player = mediaPlayer,
                            totalDuration = playerManager.playerState.duration,
                            onSeek = { newProgress ->
                                val seekPosition = (newProgress * totalDuration).toLong()
                                mediaPlayer.seekTo(seekPosition)
                                println("Seek to: ${newProgress * 100}%")
                            },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        ) {
                            // 当前播放时间 / 总时间
                            Text(
                                text = "${formatDuration((videoProgress * totalDuration).toLong())} / ${formatDuration(totalDuration)}",
                                color = Color.White,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }
}

suspend fun MediampPlayer.playUri(
    uri: String,
    headers: Map<String, String> = emptyMap()
): Unit =
    setMediaData(UriMediaData(uri, headers, MediaExtraFiles()))

@Composable
fun rememberPlayMediaFunction(
    guid: String,
    title: String,
    player: MediampPlayer
): () -> Unit {
    val streamListViewModel: StreamListViewModel = koinInject()
    val playPlayViewModel: PlayPlayViewModel = koinInject()
    val playInfoViewModel: PlayInfoViewModel = koinInject()
    val scope = rememberCoroutineScope()
    val playerManager = LocalPlayerManager.current

    return remember(streamListViewModel, playPlayViewModel, guid, title, player, playerManager) {
        {
            scope.launch {
                try {
                    // 显示播放器界面

                    val playInfoResponse = playInfoViewModel.loadDataAndWait(guid)

                    // 获取起始播放时间戳（秒转毫秒）
                    val startPosition: Long = playInfoResponse.ts.toLong() * 1000

                    // 调用 getStreamList 接口
                    val streamListResponse = streamListViewModel.loadDataAndWait(guid, 1)

                    // 从返回结果中默认获取第一个文件的 guid
                    val videoStream = streamListResponse.videoStreams.firstOrNull() ?: return@launch
                    val audioStream = streamListResponse.audioStreams.firstOrNull() ?: return@launch
                    val subtitleStream =
                        streamListResponse.subtitleStreams.firstOrNull() ?: return@launch
                    val files = streamListResponse.files.firstOrNull() ?: return@launch
                    // 获取视频时长（秒转毫秒）
                    val videoDuration = videoStream.duration * 1000L

                    playerManager.showPlayer(guid, title, videoDuration)
                    // 构造 PlayPlayRequest
                    val playRequest = PlayPlayRequest(
                        videoGuid = videoStream.guid,
                        mediaGuid = files.guid,
                        audioEncoder = "aac",
                        audioGuid = audioStream.guid,
                        bitrate = videoStream.bps,
                        channels = 2,
                        forcedSdr = 0,
                        resolution = videoStream.resolutionType,
                        startTimestamp = 0,
                        subtitleGuid = subtitleStream.guid,
                        videoEncoder = videoStream.codecName,
                    )

                    // 调用 playPlay 接口
                    val playResponse = playPlayViewModel.loadDataAndWait(playRequest)

                    // 获取播放链接
                    val playLink = playResponse.playLink

                    // 启动播放器
                    if (SystemAccountData.cookie.isNotBlank()) {
                        val headers = mapOf("cookie" to SystemAccountData.cookie)
                        player.playUri("${SystemAccountData.fnOfficialBaseUrl}$playLink", headers)
                    } else {
                        player.playUri("${SystemAccountData.fnOfficialBaseUrl}$playLink")
                    }
                    delay(500) // 等待播放器初始化
                    player.seekTo(startPosition)
                } catch (e: Exception) {
                    // 处理错误情况
                    println("播放失败: ${e.message}")
                }
            }
        }
    }
}

//private val InvisiblePointerIcon: PointerIcon = run {
//    // 创建一个1x1的透明图像
//    val image = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
//    // 获取默认的 Toolkit
//    val toolkit = Toolkit.getDefaultToolkit()
//    // 创建一个自定义的 AWT Cursor
//    val cursor = toolkit.createCustomCursor(image, Point(0, 0), "invisible")
//    // 将 AWT Cursor 包装成 Compose 的 PointerIcon
//    PointerIcon(cursor)
//}