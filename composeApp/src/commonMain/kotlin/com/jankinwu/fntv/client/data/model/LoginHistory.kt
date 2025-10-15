package com.jankinwu.fntv.client.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginHistory(
    @param:JsonProperty("host")
    val host: String,
    
    @param:JsonProperty("port")
    val port: Int,
    
    @param:JsonProperty("username")
    val username: String,
    
    @param:JsonProperty("password")
    val password: String?,
    
    @param:JsonProperty("isHttps")
    val isHttps: Boolean,
    
    @param:JsonProperty("rememberMe")
    val rememberMe: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LoginHistory) return false
        
        return host == other.host && 
               port == other.port && 
               username == other.username
    }
    
    override fun hashCode(): Int {
        var result = host.hashCode()
        result = 31 * result + port
        result = 31 * result + username.hashCode()
        return result
    }
    
    fun getDisplayAddress(): String {
        return if (port != 0) {
            "$host:$port"
        } else {
            host
        }
    }
}