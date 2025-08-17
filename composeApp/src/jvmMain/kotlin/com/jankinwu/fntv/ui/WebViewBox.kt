package com.jankinwu.fntv.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragData
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@Preview
@Composable
fun WebViewBox() {

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // 3. 应用来自 Scaffold 的内边距，防止内容被遮挡
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text("测试")
                val state = rememberWebViewState("http://192.168.31.74:8000/v")
//                val state = rememberWebViewState("https://www.google.com.hk/")
                WebView(state, modifier = Modifier.fillMaxSize())
            }
        }
    )
//    Box(modifier = Modifier
//        .fillMaxSize()
//    ) {
//
//        val state = rememberWebViewState("http://192.168.31.74:8000/v")
//        WebView(state, modifier = Modifier.fillMaxSize())
//    }
}