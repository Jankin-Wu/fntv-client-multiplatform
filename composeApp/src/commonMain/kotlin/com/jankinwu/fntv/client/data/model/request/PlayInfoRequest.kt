package com.jankinwu.fntv.client.data.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PlayInfoRequest(
    @param:JsonProperty("item_guid")
    val itemGuid: String
)