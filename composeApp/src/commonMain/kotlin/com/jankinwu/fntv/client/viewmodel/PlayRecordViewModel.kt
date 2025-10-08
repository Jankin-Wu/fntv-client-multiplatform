package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.request.PlayRecordRequest
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class PlayRecordViewModel : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    val uiState: StateFlow<UiState<Unit>> = _uiState.asStateFlow()

    fun loadData(request: PlayRecordRequest) {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.playRecord(request)
            }
        }
    }

    suspend fun loadDataAndWait(request: PlayRecordRequest) {
        executeWithLoadingAndReturn {
            fnOfficialApi.playRecord(request)
        }
    }

    fun refresh(request: PlayRecordRequest) {
        loadData(request)
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}