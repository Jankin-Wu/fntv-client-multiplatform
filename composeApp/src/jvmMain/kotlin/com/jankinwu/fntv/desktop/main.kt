package com.jankinwu.fntv.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.composefluent.gallery.component.rememberComponentNavigator

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "fntv-desktop-compose",
    ) {
        val navigator = rememberComponentNavigator()
        App()
    }
}