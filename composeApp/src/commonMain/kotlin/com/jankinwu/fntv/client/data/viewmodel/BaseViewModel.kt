package com.jankinwu.fntv.client.data.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

abstract class BaseViewModel : ViewModel() {
    // 通用的网络请求方法
    protected suspend fun <T> executeWithLoading(
        stateFlow: MutableStateFlow<UiState<T>>,
        apiCall: suspend () -> T
    ) {
        stateFlow.value = UiState.Loading
        try {
            val result = apiCall()
            stateFlow.value = UiState.Success(result)
        } catch (e: Exception) {
            stateFlow.value = UiState.Error(e.message ?: "未知错误")
        }
    }
}

// 通用的 UI 状态密封类
sealed class UiState<out T> {
    object Initial : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

val viewModelModule = module {
    viewModelOf (::MediaDbViewModel)
}