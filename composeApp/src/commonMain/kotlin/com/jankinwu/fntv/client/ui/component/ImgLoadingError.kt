package com.jankinwu.fntv.client.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fntv_client_multiplatform.composeapp.generated.resources.Res
import fntv_client_multiplatform.composeapp.generated.resources.placeholder_error
import io.github.composefluent.FluentTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImgLoadingError(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FluentTheme.colors.stroke.control.secondary.copy(alpha = 0.07f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(Res.drawable.placeholder_error),
            contentDescription = "加载失败占位图",
            modifier = Modifier.fillMaxSize(0.5f)
        )
    }
}