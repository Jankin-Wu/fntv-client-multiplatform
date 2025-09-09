package com.jankinwu.fntv.client.data.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class MediaListQueryRequest (
    @param:JsonProperty("ancestor_guid")
    val ancestorGuid: String,

    @param:JsonProperty("exclude_grouped_video")
    val excludeGroupedVideo: Int = 1,

    @param:JsonProperty("sort_type")
    val sortType: String = "DESC",

    @param:JsonProperty("sort_column")
    val sortColumn: String = "create_time",

    @param:JsonProperty("page_size")
    val pageSize: Int = 22,

    @param:JsonProperty("page")
    val page: Int = 1,

    val tags: Tags
)

data class Tags(
    @param:JsonProperty("type")
    val type: List<String>
)