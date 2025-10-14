package com.jankinwu.fntv.client

import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jankinwu.fntv.client.data.store.LoginStateManagement
import com.jankinwu.fntv.client.data.store.PreferencesManager
import com.jankinwu.fntv.client.ui.screen.LocalPlayerManager
import com.jankinwu.fntv.client.ui.screen.LoginScreen
import com.jankinwu.fntv.client.ui.screen.PlayerManager
import com.jankinwu.fntv.client.ui.screen.PlayerOverlay
import com.jankinwu.fntv.client.viewmodel.UiState
import com.jankinwu.fntv.client.viewmodel.UserInfoViewModel
import com.jankinwu.fntv.client.window.WindowFrame
import fntv_client_multiplatform.composeapp.generated.resources.Res
import fntv_client_multiplatform.composeapp.generated.resources.icon
import io.github.composefluent.gallery.component.rememberComponentNavigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.openani.mediamp.compose.rememberMediampPlayer

fun main() = application {
    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(1280.dp, 720.dp)
    )
    val title = "飞牛影视"
    val icon = painterResource(Res.drawable.icon)
    // 加载登录信息到缓存
    PreferencesManager.getInstance().loadAllLoginInfo()
    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = title,
        icon = icon
    ) {
//        ReadEnvVariable()
        val navigator = rememberComponentNavigator()
        val playerManager = remember { PlayerManager() }
        val player = rememberMediampPlayer()
        val userInfoViewModel: UserInfoViewModel = koinInject()
        val userInfoState by userInfoViewModel.uiState.collectAsState()
        CompositionLocalProvider(
            LocalPlayerManager provides playerManager
        ) {
            WindowFrame(
                onCloseRequest = {
                    player.close() // 关闭播放器
                    exitApplication() // 退出应用
                },
                icon = icon,
                title = title,
                state = state,
                backButtonEnabled = navigator.canNavigateUp,
                backButtonClick = { navigator.navigateUp() },
    //            backButtonVisible = hostOs.isWindows
                backButtonVisible = false
            ) { windowInset, contentInset ->
                // 使用LoginStateManagement来管理登录状态
                val isLoggedIn by LoginStateManagement.isLoggedIn.collectAsState()
                
                // 校验cookie是否有效
                LaunchedEffect(Unit) {
                    if (isLoggedIn) {
                        userInfoViewModel.loadUserInfo()
                    }
                    if (userInfoState is UiState.Error) {
                        LoginStateManagement.updateLoginStatus(false)
                    }
                }
                // 只有在未登录状态下才显示登录界面
                if (!isLoggedIn) {
                    LoginScreen()
                } else {
                    App(
                        windowInset = windowInset,
                        contentInset = contentInset,
                        navigator = navigator,
                        title = title,
                        icon = icon,
                        player = player
                    )
                }
                if (playerManager.playerState.isVisible) {
                    WindowDraggableArea {
                        PlayerOverlay(
                            itemGuid = playerManager.playerState.itemGuid,
                            mediaTitle = playerManager.playerState.mediaTitle,
                            onBack = { playerManager.hidePlayer() },
                            mediaPlayer = player
                        )
                    }
                }
            }
        }
    }
}
