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
import androidx.compose.foundation.layout.height
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
import com.jankinwu.fntv.client.data.model.request.StreamRequest
import com.jankinwu.fntv.client.icons.ArrowLeft
import com.jankinwu.fntv.client.icons.Back10S
import com.jankinwu.fntv.client.icons.Forward10S
import com.jankinwu.fntv.client.icons.Pause
import com.jankinwu.fntv.client.icons.Play
import com.jankinwu.fntv.client.ui.component.player.VideoPlayerProgressBar
import com.jankinwu.fntv.client.ui.component.player.formatDuration
import com.jankinwu.fntv.client.viewmodel.PlayInfoViewModel
import com.jankinwu.fntv.client.viewmodel.PlayPlayViewModel
import com.jankinwu.fntv.client.viewmodel.StreamListViewModel
import com.jankinwu.fntv.client.viewmodel.StreamViewModel
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
    val itemGuid: String = "",
    val mediaTitle: String = "",
    val duration: Long = 0L
)

class PlayerManager {
    var playerState: PlayerState by mutableStateOf(PlayerState())

    fun showPlayer(itemGuid: String, mediaTitle: String, duration: Long = 0L) {
        playerState = PlayerState(
            isVisible = true,
            itemGuid = itemGuid,
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlayerOverlay(
    itemGuid: String,
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
    val playState by mediaPlayer.playbackState.collectAsState()
    val videoProgress = if (totalDuration > 0) {
        (currentPosition.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    val videoBuffered by remember { mutableFloatStateOf(0f) }

    // 当播放状态变为暂停时，确保UI可见
    LaunchedEffect(playState) {
        if (playState == PlaybackState.PAUSED) {
            uiVisible = true
            isCursorVisible = true
        }
    }

    // 鼠标静止检测协程
    LaunchedEffect(uiVisible, lastMouseMoveTime, isProgressBarHovered, playState) {
        if (uiVisible && !isProgressBarHovered && playState == PlaybackState.PLAYING) {
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
            .hoverable(interactionSource)
            .background(Color.Black)
            .onPointerEvent(PointerEventType.Move) {
                // 鼠标移动时更新时间并显示UI
                lastMouseMoveTime = System.currentTimeMillis()
                uiVisible = true
                isCursorVisible = true
            }
            .pointerHoverIcon(
                if (isCursorVisible) PointerIcon.Hand else PointerIcon.Default,
                true
            )
    ) {
        // 添加标题栏占位区域，允许窗口拖动
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp) // 与标题栏高度一致
        )

        // 视频层 - 从标题栏下方开始显示
        MediampPlayerSurface(
            mediaPlayer, Modifier
                .fillMaxSize()
                .padding(top = 48.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        if (mediaPlayer.getCurrentPlaybackState() == PlaybackState.PLAYING) {
                            mediaPlayer.pause()
                        } else if (mediaPlayer.getCurrentPlaybackState() == PlaybackState.PAUSED) {
                            mediaPlayer.resume()
                        }
                    }
                ))
        // 播放器 UI
        if (uiVisible) {
            Row(
                modifier = Modifier
                    .padding(top = 48.dp)
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
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        if (uiVisible) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp), // 为标题栏留出空间
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
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                    )
                    // 播放器控制行
                    PlayerControlRow(playState, mediaPlayer, videoProgress, totalDuration)
                }
            }
        }
    }
}

@Composable
fun PlayerControlRow(
    playState: PlaybackState,
    mediaPlayer: MediampPlayer,
    videoProgress: Float,
    totalDuration: Long,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                16.dp,
                Alignment.Start
            ),
        ) {
            // 播放/暂停按钮
            Icon(
                imageVector = if (playState == PlaybackState.PLAYING) Pause else Play,
                contentDescription = "播放/暂停",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            if (mediaPlayer.getCurrentPlaybackState() == PlaybackState.PLAYING) {
                                mediaPlayer.pause()
                            } else if (mediaPlayer.getCurrentPlaybackState() == PlaybackState.PAUSED) {
                                mediaPlayer.resume()
                            }
                        })
            )
            Icon(
                imageVector = Back10S,
                contentDescription = "快退10s",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            mediaPlayer.skip(-10_000)
                        })
            )
            Icon(
                imageVector = Forward10S,
                contentDescription = "快进10s",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            mediaPlayer.skip(10_000)
                        })
            )
            // 当前播放时间 / 总时间
            Text(
                text = "${formatDuration((videoProgress * totalDuration).toLong())} / ${
                    formatDuration(totalDuration)
                }",
                color = Color.White,
                modifier = Modifier
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
        ) {
            Text(
                text = "倍速",
                style = LocalTypography.current.title,
                color = Color.White.copy(alpha = 0.7843f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
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
    val streamViewModel: StreamViewModel = koinInject()
    val playPlayViewModel: PlayPlayViewModel = koinInject()
    val playInfoViewModel: PlayInfoViewModel = koinInject()
    val scope = rememberCoroutineScope()
    val playerManager = LocalPlayerManager.current

    return remember(streamViewModel, playPlayViewModel, guid, title, player, playerManager) {
        {
            scope.launch {
                try {
                    // 显示播放器界面

                    val playInfoResponse = playInfoViewModel.loadDataAndWait(guid)

                    // 获取起始播放时间戳（秒转毫秒）
                    val startPosition: Long = playInfoResponse.ts.toLong() * 1000

                    // 调用 getStreamList 接口
//                    val streamListResponse = streamListViewModel.loadDataAndWait(guid, 1)
                    val streamInfo = streamViewModel.loadDataAndWait(
                        StreamRequest(
                            playInfoResponse.mediaGuid,
                            ip = "a723221e928f7e4fd6f98b12129dc774",
                            level = 1,
                            header = StreamRequest.Header(
                                listOf("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36")
                            )
                        )
                    )
                    // 从返回结果中默认获取第一个文件的 guid
                    val videoStream = streamInfo.videoStream
                    val audioStream = streamInfo.audioStreams.firstOrNull() ?: return@launch
                    val subtitleStream =
                        streamInfo.subtitleStreams.firstOrNull() ?: return@launch
                    val files = streamInfo.fileStream
                    // 获取视频时长（秒转毫秒）
                    val videoDuration = videoStream.duration * 1000L
                    // 显示播放器
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