package com.jankinwu.fntv.client.data.model.response

import androidx.compose.runtime.Immutable
import com.fasterxml.jackson.annotation.JsonProperty

@Immutable
data class PersonListResponse(
    @param:JsonProperty("item_guid")
    val itemGuid: String,

    @param:JsonProperty("person_guid")
    val personGuid: String,

    @param:JsonProperty("role")
    val role: String,

    @param:JsonProperty("job")
    val job: String,

    @param:JsonProperty("order")
    val order: Int,

    @param:JsonProperty("department")
    val department: String,

    @param:JsonProperty("trim_id")
    val trimId: String,

    @param:JsonProperty("imdb_id")
    val imdbId: String,

    @param:JsonProperty("tmdb_id")
    val tmdbId: Int,

    @param:JsonProperty("lan")
    val lan: String,

    @param:JsonProperty("name")
    val name: String,

    @param:JsonProperty("original_name")
    val originalName: String,

    @param:JsonProperty("also_know_as")
    val alsoKnowAs: String,

    @param:JsonProperty("biography")
    val biography: String,

    @param:JsonProperty("known_for_department")
    val knownForDepartment: String,

    @param:JsonProperty("profile_path")
    val profilePath: String,

    @param:JsonProperty("gender")
    val gender: Int
)
