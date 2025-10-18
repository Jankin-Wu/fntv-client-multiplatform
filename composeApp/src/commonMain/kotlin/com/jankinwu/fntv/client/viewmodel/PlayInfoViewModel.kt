package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.response.PlayInfoResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class PlayInfoViewModel : BaseViewModel()  {


    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<PlayInfoResponse>>(UiState.Initial)
    val uiState: StateFlow<UiState<PlayInfoResponse>> = _uiState.asStateFlow()

    fun loadData(guid: String) {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.playInfo(guid)
            }
        }
    }

    suspend fun loadDataAndWait(guid: String): PlayInfoResponse {
        return executeWithLoadingAndReturn(_uiState) {
            fnOfficialApi.playInfo(guid)
        }
    }

    fun refresh(guid: String) {
        loadData(guid)
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}