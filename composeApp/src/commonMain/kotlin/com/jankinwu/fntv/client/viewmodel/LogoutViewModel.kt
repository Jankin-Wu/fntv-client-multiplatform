package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class LogoutViewModel : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<Boolean>>(UiState.Initial)
    val uiState: StateFlow<UiState<Boolean>> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.logout()
            }
        }
    }

    suspend fun logoutAndWait() {
        executeWithLoadingAndReturn {
            fnOfficialApi.logout()
        }
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}