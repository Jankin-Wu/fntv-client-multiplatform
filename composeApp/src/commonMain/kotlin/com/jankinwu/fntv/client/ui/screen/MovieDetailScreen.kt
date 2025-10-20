package com.jankinwu.fntv.client.ui.screen

import androidx.compose.runtime.Composable
import com.jankinwu.fntv.client.ui.component.CastScrollRow

@Composable
fun CastDetailScreen(guid: String) {
    CastScrollRow( guid = guid)
}