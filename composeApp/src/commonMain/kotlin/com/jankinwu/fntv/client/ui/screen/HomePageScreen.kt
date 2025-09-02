package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jankinwu.fntv.client.data.model.PosterData
import com.jankinwu.fntv.client.ui.component.MediaLibGallery
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.Scrollbar
import io.github.composefluent.component.ScrollbarContainer
import io.github.composefluent.component.Text
import io.github.composefluent.component.rememberScrollbarAdapter
import io.github.composefluent.gallery.screen.settings.alignHorizontalSpace

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
    val scrollState = rememberScrollState()
    Column(horizontalAlignment = Alignment.Start,) {
        Text(
            text = "首页",
            style = FluentTheme.typography.subtitle,
            color = FluentTheme.colors.text.text.tertiary,
            modifier = Modifier
//                .alignHorizontalSpace()
                .padding(top = 36.dp, bottom = 36.dp, start = 32.dp)
        )
        ScrollbarContainer(
            adapter = rememberScrollbarAdapter(scrollState)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
            ) {
                MediaLibGallery(
                    movies = sampleMovies,
                    title = "美剧"
                )
                MediaLibGallery(
                    movies = sampleMovies,
                    title = "电影"
                )
                MediaLibGallery(
                    movies = sampleMovies,
                    title = "动漫"
                )
                MediaLibGallery(
                    movies = sampleMovies,
                    title = "韩剧"
                )
            }
//        Scrollbar(
//            modifier = Modifier
//                .fillMaxHeight()
//                .width(15.dp),
//            isVertical = true,
//            adapter = rememberScrollbarAdapter(
//                scrollState
//            )
//        )
        }
    }

}
