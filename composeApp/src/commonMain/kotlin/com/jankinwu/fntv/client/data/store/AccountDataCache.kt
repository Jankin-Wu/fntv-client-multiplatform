package com.jankinwu.fntv.client.data.store

object AccountDataCache {

    var authorization: String = ""

    var cookieMap: Map<String, String> = mutableMapOf()

    var userName: String = ""

    var password: String = ""

    var isHttps: Boolean = false

    var host: String = ""

    var port: Int = 0

    var isLoggedIn: Boolean = false

    var rememberMe: Boolean = false

    fun getFnOfficialBaseUrl(): String {
        var server = host
        if (port != 0) {
            server = "$server:$port"
        }
        return if (isHttps) {
            "https://$server"
        } else {
            "http://$server"
        }
    }

    fun getCookie(): String {
        return cookieMap.entries.joinToString("; ") { "${it.key}=${it.value}" }
    }
}