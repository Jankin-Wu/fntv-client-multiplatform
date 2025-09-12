package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class FavoriteViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<FavoriteActionResult>>(UiState.Initial)
    val uiState: StateFlow<UiState<FavoriteActionResult>> = _uiState.asStateFlow()

    fun favorite(guid: String, currentFavoriteState: Boolean) {
        // 存储回调
        viewModelScope.launch {
            executeWithLoading(_uiState, operationId = guid) {
                fnOfficialApi.favorite(guid)
                FavoriteActionResult(
                    guid = guid,
                    isFavorite = true,
                    success = true,
                    message = "已收藏",
                    previousState = currentFavoriteState
                )
            }
        }
    }

    fun cancelFavorite(guid: String, currentFavoriteState: Boolean) {
        viewModelScope.launch {
            executeWithLoading(_uiState, operationId = guid) {
                fnOfficialApi.cancelFavorite(guid)
                FavoriteActionResult(
                    guid = guid,
                    isFavorite = false,
                    success = true,
                    message = "已取消收藏",
                    previousState = currentFavoriteState
                )
            }
        }
    }

    fun toggleFavorite(guid: String, currentFavoriteState: Boolean) {
        if (currentFavoriteState) {
            cancelFavorite(guid, currentFavoriteState)
        } else {
            favorite(guid, currentFavoriteState)
        }
    }

    data class FavoriteActionResult(
        val guid: String,
        val isFavorite: Boolean,
        val success: Boolean,
        val message: String,
        val previousState: Boolean
    )

    // 更新收藏操作结果
//    private fun updateFavoriteActionResult(result: FavoriteActionResult) {
//        val currentResults = _favoriteActionResults.value.toMutableMap()
//        currentResults[result.guid] = result
//        _favoriteActionResults.value = currentResults
//    }
//
//    // 清除特定视频的收藏操作结果（用于隐藏提示）
//    fun clearFavoriteActionResult(guid: String) {
//        val currentResults = _favoriteActionResults.value.toMutableMap()
//        currentResults.remove(guid)
//        _favoriteActionResults.value = currentResults
//    }
//
//    // 清除所有收藏操作结果
//    fun clearAllFavoriteActionResults() {
//        _favoriteActionResults.value = emptyMap()
//    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}