package com.jankinwu.fntv.client.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jankinwu.fntv.client.data.model.response.MediaDbListResponse
import com.jankinwu.fntv.client.viewmodel.MediaListViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import io.github.composefluent.gallery.component.ComponentNavigator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MediaDbScreen(media: MediaDbListResponse, navigator: ComponentNavigator) {
    val mediaListViewModel: MediaListViewModel = koinViewModel<MediaListViewModel>()
    val mediaUiState by mediaListViewModel.uiState.collectAsState()
    LaunchedEffect(mediaUiState) {
        val state = mediaUiState
        when (state) {
            is UiState.Success -> {

            } else -> {
            }
        }
    }
}

