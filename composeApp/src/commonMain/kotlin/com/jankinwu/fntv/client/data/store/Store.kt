package com.jankinwu.fntv.client.data.store

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.network.NetworkHeaders
import io.github.composefluent.component.NavigationDisplayMode

class Store(
    systemDarkMode: Boolean,
    enabledAcrylicPopup: Boolean,
    compactMode: Boolean,
    windowWidth: Dp,
    windowHeight: Dp,
    cookie: String
) {
    var darkMode by mutableStateOf(systemDarkMode)

    var enabledAcrylicPopup by mutableStateOf(enabledAcrylicPopup)

    var compactMode by mutableStateOf(compactMode)

    var navigationDisplayMode by mutableStateOf(NavigationDisplayMode.Left)

    // 缩放因子，用于调整组件大小
    var scaleFactor by mutableFloatStateOf((windowWidth / 1280.dp))

    val fnImgHeaders by mutableStateOf(NetworkHeaders.Builder()
        .set("cookie", cookie)
        .build())
    var windowWidth by mutableStateOf(windowWidth)

    var windowHeight by mutableStateOf(windowHeight)
    
    fun updateWindowWidth(newWidth: Dp) {
        val windowScaleFactor = (newWidth / 1280.dp)
        scaleFactor =
            if (windowScaleFactor == 1f) 1f else (1f + (windowScaleFactor - 1f) * 0.3f).coerceIn(
                1f,
                1.5f
            )
        windowWidth = newWidth
    }

    fun updateWindowHeight(newHeight: Dp) {
        windowHeight = newHeight
    }
}