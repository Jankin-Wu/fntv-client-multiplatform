package com.jankinwu.fntv.client.data.store

import com.jankinwu.fntv.client.ui.component.ToastManager
import com.jankinwu.fntv.client.utils.DomainIpValidator
import com.jankinwu.fntv.client.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 登录状态管理单例类
 * 负责管理全局登录状态，并在状态改变时通知UI更新
 */
object LoginStateManagement {
    private val _isLoggedIn = MutableStateFlow(AccountDataCache.isLoggedIn)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    /**
     * 更新登录状态
     * @param loggedIn 新的登录状态
     */
    fun updateLoginStatus(loggedIn: Boolean) {
        _isLoggedIn.value = loggedIn
        AccountDataCache.isLoggedIn = loggedIn
        
        // 如果登录状态为false，清理用户信息
        if (!loggedIn) {
            AccountDataCache.authorization = ""
            AccountDataCache.userName = ""
            AccountDataCache.password = ""
            AccountDataCache.cookieMap = mutableMapOf()
        }
        
        // 持久化登录状态
        PreferencesManager.getInstance().saveAllLoginInfo()
    }
    
    /**
     * 获取当前登录状态
     * @return 当前登录状态
     */
    /**
     * 登出操作
     */
    fun logout() {
        updateLoginStatus(false)
//        PreferencesManager.getInstance().clearLoginInfo()
    }
    
    /**
     * 获取当前登录状态
     * @return 当前登录状态
     */
    fun getLoginStatus(): Boolean {
        return _isLoggedIn.value
    }

    fun handleLogin(
        host: String,
        port: Int,
        username: String,
        password: String,
        isHttps: Boolean,
        toastManager: ToastManager,
        loginViewModel: LoginViewModel
    ) {
//    val loginState by loginViewModel.uiState.collectAsState()
        if (host.isBlank() || username.isBlank() || password.isBlank()) {
            toastManager.showToast("请填写完整的登录信息", false)
            return
        }
        if (isHttps) {
            AccountDataCache.isHttps = true
        }
        val isValidDomainOrIP = DomainIpValidator.isValidDomainOrIP(host)
        if (!isValidDomainOrIP) {
            toastManager.showToast("请填写正确的ip地址或域名", false)
            return
        }
        AccountDataCache.host = host
        if (port != 0) {
            AccountDataCache.port = port
        }
        // 执行登录逻辑
        loginViewModel.login(username, password)
    }
}