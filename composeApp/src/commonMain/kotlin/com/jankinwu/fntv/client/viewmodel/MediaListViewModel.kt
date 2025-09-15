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
            // 如果是加载更多，使用专门的加载方法避免覆盖现有数据
            if (isLoadMore && page > 1) {
                loadMoreDataInternal(guid, typeList, pageSize, page)
            } else {
                // 首次加载使用原有方法
                executeWithLoading(_uiState) {
                    val request = MediaListQueryRequest(
                        ancestorGuid = guid,
                        tags = Tags(type = typeList),
                        pageSize = pageSize,
                        page = page
                    )
                    val result = fnOfficialApi.getMediaList(request)

                    // 更新分页状态
                    currentPage = page
                    isLastPage = result.list.size < pageSize

                    result
                }
            }
        }
    }

    private suspend fun loadMoreDataInternal(
        guid: String,
        typeList: List<String>,
        pageSize: Int,
        page: Int
    ) {
        try {
            val request = MediaListQueryRequest(
                ancestorGuid = guid,
                tags = Tags(type = typeList),
                pageSize = pageSize,
                page = page
            )

            val result = fnOfficialApi.getMediaList(request)

            // 更新分页状态
            currentPage = page
            isLastPage = result.list.size < pageSize

            // 合并数据
            val currentData = (_uiState.value as? UiState.Success)?.data
            if (currentData != null) {
                val mergedList = currentData.list + result.list
                val mergedResult = result.copy(list = mergedList)
                _uiState.value = UiState.Success(mergedResult)
            } else {
                _uiState.value = UiState.Success(result)
            }
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "未知错误")
        }
    }

    fun refresh() {
//        loadData()
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}