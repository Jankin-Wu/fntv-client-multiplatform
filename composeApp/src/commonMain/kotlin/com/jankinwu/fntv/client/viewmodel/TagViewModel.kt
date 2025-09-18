package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.response.QueryTagResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class TagViewModel : BaseViewModel() {
    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    // ISO 639-1 标签状态
    private val _iso6391State = MutableStateFlow<UiState<List<QueryTagResponse>>>(UiState.Initial)
    val iso6391State: StateFlow<UiState<List<QueryTagResponse>>> = _iso6391State.asStateFlow()

    // ISO 639-2 标签状态
    private val _iso6392State = MutableStateFlow<UiState<List<QueryTagResponse>>>(UiState.Initial)
    val iso6392State: StateFlow<UiState<List<QueryTagResponse>>> = _iso6392State.asStateFlow()

    // ISO 3166 标签状态
    private val _iso3166State = MutableStateFlow<UiState<List<QueryTagResponse>>>(UiState.Initial)
    val iso3166State: StateFlow<UiState<List<QueryTagResponse>>> = _iso3166State.asStateFlow()

    /**
     * 加载所有标签数据
     */
    fun loadAllTags(lan: String = "zh-CN") {
        loadIso6391Tags(lan)
        loadIso6392Tags(lan)
        loadIso3166Tags(lan)
    }

    /**
     * 加载 ISO 639-1 标签数据
     */
    fun loadIso6391Tags(lan: String = "zh-CN") {
        viewModelScope.launch {
            executeWithLoading(_iso6391State, "loadIso6391Tags") {
                fnOfficialApi.getTag("iso6391", lan)
            }
        }
    }

    /**
     * 加载 ISO 639-2 标签数据
     */
    fun loadIso6392Tags(lan: String = "zh-CN") {
        viewModelScope.launch {
            executeWithLoading(_iso6392State, "loadIso6392Tags") {
                fnOfficialApi.getTag("iso6392", lan)
            }
        }
    }

    /**
     * 加载 ISO 3166 标签数据
     */
    fun loadIso3166Tags(lan: String = "zh-CN") {
        viewModelScope.launch {
            executeWithLoading(_iso3166State, "loadIso3166Tags") {
                fnOfficialApi.getTag("iso3166", lan)
            }
        }
    }

    /**
     * 刷新所有标签数据
     */
    fun refreshAll(lan: String = "zh-CN") {
        clearAllErrors()
        loadAllTags(lan)
    }

    /**
     * 清除所有错误状态
     */
    fun clearAllErrors() {
        if (_iso6391State.value is UiState.Error) _iso6391State.value = UiState.Initial
        if (_iso6392State.value is UiState.Error) _iso6392State.value = UiState.Initial
        if (_iso3166State.value is UiState.Error) _iso3166State.value = UiState.Initial
    }
}