package com.jankinwu.fntv.client.data.model.response

import androidx.compose.runtime.Immutable
import com.fasterxml.jackson.annotation.JsonProperty

@Immutable
data class MediaDbListResponse (
    val guid: String,
    val title: String,
    val posters: List<String>,
    val category: String,
    @param:JsonProperty("view_type")
    val viewType: Int
)