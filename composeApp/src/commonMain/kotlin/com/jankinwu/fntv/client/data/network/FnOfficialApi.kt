package com.jankinwu.fntv.client.data.network

import com.jankinwu.fntv.client.data.model.request.MediaListQueryRequest
import com.jankinwu.fntv.client.data.model.response.MediaListQueryResponse
import com.jankinwu.fntv.client.data.model.response.MediaDbListResponse
import com.jankinwu.fntv.client.data.model.response.PlayDetailResponse

interface FnOfficialApi {

    suspend fun getMediaDbList(): List<MediaDbListResponse>

    suspend fun getMediaList(request: MediaListQueryRequest): MediaListQueryResponse

    suspend fun getPlayList(): List<PlayDetailResponse>

    suspend fun favorite(guid: String): Boolean

    suspend fun cancelFavorite(guid: String): Boolean

    suspend fun watched(guid: String): Boolean

    suspend fun cancelWatched(guid: String): Boolean
}