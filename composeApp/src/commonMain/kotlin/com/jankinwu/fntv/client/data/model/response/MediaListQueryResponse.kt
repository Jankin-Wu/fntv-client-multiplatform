package com.jankinwu.fntv.client.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MediaListQueryResponse(
    val dir: String,
    @param:JsonProperty("jump_list")
    val jumpList: List<String>? = null,
    val list: List<MediaItem>,
    @param:JsonProperty("mdb_category")
    val mdbCategory: String,
    @param:JsonProperty("mdb_name")
    val mdbName: String,
    @param:JsonProperty("top_dir")
    val topDir: String,
    val total: Int
)

data class MediaItem(
    val guid: String,
    val lan: String,
    @param:JsonProperty("douban_id")
    val doubanId: Int,
    @param:JsonProperty("imdb_id")
    val imdbId: String,
    @param:JsonProperty("trim_id")
    val trimId: String,
    @param:JsonProperty("tv_title")
    val tvTitle: String,
    @param:JsonProperty("parent_guid")
    val parentGuid: String,
    @param:JsonProperty("parent_title")
    val parentTitle: String,
    val title: String,
    val type: String,
    val poster: String,
    @param:JsonProperty("poster_width")
    val posterWidth: Int,
    @param:JsonProperty("poster_height")
    val posterHeight: Int,
    @param:JsonProperty("is_favorite")
    val isFavorite: Int,
    val watched: Int,
    @param:JsonProperty("watched_ts")
    val watchedTs: Int,
    @param:JsonProperty("vote_average")
    val voteAverage: String,
    @param:JsonProperty("media_stream")
    val mediaStream: MediaStream,
    @param:JsonProperty("release_date")
    val releaseDate: String,
    @param:JsonProperty("season_number")
    val seasonNumber: Int,
    @param:JsonProperty("episode_number")
    val episodeNumber: Int,
    @param:JsonProperty("air_date")
    val airDate: String,
    @param:JsonProperty("number_of_seasons")
    val numberOfSeasons: Int,
    @param:JsonProperty("number_of_episodes")
    val numberOfEpisodes: Int,
    @param:JsonProperty("local_number_of_seasons")
    val localNumberOfSeasons: Int,
    @param:JsonProperty("local_number_of_episodes")
    val localNumberOfEpisodes: Int,
    val status: String,
    val overview: String,
    @param:JsonProperty("ancestor_guid")
    val ancestorGuid: String,
    @param:JsonProperty("ancestor_name")
    val ancestorName: String,
    @param:JsonProperty("ancestor_category")
    val ancestorCategory: String,
    val ts: Int,
    val duration: Int,
    @param:JsonProperty("single_child_guid")
    val singleChildGuid: String,
    @param:JsonProperty("file_name")
    val fileName: String,
    // 首播时间
    @param:JsonProperty("first_air_date")
    val firstAirDate: String? = null,
    // 最后播出时间
    @param:JsonProperty("last_air_date")
    val lastAirDate: String? = null
)

data class MediaStream(
    val resolutions: List<String>,
    @param:JsonProperty("audio_type")
    val audioType: String? = null,
    @param:JsonProperty("color_range_type")
    val colorRangeType: String? = null
)