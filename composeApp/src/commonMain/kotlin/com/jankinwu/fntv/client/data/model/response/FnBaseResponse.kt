package com.jankinwu.fntv.client.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class FnBaseResponse<T>(
    var code: Int = 0,
    var msg: String = "",
    var data: T? = null
)