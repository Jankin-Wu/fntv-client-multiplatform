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
    
    fun saveLoginInfo(username: String, password: String, token: String, isHttps: Boolean = false) {
        settings.putString("username", username)
        settings.putString("password", password)
        settings.putString("token", token)
        settings.putBoolean("isHttps", isHttps)
    }
    
    fun saveToken(token: String) {
        settings.putString("token", token)
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
    
    fun clearLoginInfo() {
        settings.remove("username")
        settings.remove("password")
        settings.remove("token")
    }
    
    fun hasSavedCredentials(): Boolean {
        return settings.getString("username", "").isNotEmpty() && 
               settings.getString("password", "").isNotEmpty()
    }
}