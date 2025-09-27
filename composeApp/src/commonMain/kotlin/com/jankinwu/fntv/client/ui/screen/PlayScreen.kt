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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.ArrowLeft
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
            .background(Color.Black.copy(alpha = 1f))
    ) {
        // 播放器标题栏（简化版）
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp)
//                .background(Color(0xFF202020))
//        ) {
//            // 返回按钮
//            IconButton(
//                onClick = onBack,
//                modifier = Modifier.align(Alignment.CenterStart)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowLeft,
//                    contentDescription = "返回",
//                    tint = Color.White
//                )
//            }
//
//            // 标题
//            Text(
//                text = mediaTitle,
//                color = Color.White,
//                modifier = Modifier.align(Alignment.Center)
//            )
//
//            // 关闭按钮
//            IconButton(
//                onClick = onBack,
//                modifier = Modifier.align(Alignment.CenterEnd)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowLeft,
//                    contentDescription = "关闭",
//                    tint = Color.White
//                )
//            }
//        }

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