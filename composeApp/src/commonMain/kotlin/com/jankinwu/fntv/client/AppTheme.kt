package com.jankinwu.fntv.client

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.jankinwu.fntv.client.utils.isSystemInDarkMode
import io.github.composefluent.ExperimentalFluentApi
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentColor
import io.github.composefluent.background.Mica
import io.github.composefluent.component.NavigationDisplayMode
import io.github.composefluent.darkColors
import io.github.composefluent.lightColors

val LocalStore = compositionLocalOf<Store> { error("Not provided") }

class Store(
    systemDarkMode: Boolean,
    enabledAcrylicPopup: Boolean,
    compactMode: Boolean,
    windowWidth: Dp
) {
    var darkMode by mutableStateOf(systemDarkMode)

    var enabledAcrylicPopup by mutableStateOf(enabledAcrylicPopup)

    var compactMode by mutableStateOf(compactMode)

    var navigationDisplayMode by mutableStateOf(NavigationDisplayMode.Left)

    // 缩放因子，用于调整组件大小
    var scaleFactor by mutableFloatStateOf((windowWidth / 1280.dp))

    fun updateWindowWidth(newWidth: Dp) {
        val windowScaleFactor = (newWidth / 1280.dp)
        scaleFactor = if (windowScaleFactor == 1f) 1f else (1f + (windowScaleFactor - 1f) * 0.3f).coerceIn(1f, 1.5f)
    }
}

@OptIn(ExperimentalFluentApi::class)
@Composable
fun AppTheme(
    displayMicaLayer: Boolean = true,
    state: WindowState,
    content: @Composable () -> Unit
) {
    val systemDarkMode = isSystemInDarkMode()

    val store = remember {
        Store(
            systemDarkMode = systemDarkMode,
            enabledAcrylicPopup = true,
            compactMode = true,
            windowWidth = state.size.width
        )
    }

    LaunchedEffect(systemDarkMode) {
        store.darkMode = systemDarkMode
    }
    LaunchedEffect(state.size.width) {
        store.updateWindowWidth(state.size.width)
    }
    CompositionLocalProvider(
        LocalStore provides store
    ) {
        FluentTheme(
            colors = if (store.darkMode) darkColors() else lightColors(),
            useAcrylicPopup = store.enabledAcrylicPopup,
            compactMode = store.compactMode
        ) {
            if (displayMicaLayer) {
                val gradient = if (store.darkMode) {
                    listOf(
                        Color(0xff282C51),
                        Color(0xff2A344A),
                    )
                } else {
                    listOf(
                        Color(0xffB1D0ED),
                        Color(0xffDAE3EC),
                    )
                }

                Mica(
                    background = {
                        Image(
                            painter = BrushPainter(Brush.linearGradient(gradient)),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    content()
                }
            } else {
                CompositionLocalProvider(
                    LocalContentColor provides FluentTheme.colors.text.text.primary,
                    content = content
                )
            }
        }
    }
}