package com.jankinwu.fntv.client.manager

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.jankinwu.fntv.client.data.model.LoginHistory
import com.jankinwu.fntv.client.data.store.AccountDataCache
import com.russhwolf.settings.Settings

class PreferencesManager private constructor() {
    private val settings = Settings()
//    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        @Volatile
        private var INSTANCE: PreferencesManager? = null

        fun getInstance(): PreferencesManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesManager().also { INSTANCE = it }
            }
        }
        val mapper = jacksonObjectMapper().apply {
            // 禁止格式化输出
            disable(SerializationFeature.INDENT_OUTPUT)
            // 忽略未知字段
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            // 不序列化null值
            disable(SerializationFeature.WRITE_NULL_MAP_VALUES)
//            setSerializationInclusion(JsonInclude.Include.NON_NULL)
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
        AccountDataCache.userName = settings.getString("username", "")
        AccountDataCache.password = settings.getString("password", "")
        AccountDataCache.authorization = settings.getString("token", "")
        AccountDataCache.isHttps = settings.getBoolean("isHttps", false)
        AccountDataCache.host = settings.getString("host", "")
        AccountDataCache.port = settings.getInt("port", 0)
        AccountDataCache.isLoggedIn = settings.getBoolean("isLoggedIn", false)
        val cookie = settings.getString("cookie", "")
        if (cookie.isNotBlank()) {
            AccountDataCache.cookieMap = cookie.split("; ").associate {
                val (key, value) = it.split("=", limit = 2)
                key to value
            } as MutableMap<String, String>
        }
        AccountDataCache.rememberMe = settings.getBoolean("rememberMe", false)
    }

    fun saveAllLoginInfo() {
        settings.putString("username", AccountDataCache.userName)
        settings.putString("password", AccountDataCache.password)
        settings.putString("token", AccountDataCache.authorization)
        settings.putBoolean("isHttps", AccountDataCache.isHttps)
        settings.putString("host", AccountDataCache.host)
        settings.putInt("port", AccountDataCache.port)
        settings.putBoolean("isLoggedIn", AccountDataCache.isLoggedIn)
        val cookie =
            AccountDataCache.cookieMap.entries.joinToString("; ") { "${it.key}=${it.value}" }
        settings.putString("cookie", cookie)
        settings.putBoolean("rememberMe", AccountDataCache.rememberMe)
    }

    fun saveToken(token: String) {
        settings.putString("token", token)
        val cookie =
            AccountDataCache.cookieMap.entries.joinToString("; ") { "${it.key}=${it.value}" }
        settings.putString("cookie", cookie)
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
    
    // 新增历史记录相关方法
    fun saveLoginHistory(historyList: List<LoginHistory>) {
        val historyJson = mapper.writeValueAsString(historyList)
//        val historyJson = json.encodeToString(historyList)
        settings.putString("loginHistory", historyJson)
    }
    
    fun loadLoginHistory(): List<LoginHistory> {
        val historyJson = settings.getString("loginHistory", "[]")
        return try {
            mapper.readValue<List<LoginHistory>>(historyJson)
//            json.decodeFromString<List<LoginHistory>>(historyJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
}