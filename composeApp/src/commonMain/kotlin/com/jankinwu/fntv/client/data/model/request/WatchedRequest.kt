package com.jankinwu.fntv.client.data.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class WatchedRequest(

    @param:JsonProperty("item_guid")
    val guid: String
)
