package com.jankinwu.fntv.client.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.MediaDbData
import com.jankinwu.fntv.client.data.network.FnOfficialApi
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaDbViewModel() : ViewModel() {

    private val fnOfficialApi: FnOfficialApi = FnOfficialApiImpl.getInstance()

    private val _uiState = MutableStateFlow<MediaDbUiState>(MediaDbUiState.Initial)
    val uiState: StateFlow<MediaDbUiState> = _uiState.asStateFlow()

    fun loadMediaDbList() {
        viewModelScope.launch {
            // 设置加载状态
            _uiState.value = MediaDbUiState.Loading

            try {
                val result = fnOfficialApi.getMediaDbList()
                _uiState.value = MediaDbUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = MediaDbUiState.Error(e.message ?: "未知错误")
            }
        }
    }

    fun refreshMediaDbList() {
        loadMediaDbList()
    }

    fun clearError() {
        _uiState.value = MediaDbUiState.Initial
    }
}

// UI 状态密封类
sealed class MediaDbUiState {
    object Initial : MediaDbUiState()
    object Loading : MediaDbUiState()
    data class Success(val data: List<MediaDbData>) : MediaDbUiState()
    data class Error(val message: String) : MediaDbUiState()
}