package com.jankinwu.fntv.client.data.model

/**
 * 用于保存电影海报信息的简单数据类
 */
class MediaData(
    val title: String,
    val subtitle: String,
    val score: String,
    val resolutions: List<String>,
    val posterImg: String,
    val isFavourite: Boolean = false,
    val isAlreadyWatched: Boolean = false,
    // 时长（秒）
    val duration: Long = 0,
    // 观看时间（秒）
    val ts: Long = 0
)