package com.jankinwu.fntv.client.data.model.request

import androidx.compose.runtime.Immutable
import com.fasterxml.jackson.annotation.JsonProperty

@Immutable
data class StreamRequest(
    @param:JsonProperty("media_guid")
    val mediaGuid: String,
    
    @param:JsonProperty("ip")
    val ip: String,
    
    @param:JsonProperty("header")
    val header: Header,
    
    @param:JsonProperty("level")
    val level: Int  = 1
) {
    data class Header(
        @param:JsonProperty("User-Agent")
        val userAgent: List<String>
    )
}