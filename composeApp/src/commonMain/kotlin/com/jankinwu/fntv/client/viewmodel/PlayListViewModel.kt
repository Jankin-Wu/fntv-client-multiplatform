package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.response.PlayDetailResponse
import com.jankinwu.fntv.client.data.network.FnOfficialApi
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayListViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApi = FnOfficialApiImpl.getInstance()

    private val _uiState = MutableStateFlow<UiState<List<PlayDetailResponse>>>(UiState.Initial)
    val uiState: StateFlow<UiState<List<PlayDetailResponse>>> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.getPlayList()
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