package com.jankinwu.fntv.client.data.network

import com.jankinwu.fntv.client.data.model.MediaDbData

interface FnOfficialApi {

    suspend fun getMediaDbList(): List<MediaDbData>
}