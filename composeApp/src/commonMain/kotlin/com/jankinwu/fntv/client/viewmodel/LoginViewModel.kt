package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.request.LoginRequest
import com.jankinwu.fntv.client.data.model.response.LoginResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import com.jankinwu.fntv.client.data.store.PreferencesManager
import com.jankinwu.fntv.client.data.store.SystemAccountDataCache
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class LoginViewModel : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)
    private val preferencesManager = PreferencesManager.getInstance()

    private val _uiState = MutableStateFlow<UiState<LoginResponse>>(UiState.Initial)
    val uiState: StateFlow<UiState<LoginResponse>> = _uiState.asStateFlow()

    fun login(username: String, password: String, appName: String = "trimemedia-web", rememberMe: Boolean = false, isHttps: Boolean) {
        viewModelScope.launch {
            executeWithLoading(_uiState) {
                val request = LoginRequest(username, password, appName)
                val response = fnOfficialApi.login(request)
                
                // 保存token到SystemAccountData
                SystemAccountDataCache.authorization = response.token
                SystemAccountDataCache.isLoggedIn = true
                SystemAccountDataCache.cookieMap.plus("Trim-MC-token" to response.token)
                
                // 如果选择了记住账号，则保存账号密码和token
                if (rememberMe) {
//                    preferencesManager.saveLoginInfo(username, password, response.token, isHttps, SystemAccountDataCache.host, SystemAccountDataCache.port)
                    preferencesManager.saveAllLoginInfo()
                } else {
                    // 只保存token
                    preferencesManager.clearLoginInfo()
                    preferencesManager.saveToken(response.token)
                }
                
                response
            }
        }
    }

    suspend fun loginAndWait(username: String, password: String, appName: String = "trimemedia-web", rememberMe: Boolean = false): LoginResponse {
        return executeWithLoadingAndReturn {
            val request = LoginRequest(username, password, appName)
            val response = fnOfficialApi.login(request)
            
            // 保存token到SystemAccountData
            SystemAccountDataCache.authorization = response.token
            
            // 如果选择了记住账号，则保存账号密码和token
            if (rememberMe) {
                preferencesManager.saveLoginInfo(
                    username,
                    password,
                    response.token,
                    host = SystemAccountDataCache.host,
                    port = SystemAccountDataCache.port
                )
            } else {
                // 只保存token
                preferencesManager.saveToken(response.token)
            }
            
            response
        }
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}