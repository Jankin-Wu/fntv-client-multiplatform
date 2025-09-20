package com.jankinwu.fntv.client.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TagListResponse(
    @param:JsonProperty("genres")
    val genres: List<Int> = emptyList(),

    @param:JsonProperty("resolutions")
    val resolutions: List<String> = emptyList(),

    @param:JsonProperty("color_range")
    val colorRange: List<String> = emptyList(),

    @param:JsonProperty("audio_type")
    val audioType: List<String> = emptyList(),

    @param:JsonProperty("locate")
    val locate: List<String> = emptyList(),

    @param:JsonProperty("decades")
    val decades: List<String> = emptyList(),

    @param:JsonProperty("recognition_status")
    val recognitionStatus: List<Int> = emptyList()
)
