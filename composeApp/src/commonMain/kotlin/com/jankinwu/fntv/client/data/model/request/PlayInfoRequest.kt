package com.jankinwu.fntv.client.data.model.request

import androidx.compose.runtime.Immutable
import com.fasterxml.jackson.annotation.JsonProperty

@Immutable
data class PlayInfoRequest(
    @param:JsonProperty("item_guid")
    val itemGuid: String
)