package com.jankinwu.fntv.client.viewmodel

import com.jankinwu.fntv.client.data.model.response.PersonListResponse
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

class PersonListViewModel() : BaseViewModel() {

    private val fnOfficialApi: FnOfficialApiImpl by inject(FnOfficialApiImpl::class.java)
    private val _uiState = MutableStateFlow<UiState<List<PersonListResponse>>>(UiState.Initial)
    val personListState: StateFlow<UiState<List<PersonListResponse>>> = _uiState

    suspend fun loadPersonList(guid: String) {
        executeWithLoading(_uiState) {
            fnOfficialApi.personList(guid)
        }
    }
}