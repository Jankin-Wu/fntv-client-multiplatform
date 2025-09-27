package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.request.PlayPlayRequest
import com.jankinwu.fntv.client.data.model.response.PlayPlayResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class PlayPlayViewModel : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<PlayPlayResponse>>(UiState.Initial)
    val uiState: StateFlow<UiState<PlayPlayResponse>> = _uiState.asStateFlow()

    fun loadData(request: PlayPlayRequest) {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.playPlay(request)
            }
        }
    }

    suspend fun loadDataAndWait(request: PlayPlayRequest): PlayPlayResponse {
        return executeWithLoadingAndReturn {
            fnOfficialApi.playPlay(request)
        }
    }

    fun refresh(request: PlayPlayRequest) {
        loadData(request)
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}