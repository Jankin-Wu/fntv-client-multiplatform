package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jankinwu.fntv.client.data.model.PosterData
import com.jankinwu.fntv.client.ui.component.MediaLibGallery

@Composable
fun HomePageScreen() {

    val sampleMovies = remember {
        listOf(
            PosterData("黑袍纠察队", "共4季·2019~2024", "8.5", listOf("4K", "1080"), ""),
            PosterData("曼达洛人", "共3季·2019", "8.4", listOf("4K"), ""),
            PosterData("爱，死亡和机器人", "共4季·2019~2022", "8.3", listOf("1080"), ""),
            PosterData("洛基", "共2季·2021~2023", "8.2", listOf("4K"), ""),
            PosterData("最后生还者", "共2季·2023", "8.6", listOf("4K"), ""),
            PosterData("惩罚者", "共2季·2017~2019", "8.1", listOf("4K"), ""),
            PosterData("夜魔侠", "共4季·2015~2018", "8.2", listOf("720"), ""),
            PosterData("卢克·凯奇", "共2季·2016", "6.9", listOf("720"), ""),
            PosterData("斗篷与匕首", "共2季·2018", "7.1", listOf("1080p"), ""),
            PosterData("绝命毒师", "共5季·2008~2013", "8.9", listOf("4K"), ""),
            PosterData("夜魔侠：重生", "共1季·2025", "8.6", listOf("4K"), ""),
            PosterData("黑袍纠察队", "共4季·2019~2024", "8.5", listOf("4K"), ""),
            PosterData("曼达洛人", "共3季·2019", "8.4", listOf("4K"), ""),
            PosterData("爱，死亡和机器人", "共4季·2019~2022", "8.3", listOf("1080"), ""),
            PosterData("洛基", "共2季·2021~2023", "8.2", listOf("1080"), ""),
            PosterData("最后生还者", "共2季·2023", "8.6", listOf("4K"), ""),
            PosterData("惩罚者", "共2季·2017~2019", "8.1", listOf("4K"), ""),
            PosterData("夜魔侠", "共4季·2015~2018", "8.2", listOf("1080"), ""),
            PosterData("卢克·凯奇", "共2季·2016", "6.9", listOf("1080"), ""),
            PosterData("斗篷与匕首", "共2季·2018", "7.1", listOf("1080"), ""),
            PosterData("绝命毒师", "共5季·2008~2013", "8.9", listOf("4K"), ""),
            PosterData("夜魔侠：重生", "共1季·2025", "8.6", listOf("4K"), "")
        )
    }
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        MediaLibGallery(
            movies = sampleMovies,
            title = "美剧"
        )
    }
}