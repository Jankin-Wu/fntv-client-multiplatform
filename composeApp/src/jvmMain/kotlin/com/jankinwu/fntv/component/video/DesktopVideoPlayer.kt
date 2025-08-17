package org.jetbrains.compose.videoplayer

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import java.awt.Component
import java.util.*
import kotlin.math.roundToInt

@Composable
fun VideoPlayerImpl(
    url: String,
    isResumed: Boolean,
    volume: Float,
    speed: Float,
    seek: Float,
    isFullscreen: Boolean,
    progressState: MutableState<Progress>,
    modifier: Modifier,
    headers: Map<String, String>,// 添加 headers 参数
    onFinish: (() -> Unit)?
) {
    val mediaPlayerComponent = remember { initializeMediaPlayerComponent() }
    val mediaPlayer = remember { mediaPlayerComponent.mediaPlayer() }
    mediaPlayer.emitProgressTo(progressState)
    mediaPlayer.setupVideoFinishHandler(onFinish)
    val factory = remember { { mediaPlayerComponent } }
    /* OR the following code and using SwingPanel(factory = { factory }, ...) */
    // val factory by rememberUpdatedState(mediaPlayerComponent)

    LaunchedEffect(mediaPlayerComponent, url, headers) { // 将 headers 添加为 key
        // 等待组件变为可显示状态
        while (!mediaPlayerComponent.isDisplayable) {
            delay(50)
        }

        // 将 headers Map 转换为 vlcj 需要的媒体选项
        val mediaOptions = headers.map { (key, value) ->
            // VLC 对常用头有特定选项，其他则使用通用选项
            when (key.lowercase(Locale.ENGLISH)) {
                "user-agent" -> ":http-user-agent=$value"
                "cookie" -> ":http-cookie=$value"
                // HTTP 请求头的 key 是大小写敏感的，所以我们使用原始的 key
                else -> ":http-header=$key: $value"
            }
        }.toTypedArray()
//        mediaOptions.add(":network-caching=2000")
        // 使用能接受媒体选项的 play 方法
        val result = mediaPlayer.media().play(url, *mediaOptions)
        println("Play result: $result")
        println("Media MRL: ${mediaPlayer.media().info()?.mrl()}")

        while (!mediaPlayer.status().isPlaying) {
            delay(1000)
            println("Is playing: ${mediaPlayer.status().isPlaying}")
            println("Is playable: ${mediaPlayer.status().isPlayable}")
        }
        // 延迟检查播放状态
        delay(100)
        println("Is playing: ${mediaPlayer.status().isPlaying}")
        println("Media state: ${mediaPlayer.status().state()}")
    }
    LaunchedEffect(seek) { mediaPlayer.controls().setPosition(seek) }
    LaunchedEffect(speed) { mediaPlayer.controls().setRate(speed) }
    LaunchedEffect(volume) { mediaPlayer.audio().setVolume(volume.toPercentage()) }
    LaunchedEffect(isResumed) { mediaPlayer.controls().setPause(!isResumed) }
    LaunchedEffect(isFullscreen) {
        if (mediaPlayer is EmbeddedMediaPlayer) {
            /*
             * To be able to access window in the commented code below,
             * extend the player composable function from WindowScope.
             * See https://github.com/JetBrains/compose-jb/issues/176#issuecomment-812514936
             * and its subsequent comments.
             *
             * We could also just fullscreen the whole window:
             * `window.placement = WindowPlacement.Fullscreen`
             * See https://github.com/JetBrains/compose-multiplatform/issues/1489
             */
            // mediaPlayer.fullScreen().strategy(ExclusiveModeFullScreenStrategy(window))
            mediaPlayer.fullScreen().toggle()
        }
    }
    DisposableEffect(Unit) { onDispose(mediaPlayer::release) }
    SwingPanel(
        factory = factory,
        background = Color.Transparent,
        modifier = modifier
    )
}

private fun Float.toPercentage(): Int = (this * 100).roundToInt()

/**
 * See https://github.com/caprica/vlcj/issues/887#issuecomment-503288294
 * for why we're using CallbackMediaPlayerComponent for macOS.
 */
private fun initializeMediaPlayerComponent(): Component {
    println("Discovering native libraries...")
    val discovery = NativeDiscovery()
    val discovered = discovery.discover()
    println("Native discovery result: $discovered")
    println("Native library path: ${System.getProperty("jna.library.path")}")
    NativeDiscovery().discover()
    return if (isMacOS()) {
        CallbackMediaPlayerComponent()
    } else {
        println("Initializing EmbeddedMediaPlayerComponent")
        EmbeddedMediaPlayerComponent()
    }
}

/**
 * We play the video again on finish (so the player is kind of idempotent),
 * unless the [onFinish] callback stops the playback.
 * Using `mediaPlayer.controls().repeat = true` did not work as expected.
 */
@Composable
private fun MediaPlayer.setupVideoFinishHandler(onFinish: (() -> Unit)?) {
    DisposableEffect(onFinish) {
        val listener = object : MediaPlayerEventAdapter() {
            override fun finished(mediaPlayer: MediaPlayer) {
                println("Video finished playing")
                onFinish?.invoke()
                mediaPlayer.submit { mediaPlayer.controls().play() }
            }

            override fun error(mediaPlayer: MediaPlayer) {
                println("Error occurred during playback")
                println("Error count: ${mediaPlayer.media().info()}")
            }

            override fun playing(mediaPlayer: MediaPlayer) {
                println("Video started playing")
            }

            override fun paused(mediaPlayer: MediaPlayer) {
                println("Video paused")
            }

            override fun stopped(mediaPlayer: MediaPlayer) {
                println("Video stopped")
            }

            override fun opening(mediaPlayer: MediaPlayer) {
                println("Media opening")
            }

            override fun buffering(mediaPlayer: MediaPlayer, newCache: Float) {
                println("Buffering: $newCache%")
            }
        }
        events().addMediaPlayerEventListener(listener)
        onDispose { events().removeMediaPlayerEventListener(listener) }
    }
}

/**
 * Checks for and emits video progress every 50 milliseconds.
 * Note that it seems vlcj updates the progress only every 250 milliseconds or so.
 *
 * Instead of using `Unit` as the `key1` for [LaunchedEffect],
 * we could use `media().info()?.mrl()` if it's needed to re-launch
 * the effect (for whatever reason) when the url (aka video) changes.
 */
@Composable
private fun MediaPlayer.emitProgressTo(state: MutableState<Progress>) {
    LaunchedEffect(key1 = Unit) {
        while (isActive) {
            val fraction = status().position()
            val time = status().time()
            state.value = Progress(fraction, time)
            delay(50)
        }
    }
}

/**
 * Returns [MediaPlayer] from player components.
 * The method names are the same, but they don't share the same parent/interface.
 * That's why we need this method.
 */
private fun Component.mediaPlayer() = when (this) {
    is CallbackMediaPlayerComponent -> mediaPlayer()
    is EmbeddedMediaPlayerComponent -> mediaPlayer()
    else -> error("mediaPlayer() can only be called on vlcj player components")
}

private fun isMacOS(): Boolean {
    val os = System
        .getProperty("os.name", "generic")
        .lowercase(Locale.ENGLISH)
    return "mac" in os || "darwin" in os
}
