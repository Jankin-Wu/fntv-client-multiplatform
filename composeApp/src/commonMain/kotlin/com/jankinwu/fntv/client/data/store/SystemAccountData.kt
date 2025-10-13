package com.jankinwu.fntv.client.data.store

object SystemAccountData {

//    var fnOfficialBaseUrl: String = ""

    var fnTvBackendBaseUrl: String = ""

    var authorization: String = ""

    var cookie: String = ""

    var userName: String = ""

    var password: String = ""

    var isHttps: Boolean = false

    var host: String = ""

    var port: Int = 0

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
}