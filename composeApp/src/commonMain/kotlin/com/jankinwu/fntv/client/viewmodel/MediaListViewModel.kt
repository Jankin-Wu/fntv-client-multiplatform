package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.request.MediaListQueryRequest
import com.jankinwu.fntv.client.data.model.request.Tags
import com.jankinwu.fntv.client.data.model.response.MediaListQueryResponse
import com.jankinwu.fntv.client.data.network.FnOfficialApi
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaListViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApi = FnOfficialApiImpl.getInstance()

    private val _uiState = MutableStateFlow<UiState<MediaListQueryResponse>>(UiState.Initial)
    val uiState: StateFlow<UiState<MediaListQueryResponse>> = _uiState.asStateFlow()

    fun loadData(guid: String, typeList: List<String>, pageSize: Int = 22) {
        viewModelScope.launch {
            val request = MediaListQueryRequest(
                ancestorGuid = guid,
                tags = Tags(type = typeList),
                pageSize = pageSize
            )
            executeWithLoading(_uiState) {
                fnOfficialApi.getMediaList(request)
            }
        }
    }

    fun refresh() {
//        loadData()
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}