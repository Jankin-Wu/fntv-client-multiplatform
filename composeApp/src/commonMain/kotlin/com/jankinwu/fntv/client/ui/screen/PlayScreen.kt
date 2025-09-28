package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jankinwu.fntv.client.data.model.SystemAccountData
import com.jankinwu.fntv.client.data.model.request.PlayPlayRequest
import com.jankinwu.fntv.client.viewmodel.PlayInfoViewModel
import com.jankinwu.fntv.client.viewmodel.PlayPlayViewModel
import com.jankinwu.fntv.client.viewmodel.StreamListViewModel
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.ArrowLeft
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.openani.mediamp.MediampPlayer
import org.openani.mediamp.compose.MediampPlayerSurface
import org.openani.mediamp.source.MediaExtraFiles
import org.openani.mediamp.source.UriMediaData


data class PlayerState(
    val isVisible: Boolean = false,
    val mediaGuid: String = "",
    val mediaTitle: String = ""
)

class PlayerManager {
    var playerState: PlayerState by mutableStateOf(PlayerState())

    fun showPlayer(mediaGuid: String, mediaTitle: String) {
        playerState = PlayerState(
            isVisible = true,
            mediaGuid = mediaGuid,
            mediaTitle = mediaTitle
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
@Composable
fun PlayerOverlay(
    mediaGuid: String,
    mediaTitle: String,
    onBack: () -> Unit,
    mediaPlayer: MediampPlayer
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 播放器内容区域
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp)
        ) {
            IconButton(
                onClick = {
                    onBack()
                    mediaPlayer.stopPlayback()
                },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowLeft,
                    contentDescription = "返回",
                    tint = Color.White
                )
            }
            MediampPlayerSurface(mediaPlayer, Modifier.fillMaxSize())
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
                    playerManager.showPlayer(guid, title)

                    // 调用 getStreamList 接口
                    val streamListResponse = streamListViewModel.loadDataAndWait(guid, 1)
                    val playInfoResponse = playInfoViewModel.loadDataAndWait(guid)

                    // 从返回结果中默认获取第一个文件的 guid
                    val videoStream = streamListResponse.videoStreams.firstOrNull()?: return@launch
                    val audioStream = streamListResponse.audioStreams.firstOrNull()?: return@launch
                    val subtitleStream = streamListResponse.subtitleStreams.firstOrNull()?: return@launch
                    val files = streamListResponse.files.firstOrNull()?: return@launch

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
                } catch (e: Exception) {
                    // 处理错误情况
                    println("播放失败: ${e.message}")
                }
            }
        }
    }
}