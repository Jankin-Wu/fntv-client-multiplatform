package com.jankinwu.fntv.client.enums

enum class FnTvMediaType(val value: String, val description: String) {
    MOVIE("Movie", "电影"),
    TV("TV", "电视节目"),
    DIRECTORY("Directory", "目录"),
    VIDEO("Video", "其他"),
    ;

    companion object {
        fun getAll(): List<String> {
            return entries.map { it -> it.value }
        }

        fun getDescByValue(value: String): String {
            return entries.first { it.value == value }.description
        }
    }
}