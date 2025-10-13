package com.jankinwu.fntv.client.data.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    @param:JsonProperty("username")
    val username: String,
    @param:JsonProperty("password")
    val password: String,
    @param:JsonProperty("app_name")
    val appName: String = "trimemedia-web",
)
