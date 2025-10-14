package com.jankinwu.fntv.client.data.network

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.jankinwu.fntv.client.data.store.AccountDataCache
import com.jankinwu.fntv.client.data.network.impl.FnOfficialApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.jackson.jackson
import org.koin.dsl.module

val fnOfficialClient = HttpClient {
    expectSuccess = true
    install(HttpTimeout) {
        val timeout = 10000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
    install(ContentNegotiation) {
        // Jackson 的 ObjectMapper 的自定义配置
        jackson {
            // 禁止格式化输出 JSON
            disable(SerializationFeature.INDENT_OUTPUT)
            // 将日期序列化为 ISO 字符串而不是时间戳
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // 忽略未知属性
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            // 不序列化null值
            disable(SerializationFeature.WRITE_NULL_MAP_VALUES)
            setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
        }

    }
    // 添加公共请求头
    defaultRequest {
        header(HttpHeaders.Authorization, AccountDataCache.authorization)
        header(HttpHeaders.Accept, "application/json")
        if (AccountDataCache.getCookie().isNotBlank()) {
            header(HttpHeaders.Cookie, AccountDataCache.getCookie())
        }
    }
}

val apiModule = module {
    single { FnOfficialApiImpl() }
}