package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.response.UserInfoResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class UserInfoViewModel : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<UserInfoResponse>>(UiState.Initial)
    val uiState: StateFlow<UiState<UserInfoResponse>> = _uiState.asStateFlow()

    suspend fun loadUserInfo() {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                fnOfficialApi.userInfo()
            }
        }
    }

    suspend fun loadUserInfoAndWait(): UserInfoResponse {
        return executeWithLoadingAndReturn {
            fnOfficialApi.userInfo()
        }
    }

    suspend fun refresh() {
        loadUserInfo()
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}