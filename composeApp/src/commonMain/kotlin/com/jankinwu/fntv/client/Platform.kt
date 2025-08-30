package com.jankinwu.fntv.client

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform