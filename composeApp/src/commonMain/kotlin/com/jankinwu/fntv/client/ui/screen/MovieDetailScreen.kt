package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.PlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jankinwu.fntv.client.LocalStore
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.model.response.ItemResponse
import com.jankinwu.fntv.client.data.store.AccountDataCache
import com.jankinwu.fntv.client.icons.ArrowLeft
import com.jankinwu.fntv.client.icons.ArrowUp
import com.jankinwu.fntv.client.icons.HeartFilled
import com.jankinwu.fntv.client.ui.component.CastScrollRow
import com.jankinwu.fntv.client.ui.component.ComponentNavigator
import com.jankinwu.fntv.client.ui.component.ImgLoadingError
import com.jankinwu.fntv.client.ui.component.ImgLoadingProgressRing
import com.jankinwu.fntv.client.viewmodel.GenresViewModel
import com.jankinwu.fntv.client.viewmodel.ItemViewModel
import com.jankinwu.fntv.client.viewmodel.TagViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.ScrollbarContainer
import io.github.composefluent.component.rememberScrollbarAdapter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailScreen(
    guid: String,
    navigator: ComponentNavigator
) {
    val itemViewModel: ItemViewModel = koinViewModel()
    val itemUiState by itemViewModel.uiState.collectAsState()
    var itemData: ItemResponse? by remember { mutableStateOf(null) }
    val store = LocalStore.current
    val windowHeight = store.windowHeightState
    LaunchedEffect(Unit) {
        itemViewModel.loadData(guid)
    }
    LaunchedEffect(itemUiState) {
        when (itemUiState) {
            is UiState.Success -> {
                itemData = (itemUiState as UiState.Success<ItemResponse>).data
            }

            is UiState.Error -> {
                println("message: ${(itemUiState as UiState.Error).message}")
            }

            else -> {}
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()

//            .background(Color(0xFF2D2D2D))
    ) {
        val lazyListState = rememberLazyListState()
        ScrollbarContainer(
            adapter = rememberScrollbarAdapter(lazyListState)
        ) {
            LazyColumn(
                state = lazyListState,
            ) {
                item {
                    if (itemData != null) {
                        Box(
                            modifier = Modifier
                                .height((windowHeight / 2.dp).dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(PlatformContext.INSTANCE)
                                    .data("${AccountDataCache.getFnOfficialBaseUrl()}/v/api/v1/sys/img${itemData?.backdrops}")
                                    .httpHeaders(store.fnImgHeaders)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = itemData?.title,
                                modifier = Modifier
                                    .height((windowHeight / 2.dp).dp)
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop,
                                loading = {
                                    ImgLoadingProgressRing()
                                },
                                error = {
                                    ImgLoadingError()
                                },
                            )
                            // 渐变遮罩层
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color(0xFF2D2D2D)
                                            ),
                                            startY = (windowHeight / 4.dp).dp.value, // 开始渐变的位置
                                            endY = (windowHeight / 2.dp).dp.value    // 结束渐变的位置
                                        )
                                    )
                            )
                            if (itemData?.logos != null) {
                                var imageHeight by remember { mutableStateOf(90.dp) }
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(PlatformContext.INSTANCE)
                                        .data("${AccountDataCache.getFnOfficialBaseUrl()}/v/api/v1/sys/img${itemData?.logos}")
                                        .httpHeaders(store.fnImgHeaders)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = itemData?.title,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .height(imageHeight)
                                        .padding(start = 48.dp, bottom = 12.dp),
                                    contentScale = ContentScale.Fit,
                                    loading = {
                                        ImgLoadingProgressRing()
                                    },
                                    error = {
                                        ImgLoadingError()
                                    },
                                    onSuccess = { state ->
                                        // 获取图片尺寸并判断是否需要调整高度
                                        state.result.image.let { drawable ->
                                            imageHeight = 90.dp
                                            val width = drawable.width
                                            val height = drawable.height
                                            println("width: $width, height: $height")
                                            val actualWidth = width / height * 90
                                            if (actualWidth > 0 && actualWidth < 300) {
                                                imageHeight = 150.dp
                                            }
                                        }
                                    }
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
//                                        .width(200.dp)
                                        .padding(start = 48.dp, bottom = 12.dp)
                                ) {
                                    itemData?.title?.let {
                                        Text(
                                            text = it,
                                            style = LocalTypography.current.title,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White,
                                            fontSize = 60.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item { MediaInfo(itemData) }
                item {
                    CastScrollRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        guid
                    )
                }
            }
        }
        // 返回按钮
        IconButton(
            onClick = { navigator.navigateUp() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .pointerHoverIcon(PointerIcon.Hand)
        ) {
            Icon(
                imageVector = ArrowLeft,
                contentDescription = "返回",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// --- 界面主容器 ---

@Composable
fun MediaInfo(itemData: ItemResponse?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 24.dp)
    ) {
        // 进度条
        TopInfoBar(modifier = Modifier.padding(bottom = 8.dp))

        MiddleControls(modifier = Modifier.padding(bottom = 16.dp), itemData)

        MediaSource(modifier = Modifier.padding(bottom = 16.dp))

        MediaDescription(modifier = Modifier.padding(bottom = 32.dp), itemData)
    }
}

@Composable
fun MediaSource(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        QualityTag(text = "4K HLG", active = true, onClick = {}, isSelected = true)
        QualityTag(text = "4K 杜比视界", active = false, onClick = {}, isSelected = false)
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TopInfoBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinearProgressIndicator(
            progress = {
                0.1f // 示例进度 10%
            },
            modifier = Modifier.width(300.dp),
            color = Color(0xFF2173DF), // 蓝色
            trackColor = Color.DarkGray.copy(alpha = 0.4f),
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "剩余 3小时14分钟",
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun MiddleControls(modifier: Modifier = Modifier, itemData: ItemResponse?) {
    val tagViewModel: TagViewModel = koinViewModel<TagViewModel>()
    val iso3166State = tagViewModel.iso3166State.collectAsState().value

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 第一行：播放、收藏、更多
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 播放按钮
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2173DF)), // 蓝色背景
                shape = CircleShape, // 圆角
                modifier = Modifier.height(56.dp).width(160.dp)
            ) {
                Text("▶  播放", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            // 收藏按钮
            CircleIconButton(icon = HeartFilled, description = "收藏")

            CircleIconButton(icon = Icons.Default.Check, description = "已观看")

            // 更多按钮
            CircleIconButton(icon = Icons.Default.MoreHoriz, description = "更多")
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            // 右侧：评分、标签、Logo
            // 使用 FlowRow 可以在空间不足时自动换行
            FlowRow(
                modifier = Modifier, // 占据右侧约 60% 宽度
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.End
                ),
                verticalArrangement = Arrangement.Center
            ) {
                val voteAverage = itemData?.voteAverage?.toDoubleOrNull()?.let { 
                    "%.1f".format(it) 
                } ?: ""
                if (voteAverage.isNotEmpty()) {
                    Text(
                        "$voteAverage 分",
                        color = Color(0xFFFACC15),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
//                        modifier = Modifier.offset(y = (-3).dp)
                    )
                    Separator()
                }
                val contentRatings = itemData?.contentRatings?: ""
                if (contentRatings.isNotEmpty()) {
                    Text(
                        contentRatings,
                        color = FluentTheme.colors.text.text.secondary,
                        fontSize = 14.sp
                    )
                    Separator()
                }
                val year = itemData?.airDate?.take(4) ?: ""
                if (year.isNotEmpty()) {
                    Text(
                        year,
                        color = FluentTheme.colors.text.text.secondary,
                        fontSize = 14.sp
                    )
                    Separator()
                }
                val genresViewModel: GenresViewModel = koinViewModel<GenresViewModel>()
                val genresUiState = genresViewModel.uiState.collectAsState().value
                if (genresUiState is UiState.Success) {
                    val genresMap = genresUiState.data.associateBy { it.id }
                    val genresText = itemData?.genres?.joinToString(" ") { genreId ->
                        genresMap[genreId]?.value ?: ""
                    }
                    if (!genresText.isNullOrBlank()) {
                        Text(genresText, color = FluentTheme.colors.text.text.secondary, fontSize = 14.sp)
                    }
                    Separator()
                }
                LaunchedEffect(Unit) {
                    tagViewModel.loadIso3166Tags()
                }
                if (iso3166State is UiState.Success) {
                    val iso3166Map = iso3166State.data.associateBy { it.key }
                    val countriesText = itemData?.productionCountries?.joinToString(" ") { locate ->
                        iso3166Map[locate]?.value ?: locate
                    }
                    if (!countriesText.isNullOrBlank()) {
                        Text(countriesText, color = FluentTheme.colors.text.text.secondary, fontSize = 14.sp)
                    }
                    Separator()
                }
                Text(
                    "2 小时 9 分钟",
                    color = FluentTheme.colors.text.text.secondary,
                    fontSize = 14.sp
                )
                Separator()
                Text(itemData?.ancestorName ?:"", color = FluentTheme.colors.text.text.secondary, fontSize = 14.sp)

                // 图标和文本标签
//                InfoIconText(Icons.Default.ClosedCaption, "中文字幕")
//                InfoIconText(Icons.Default.Web, "网页全屏")

            }
            // 第二行：4K 标签
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoIconText("中文字幕")

                LogoPlaceholder("4k")
                LogoPlaceholder("HDR")
            }
        }
    }
}

@Composable
fun Separator() {
    Text("/", color = FluentTheme.colors.text.text.disabled.copy(alpha = 0.1f), fontSize = 16.sp, modifier = Modifier.offset(y = (-3).dp))
}

@Composable
fun MediaDescription(modifier: Modifier = Modifier, itemData: ItemResponse?) {
    val processedOverview = itemData?.overview?.replace("\n\n", "\n") ?: ""
    Text(
        text = processedOverview,
        style = LocalTypography.current.body,
        color = FluentTheme.colors.text.text.secondary,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        modifier = modifier.fillMaxWidth()
    )
}


// --- 可复用的子组件 ---

/**
 * 右上角带背景的灰色小标签，如 "2023"
 */
@Composable
fun InfoTag(text: String) {
    Surface(
        color = Color.DarkGray.copy(alpha = 0.5f),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

/**
 * 右上角带图标的文本，如 "中文字幕"
 */
@Composable
fun InfoIconText(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = text, color = Color.White, fontSize = 12.sp)
        Icon(
            imageVector = ArrowUp,
            contentDescription = text,
            tint = FluentTheme.colors.text.text.secondary,
            modifier = Modifier.size(14.dp)
        )
    }
}

/**
 * 右上角 Logo 的占位符
 */
@Composable
fun LogoPlaceholder(resolution: String) {
    if (resolution.endsWith("k")) {
        Box(
            modifier = Modifier
//                .alpha(if (isPosterHovered) 0f else 1f)
                //                                .align(Alignment.BottomEnd)
                .padding(2.dp)
                .background(
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(3.dp)
                )
                .padding(
                    horizontal = 6.dp,
                    vertical = 1.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = resolution.uppercase(),
                color = Color.Black.copy(alpha = 0.6f),
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    } else {
        Box(
            modifier = Modifier
//                .alpha(if (isPosterHovered) 0f else 1f)
                //                                .align(Alignment.BottomEnd)
                //                                .padding((8 * scaleFactor).dp)
                .border(
                    2.dp,
                    Color.White.copy(alpha = 0.6f),
                    RoundedCornerShape(3.dp)
                )
                .padding(
                    horizontal = 3.dp,
                    vertical = 1.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = resolution,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * 中间的圆形图标按钮
 */
@Composable
fun CircleIconButton(icon: ImageVector, description: String) {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(56.dp)
            .border(1.dp, Color.Gray.copy(alpha = 0.5f), CircleShape)
            .background(Color.Gray.copy(alpha = 0.1f), CircleShape)
    ) {
        Icon(icon, contentDescription = description, tint = Color.White, modifier = Modifier.size(25.dp))
    }
}

/**
 * 中间的 "4K" 质量标签
 */
@Composable
fun QualityTag(text: String, active: Boolean, onClick: () -> Unit, isSelected: Boolean) {
    val textColor = if (active) Color(0xFF2173DF) else Color.White // 激活时为蓝色
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                if (isSelected) 2.dp else 1.dp,
                if (isSelected) Color(0xFF2173DF) else Color.Gray.copy(alpha = 0.5f),
                RoundedCornerShape(8.dp)
            )
            .width(128.dp)
            .height(36.dp)
            .clickable(
                onClick = onClick,
            )
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}