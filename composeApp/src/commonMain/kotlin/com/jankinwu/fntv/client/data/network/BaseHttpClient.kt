package com.jankinwu.fntv.client.data.network

import com.fasterxml.jackson.databind.SerializationFeature
import com.jankinwu.fntv.client.data.model.SystemAccountData
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.jackson.jackson
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
//        json(baseJsonConf)
        jackson {
            // 在这里进行 Jackson 的 ObjectMapper 的自定义配置
            // 例如：
            enable(SerializationFeature.INDENT_OUTPUT) // 格式化输出 JSON
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 将日期序列化为 ISO 字符串而不是时间戳
//            registerModule(JavaTimeModule()) // 如果你需要处理 LocalDate, LocalDateTime 等

            // 你还可以配置其他的 ObjectMapper 属性，例如：
            // setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
            // propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        }

    }
    // 添加公共请求头
    defaultRequest {
        header(HttpHeaders.Authorization, SystemAccountData.authorization)
        header(HttpHeaders.Accept, "application/json")
        if (SystemAccountData.cookie.isNotBlank()) {
            header(HttpHeaders.Cookie, SystemAccountData.cookie)
        }
    }
}
