package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.request.ItemListQueryRequest
import com.jankinwu.fntv.client.data.model.request.Tags
import com.jankinwu.fntv.client.data.model.response.ItemListQueryResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class ItemListViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<ItemListQueryResponse>>(UiState.Initial)

    val uiState: StateFlow<UiState<ItemListQueryResponse>> = _uiState.asStateFlow()

    private var currentPage = 1

    var isLastPage = false

    fun loadData(guid: String, tags: Tags, pageSize: Int = 22, isLoadMore: Boolean = false, sortColumn: String = "create_time", sortOrder: String = "DESC") {
        // 重置状态
        currentPage = 1
        isLastPage = false
        _uiState.value = UiState.Initial

        loadPageData(guid, tags, pageSize, currentPage,isLoadMore, sortColumn, sortOrder)
    }

    fun loadMoreData(guid: String, tags: Tags, pageSize: Int = 50, isLoadMore: Boolean = false, sortColumn: String = "create_time", sortOrder: String = "DESC") {
        if (!isLastPage) {
            loadPageData(guid, tags, pageSize, currentPage + 1, isLoadMore, sortColumn, sortOrder)
        }
    }

    fun loadPageData(
        guid: String,
        tags: Tags,
        pageSize: Int,
        page: Int = 1,
        isLoadMore: Boolean = false,
        sortColumn: String,
        sortOrder: String
    ) {
        viewModelScope.launch {
            // 如果是加载更多，使用专门的加载方法避免覆盖现有数据
            if (isLoadMore && page > 1) {
                loadMoreDataInternal(guid, tags, pageSize, page)
            } else {
                // 首次加载使用原有方法
                executeWithLoading(_uiState) {
                    val request = ItemListQueryRequest(
                        ancestorGuid = guid,
                        tags = tags,
                        pageSize = pageSize,
                        page = page,
                        sortColumn = sortColumn,
                        sortType = sortOrder
                    )
                    val result = fnOfficialApi.getItemList(request)

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
        tags: Tags,
        pageSize: Int,
        page: Int
    ) {
        try {
            val request = ItemListQueryRequest(
                ancestorGuid = guid,
                tags = tags,
                pageSize = pageSize,
                page = page
            )

            val result = fnOfficialApi.getItemList(request)

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