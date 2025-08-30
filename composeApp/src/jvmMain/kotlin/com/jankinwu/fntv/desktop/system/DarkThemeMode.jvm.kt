package com.jankinwu.fntv.desktop.system

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.jthemedetecor.OsThemeDetector
import java.util.function.Consumer

@Composable
actual fun isSystemInDarkMode(): Boolean {
    val isSystemInDarkTheme = isSystemInDarkTheme().let { currentValue ->
        remember(currentValue) { mutableStateOf(currentValue) }
    }
    DisposableEffect(isSystemInDarkTheme) {
        val listener = Consumer<Boolean> {
            isSystemInDarkTheme.value = it
        }
        val detector = OsThemeDetector.getDetector()
        detector.registerListener(listener)
        onDispose {
            detector.removeListener(listener)
        }
    }
    return isSystemInDarkTheme.value
}