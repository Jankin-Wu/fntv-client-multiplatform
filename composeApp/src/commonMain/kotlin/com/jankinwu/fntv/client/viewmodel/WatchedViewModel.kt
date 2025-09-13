package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class WatchedViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<WatchedActionResult>>(UiState.Initial)
    val uiState: StateFlow<UiState<WatchedActionResult>> = _uiState.asStateFlow()

    fun watched(guid: String, currentWatchedState: Boolean) {

        viewModelScope.launch {
            executeWithLoading(_uiState, operationId = guid) {
                fnOfficialApi.watched(guid)
                WatchedActionResult(
                    guid = guid,
                    isFavorite = true,
                    success = true,
                    message = "标记为已观看",
                    previousState = currentWatchedState
                )
            }
        }
    }

    fun cancelWatched(guid: String, currentWatchedState: Boolean) {
        viewModelScope.launch {
            executeWithLoading(_uiState, operationId = guid) {
                fnOfficialApi.cancelWatched(guid)
                WatchedActionResult(
                    guid = guid,
                    isFavorite = false,
                    success = true,
                    message = "标记为未观看",
                    previousState = currentWatchedState
                )
            }
        }
    }

    fun toggleWatched(guid: String, currentWatchedState: Boolean) {
        if (currentWatchedState) {
            cancelWatched(guid, true)
        } else {
            watched(guid, false)
        }
    }

    data class WatchedActionResult(
        val guid: String,
        val isFavorite: Boolean,
        val success: Boolean,
        val message: String,
        val previousState: Boolean
    )

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}