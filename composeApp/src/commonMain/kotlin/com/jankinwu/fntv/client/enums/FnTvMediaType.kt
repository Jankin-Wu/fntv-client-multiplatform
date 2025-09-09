package com.jankinwu.fntv.client.enums

enum class FnTvMediaType(val value: String) {
    MOVIE("Movie"),
    TV("TV"),
    DIRECTORY("Directory"),
    VIDEO("Video"),
    ;

    companion object {
        fun getAll(): List<String> {
            return entries.map { it -> it.value }
        }
    }
}