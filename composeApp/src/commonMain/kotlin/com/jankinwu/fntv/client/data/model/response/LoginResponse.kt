package com.jankinwu.fntv.client.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse(
    @param:JsonProperty("token")
    val token: String,
)
