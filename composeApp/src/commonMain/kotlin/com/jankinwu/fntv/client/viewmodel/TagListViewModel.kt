package com.jankinwu.fntv.client.viewmodel

import androidx.lifecycle.viewModelScope
import com.jankinwu.fntv.client.data.model.response.TagListResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class TagListViewModel : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)

    private val _uiState = MutableStateFlow<UiState<TagListResponse>>(UiState.Initial)
    val uiState: StateFlow<UiState<TagListResponse>> = _uiState.asStateFlow()

    /**
     * 加载标签列表数据
     * @param ancestorGuid 父级标签GUID
     * @param isFavorite 是否收藏 0:全部 1:已收藏 2:未收藏
     * @param type 标签类型
     */
    fun loadTagList(
        ancestorGuid: String? = null,
        isFavorite: Int = 0,
        type: String? = null
    ) {
        viewModelScope.launch {
            executeWithLoading(_uiState, "loadTagList") {
                fnOfficialApi.getTagList(ancestorGuid, isFavorite, type)
            }
        }
    }

    /**
     * 刷新标签列表数据
     * @param ancestorGuid 父级标签GUID
     * @param isFavorite 是否收藏 0:全部 1:已收藏 2:未收藏
     * @param type 标签类型
     */
    fun refreshTagList(
        ancestorGuid: String? = null,
        isFavorite: Int = 0,
        type: String? = null
    ) {
        loadTagList(ancestorGuid, isFavorite, type)
    }

    /**
     * 清除错误状态
     */
    fun clearError() {
        _uiState.value = UiState.Initial
    }
}