package com.jankinwu.fntv.client.data.store

object SystemAccountData {

    var fnOfficialBaseUrl: String = ""

    var fnTvBackendBaseUrl: String = ""

    var authorization: String = ""

    var cookie: String = ""

    var userName: String = ""

    var password: String = ""

    var isHttps: Boolean = false

    var host: String = ""

    fun getFnOfficialBaseUrl(): String {
        return if (isHttps) {
            "https://$host"
        } else {
            "http://$host"
        }
    }
}