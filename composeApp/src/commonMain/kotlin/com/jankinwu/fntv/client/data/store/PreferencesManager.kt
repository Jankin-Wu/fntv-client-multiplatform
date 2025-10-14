package com.jankinwu.fntv.client.data.store

import com.russhwolf.settings.Settings

class PreferencesManager private constructor() {
    private val settings = Settings()

    companion object {
        @Volatile
        private var INSTANCE: PreferencesManager? = null

        fun getInstance(): PreferencesManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesManager().also { INSTANCE = it }
            }
        }
    }

    fun saveLoginInfo(
        username: String,
        password: String,
        token: String,
        isHttps: Boolean = false,
        host: String,
        port: Int
    ) {
        settings.putString("username", username)
        settings.putString("password", password)
        settings.putString("token", token)
        settings.putBoolean("isHttps", isHttps)
        settings.putString("host", host)
        settings.putInt("port", port)
    }

    fun loadAllLoginInfo() {
        SystemAccountDataCache.userName = settings.getString("username", "")
        SystemAccountDataCache.password = settings.getString("password", "")
        SystemAccountDataCache.authorization = settings.getString("token", "")
        SystemAccountDataCache.isHttps = settings.getBoolean("isHttps", false)
        SystemAccountDataCache.host = settings.getString("host", "")
        SystemAccountDataCache.port = settings.getInt("port", 0)
        SystemAccountDataCache.isLoggedIn = settings.getBoolean("isLoggedIn", false)
        val cookie = settings.getString("cookie", "")
        SystemAccountDataCache.cookieMap = cookie.split("; ").associate {
            val (key, value) = it.split("=", limit = 2)
            key to value
        }
        SystemAccountDataCache.rememberMe = settings.getBoolean("rememberMe", false)
    }

    fun saveAllLoginInfo() {
        settings.putString("username", SystemAccountDataCache.userName)
        settings.putString("password", SystemAccountDataCache.password)
        settings.putString("token", SystemAccountDataCache.authorization)
        settings.putBoolean("isHttps", SystemAccountDataCache.isHttps)
        settings.putString("host", SystemAccountDataCache.host)
        settings.putInt("port", SystemAccountDataCache.port)
        settings.putBoolean("isLoggedIn", SystemAccountDataCache.isLoggedIn)
        val cookie =
            SystemAccountDataCache.cookieMap.entries.joinToString("; ") { "${it.key}=${it.value}" }
        settings.putString("cookie", cookie)
        settings.putBoolean("rememberMe", SystemAccountDataCache.rememberMe)
    }

    fun saveToken(token: String) {
        settings.putString("token", token)
        val cookie =
            SystemAccountDataCache.cookieMap.entries.joinToString("; ") { "${it.key}=${it.value}" }
        settings.putString("cookie", cookie)
    }

    fun getSavedUsername(): String {
        return settings.getString("username", "")
    }

    fun getSavedPassword(): String {
        return settings.getString("password", "")
    }

    fun getSavedToken(): String {
        return settings.getString("token", "")
    }

    fun isHttps(): Boolean {
        return settings.getBoolean("isHttps", false)
    }

    fun getSavedHost(): String {
        return settings.getString("host", "")
    }

    fun getSavedPort(): Int {
        return settings.getInt("port", 0)
    }

    fun clearLoginInfo() {
        settings.remove("username")
        settings.remove("password")
        settings.remove("token")
        settings.remove("isHttps")
        settings.remove("host")
        settings.remove("port")
        settings.remove("cookie")
        settings.remove("isLoggedIn")
        settings.remove("rememberMe")
    }

    fun hasSavedCredentials(): Boolean {
        return settings.getString("username", "").isNotEmpty() &&
                settings.getString("password", "").isNotEmpty()
    }
}