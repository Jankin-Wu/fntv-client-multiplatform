package com.jankinwu.fntv.client.data.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PlayPlayRequest(
    @param:JsonProperty("media_guid")
    val mediaGuid: String,

    @param:JsonProperty("video_guid")
    val videoGuid: String,

    @param:JsonProperty("video_encoder")
    val videoEncoder: String,

    @param:JsonProperty("resolution")
    val resolution: String,

    @param:JsonProperty("bitrate")
    val bitrate: Int,

    @param:JsonProperty("startTimestamp")
    val startTimestamp: Int,

    @param:JsonProperty("audio_encoder")
    val audioEncoder: String,

    @param:JsonProperty("audio_guid")
    val audioGuid: String,

    @param:JsonProperty("subtitle_guid")
    val subtitleGuid: String,

    @param:JsonProperty("channels")
    val channels: Int,

    @param:JsonProperty("forced_sdr")
    val forcedSdr: Int
)
