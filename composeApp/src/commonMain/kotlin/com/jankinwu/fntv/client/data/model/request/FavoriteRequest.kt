package com.jankinwu.fntv.client.data.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class FavoriteRequest(

    @param:JsonProperty("item_guid")
    val guid: String
)
