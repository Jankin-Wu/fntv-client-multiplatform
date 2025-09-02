package com.jankinwu.fntv.client.data.network

import com.jankinwu.fntv.client.data.model.SystemAccountData
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.headers


suspend fun getMediaDbList(): List<String> {
    val response = client.get("${SystemAccountData}/v/api/v1/mediadb/list")
    {
        headers {
            append(HttpHeaders.Authorization, SystemAccountData.authorization)

            append(HttpHeaders.Accept, "application/json")
        }
    }
    return listOf()
}

