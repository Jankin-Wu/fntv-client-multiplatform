package com.jankinwu.fntv.client.data.model

import com.jankinwu.fntv.client.data.model.request.PlayPlayRequest
import com.jankinwu.fntv.client.data.model.response.StreamResponse

/**
 * 播放信息缓存数据类
 * 用于缓存当前播放的视频流、音频流、字幕流等信息
 * 生命周期跟随播放器
 */
data class PlayingInfoCache(
    val playRequest: PlayPlayRequest,
    val streamInfo: StreamResponse,
    val playLink: String
)