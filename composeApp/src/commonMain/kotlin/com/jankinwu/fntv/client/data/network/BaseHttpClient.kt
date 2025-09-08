package com.jankinwu.fntv.client.data.network

import com.jankinwu.fntv.client.data.model.SystemAccountData
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val baseJsonConf = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}

val fnOfficialClient = HttpClient {
    expectSuccess = true
    install(HttpTimeout) {
        val timeout = 30000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
    install(ContentNegotiation) {
        json(baseJsonConf)
    }
    // 添加公共请求头
    defaultRequest {
        header(HttpHeaders.Authorization, SystemAccountData.authorization)
        header(HttpHeaders.Accept, "application/json")
    }
}
