package com.jankinwu.fntv.client.data.model.response

import androidx.compose.runtime.Immutable

@Immutable
data class QueryTagResponse(
    val key: String,
    val value: String
)
