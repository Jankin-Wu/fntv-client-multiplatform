package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.response.GenresResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class GenresViewModel : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<List<GenresResponse>>>(UiState.Initial)
    val uiState: StateFlow<UiState<List<GenresResponse>>> = _uiState.asStateFlow()

    /**
     * 加载指定语言的 genres 数据
     * @param lan 语言代码，例如 "zh-CN"
     */
    fun loadGenres(lan: String = "zh-CN") {
        viewModelScope.launch {
            executeWithLoading(_uiState, "loadGenres") {
                fnOfficialApi.getGenres(lan)
            }
        }
    }

    /**
     * 刷新 genres 数据
     * @param lan 语言代码，例如 "zh-CN"
     */
    fun refreshGenres(lan: String = "zh-CN") {
        loadGenres(lan)
    }

    /**
     * 清除错误状态
     */
    fun clearError() {
        _uiState.value = UiState.Initial
    }
}