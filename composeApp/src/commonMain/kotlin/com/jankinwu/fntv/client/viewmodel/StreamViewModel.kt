package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.request.StreamRequest
import com.jankinwu.fntv.client.data.model.response.StreamResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class StreamViewModel : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<StreamResponse>>(UiState.Initial)
    val uiState: StateFlow<UiState<StreamResponse>> = _uiState.asStateFlow()

    fun loadData(request: StreamRequest) {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.stream(request)
            }
        }
    }

    suspend fun loadDataAndWait(request: StreamRequest): StreamResponse {
        return executeWithLoadingAndReturn {
            fnOfficialApi.stream(request)
        }
    }

    fun refresh(request: StreamRequest) {
        loadData(request)
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}