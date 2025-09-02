package com.jankinwu.fntv.client.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val baseJsonConf = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}

val client = HttpClient {
    install(ContentNegotiation) {
        json(baseJsonConf)
    }
}
