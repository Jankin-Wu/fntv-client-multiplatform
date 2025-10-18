package com.jankinwu.fntv.client.data.store

object AccountDataCache {

    var authorization: String = ""

    var cookieMap: MutableMap<String, String> = mutableMapOf()

    var userName: String = ""

    var password: String = ""

    var isHttps: Boolean = false

    var host: String = ""

    var port: Int = 0

    var isLoggedIn: Boolean = false

    var rememberMe: Boolean = false

    fun getFnOfficialBaseUrl(): String {
        var endpoint = host
        if (port != 0) {
            endpoint = "$endpoint:$port"
        }
        return if (isHttps) {
            "https://$endpoint"
        } else {
            "http://$endpoint"
        }
    }

    fun getCookie(): String {
        return cookieMap.entries.joinToString("; ") { "${it.key}=${it.value}" }
    }
}