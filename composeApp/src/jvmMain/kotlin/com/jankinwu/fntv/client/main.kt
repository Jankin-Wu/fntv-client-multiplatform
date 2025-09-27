package com.jankinwu.fntv.client

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jankinwu.fntv.client.window.WindowFrame
import fntv_client_multiplatform.composeapp.generated.resources.Res
import fntv_client_multiplatform.composeapp.generated.resources.icon
import io.github.composefluent.gallery.component.rememberComponentNavigator
import org.jetbrains.compose.resources.painterResource

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
        ReadEnvVariable()
        val navigator = rememberComponentNavigator()
        WindowFrame(
            onCloseRequest = ::exitApplication,
            icon = icon,
            title = title,
            state = state,
            backButtonEnabled = navigator.canNavigateUp,
            backButtonClick = { navigator.navigateUp() },
//            backButtonVisible = hostOs.isWindows
            backButtonVisible = false
        ) { windowInset, contentInset ->
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