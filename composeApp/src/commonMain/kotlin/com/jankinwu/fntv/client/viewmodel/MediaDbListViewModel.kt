package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.response.MediaDbListResponse
import com.jankinwu.fntv.client.data.network.FnOfficialApi
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaDbListViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApi = FnOfficialApiImpl.getInstance()

    private val _uiState = MutableStateFlow<UiState<List<MediaDbListResponse>>>(UiState.Initial)
    val uiState: StateFlow<UiState<List<MediaDbListResponse>>> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.getMediaDbList()
            }
        }
    }

    fun refresh() {
        loadData()
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}