package com.jankinwu.fntv.client

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jankinwu.fntv.client.window.WindowFrame
import fntv_client_compoose.composeapp.generated.resources.Res
import fntv_client_compoose.composeapp.generated.resources.icon
import io.github.composefluent.gallery.component.rememberComponentNavigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skiko.hostOs

fun main() = application {
    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(1280.dp, 720.dp)
    )
    val title = "飞牛影视"
    val icon = painterResource(Res.drawable.icon)
    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = title,
        icon = icon
    ) {
        val navigator = rememberComponentNavigator()
        WindowFrame(
            onCloseRequest = ::exitApplication,
            icon = icon,
            title = title,
            state = state,
            backButtonEnabled = navigator.canNavigateUp,
            backButtonClick = { navigator.navigateUp() },
            backButtonVisible = hostOs.isWindows
        ) { windowInset, contentInset ->
            // 使用 CompositionLocalProvider 将窗口大小提供给其下的所有 Composable
            CompositionLocalProvider(LocalWindowSize provides state.size) {
                App(
                    windowInset = windowInset,
                    contentInset = contentInset,
                    navigator = navigator,
                    title = title,
                    icon = icon
                )
            }
        }
    }
}