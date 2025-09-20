package com.jankinwu.fntv.client.enums

enum class FnTvMediaType(val value: String, val description: String) {
    MOVIE("Movie", "电影"),
    TV("TV", "电视节目"),
    DIRECTORY("Directory", "目录"),
    VIDEO("Video", "其他"),
    EPISODE("Episode", "剧集"),
    SEASON("Season", "季"),
    ;

    companion object {
        fun getCommonly(): List<String> {
            return entries.map { it -> it.value } - EPISODE.value - SEASON.value
        }

        fun getDescByValue(value: String): String {
            return entries.first { it.value == value }.description
        }

        fun getByCategory(category: String): List<String> {
            return when (category) {
                Category.OTHERS.value -> {
                    listOf(DIRECTORY.value, VIDEO.value)
                }
                else -> {
                    getCommonly()
                }
            }
        }
    }
}