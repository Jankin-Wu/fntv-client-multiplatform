package com.jankinwu.fntv

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jankinwu.fntv.component.video.VideoDemo

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Compose WebView Multiplatform") {
        VideoDemo()
    }
}