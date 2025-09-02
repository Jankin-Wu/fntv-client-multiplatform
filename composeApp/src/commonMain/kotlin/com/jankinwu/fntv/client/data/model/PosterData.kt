package com.jankinwu.fntv.client.data.model

/**
 * 用于保存电影海报信息的简单数据类
 */
class PosterData(
    val title: String,
    val subtitle: String,
    val score: String,
//    val quality: String,
    val resolutions: List<String>,
    val posterImg: String,
    val isFavourite: Boolean = false,
    val isAlreadyWatched: Boolean = false
)