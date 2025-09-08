package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.MediaDbData
import com.jankinwu.fntv.client.data.network.FnOfficialApi
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaDbViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApi = FnOfficialApiImpl.getInstance()

    private val _uiState = MutableStateFlow<UiState<List<MediaDbData>>>(UiState.Initial)
    val uiState: StateFlow<UiState<List<MediaDbData>>> = _uiState.asStateFlow()

    fun loadMediaDbList() {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.getMediaDbList()
            }
        }
    }

    fun refreshMediaDbList() {
        loadMediaDbList()
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}