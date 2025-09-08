package com.jankinwu.fntv.client.data.model

import com.fasterxml.jackson.annotation.JsonProperty


data class MediaDbData (
    val guid: String,
    val title: String,
    val posters: List<String>,
    val category: String,
    @JsonProperty("view_type")
    val viewType: Int
)