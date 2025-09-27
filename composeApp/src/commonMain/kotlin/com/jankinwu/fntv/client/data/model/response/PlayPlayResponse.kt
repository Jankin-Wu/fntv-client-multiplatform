package com.jankinwu.fntv.client.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PlayPlayResponse(
    @param:JsonProperty("audio_guid")
    val audioGuid: String,
    @param:JsonProperty("audio_index")
    val audioIndex: Int,
    @param:JsonProperty("hls_time")
    val hlsTime: Int,
    @param:JsonProperty("is_subtitle_external")
    val isSubtitleExternal: Int,
    @param:JsonProperty("media_guid")
    val mediaGuid: String,
    @param:JsonProperty("play_link")
    val playLink: String,
    @param:JsonProperty("subtitle_guid")
    val subtitleGuid: String,
    @param:JsonProperty("subtitle_index")
    val subtitleIndex: Int,
    @param:JsonProperty("subtitle_link")
    val subtitleLink: String,
    @param:JsonProperty("video_guid")
    val videoGuid: String,
    @param:JsonProperty("video_index")
    val videoIndex: Int,
    @param:JsonProperty("non_fatal_errno")
    val nonFatalErrno: Int?,
    @param:JsonProperty("video_encoder")
    val videoEncoder: String,
    @param:JsonProperty("not_supported_resolution")
    val notSupportedResolution: String,
    @param:JsonProperty("supported_highest_resolution")
    val supportedHighestResolution: String
)
