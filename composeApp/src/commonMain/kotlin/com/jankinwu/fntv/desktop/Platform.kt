package com.jankinwu.fntv.desktop

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform