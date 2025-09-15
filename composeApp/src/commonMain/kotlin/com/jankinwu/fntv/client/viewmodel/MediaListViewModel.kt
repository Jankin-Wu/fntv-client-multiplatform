package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.request.MediaListQueryRequest
import com.jankinwu.fntv.client.data.model.request.Tags
import com.jankinwu.fntv.client.data.model.response.MediaListQueryResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MediaListViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<MediaListQueryResponse>>(UiState.Initial)

    val uiState: StateFlow<UiState<MediaListQueryResponse>> = _uiState.asStateFlow()

    private var currentPage = 1

    var isLastPage = false

    fun loadData(guid: String, typeList: List<String>, pageSize: Int = 22) {
        // 重置状态
        currentPage = 1
        isLastPage = false
        _uiState.value = UiState.Initial

        loadPageData(guid, typeList, pageSize, currentPage)
    }

    fun loadMoreData(guid: String, typeList: List<String>, pageSize: Int = 50) {
        if (!isLastPage) {
            loadPageData(guid, typeList, pageSize, currentPage + 1, isLoadMore = true)
        }
    }

    fun loadPageData(
        guid: String,
        typeList: List<String>,
        pageSize: Int,
        page: Int = 1,
        isLoadMore: Boolean = false
    ) {
        viewModelScope.launch {
            val request = MediaListQueryRequest(
                ancestorGuid = guid,
                tags = Tags(type = typeList),
                pageSize = pageSize,
                page = page
            )
            executeWithLoading(_uiState) {
                val result = fnOfficialApi.getMediaList(request)

                // 更新分页状态
                currentPage = page
                isLastPage = result.list.size < pageSize

                // 如果是加载更多，需要合并数据
                if (isLoadMore && page > 1) {
                    val currentData = (_uiState.value as? UiState.Success)?.data
                    if (currentData != null) {
                        // 创建一个新的响应对象，合并列表数据
                        val mergedList = currentData.list + result.list
                        val mergedResult = result.copy(list = mergedList)
                        return@executeWithLoading mergedResult
                    }
                }

                result
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