package com.jankinwu.fntv.desktop.system

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
actual fun isSystemInDarkMode(): Boolean {
    return isSystemInDarkTheme()
}