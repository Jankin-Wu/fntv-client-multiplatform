package com.jankinwu.fntv.client.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PlayDetailResponse(
    val guid: String,
    val lan: String?,
    @param:JsonProperty("douban_id")
    val doubanId: Int?,
    @param:JsonProperty("imdb_id")
    val imdbId: String?,
    @param:JsonProperty("tv_title")
    val tvTitle: String?,
    @param:JsonProperty("parent_guid")
    val parentGuid: String?,
    @param:JsonProperty("parent_title")
    val parentTitle: String?,
    val title: String,
    val type: String,
    val poster: String,
    @param:JsonProperty("poster_width")
    val posterWidth: Int,
    @param:JsonProperty("poster_height")
    val posterHeight: Int,
    @param:JsonProperty("runtime")
    val runtime: Int?,
    @param:JsonProperty("is_favorite")
    val isFavorite: Int,
    @param:JsonProperty("watched")
    val watched: Int,
    @param:JsonProperty("watched_ts")
    val watchedTs: Int,
    @param:JsonProperty("media_stream")
    val mediaStream: MediaStream,
    @param:JsonProperty("season_number")
    val seasonNumber: Int?,
    @param:JsonProperty("episode_number")
    val episodeNumber: Int,
    @param:JsonProperty("number_of_seasons")
    val numberOfSeasons: Int?,
    @param:JsonProperty("number_of_episodes")
    val numberOfEpisodes: Int?,
    @param:JsonProperty("local_number_of_seasons")
    val localNumberOfSeasons: Int?,
    @param:JsonProperty("local_number_of_episodes")
    val localNumberOfEpisodes: Int?,
    @param:JsonProperty("status")
    val status: String?,
    @param:JsonProperty("overview")
    val overview: String? = null,
    @param:JsonProperty("ancestor_guid")
    val ancestorGuid: String?,
    @param:JsonProperty("ancestor_name")
    val ancestorName: String?,
    @param:JsonProperty("ancestor_category")
    val ancestorCategory: String?,
    @param:JsonProperty("ts")
    val ts: Int,
    @param:JsonProperty("duration")
    val duration: Int,
    @param:JsonProperty("single_child_guid")
    val singleChildGuid: String?,
    @param:JsonProperty("media_guid")
    val mediaGuid: String?,
    @param:JsonProperty("audio_guid")
    val audioGuid: String?,
    @param:JsonProperty("video_guid")
    val videoGuid: String?,
    @param:JsonProperty("subtitle_guid")
    val subtitleGuid: String?,
    @param:JsonProperty("file_name")
    val fileName: String?
)
