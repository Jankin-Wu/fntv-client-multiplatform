package com.jankinwu.fntv.client.data.model.response

import androidx.compose.runtime.Immutable
import com.fasterxml.jackson.annotation.JsonProperty

@Immutable
data class LoginResponse(
    @param:JsonProperty("token")
    val token: String,
)
