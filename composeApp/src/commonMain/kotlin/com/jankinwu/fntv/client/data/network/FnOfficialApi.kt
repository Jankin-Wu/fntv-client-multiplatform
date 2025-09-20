package com.jankinwu.fntv.client.data.network

import com.jankinwu.fntv.client.data.model.request.MediaListQueryRequest
import com.jankinwu.fntv.client.data.model.response.GenresResponse
import com.jankinwu.fntv.client.data.model.response.MediaListQueryResponse
import com.jankinwu.fntv.client.data.model.response.MediaDbListResponse
import com.jankinwu.fntv.client.data.model.response.PlayDetailResponse
import com.jankinwu.fntv.client.data.model.response.QueryTagResponse
import com.jankinwu.fntv.client.data.model.response.TagListResponse

interface FnOfficialApi {

    suspend fun getMediaDbList(): List<MediaDbListResponse>

    suspend fun getMediaList(request: MediaListQueryRequest): MediaListQueryResponse

    suspend fun getPlayList(): List<PlayDetailResponse>

    suspend fun favorite(guid: String): Boolean

    suspend fun cancelFavorite(guid: String): Boolean

    suspend fun watched(guid: String): Boolean

    suspend fun cancelWatched(guid: String): Boolean

    suspend fun getGenres(lan: String): List<GenresResponse>

    suspend fun getTag(tag: String, lan: String): List<QueryTagResponse>

    suspend fun getTagList(ancestorGuid:  String?, isFavorite: Int, type: String?): TagListResponse
}