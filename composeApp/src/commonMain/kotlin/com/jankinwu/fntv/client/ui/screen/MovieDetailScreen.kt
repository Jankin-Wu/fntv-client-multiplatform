package com.jankinwu.fntv.client.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.jankinwu.fntv.client.data.model.response.ItemResponse
import com.jankinwu.fntv.client.data.store.AccountDataCache
import com.jankinwu.fntv.client.icons.ArrowLeft
import com.jankinwu.fntv.client.ui.component.CastScrollRow
import com.jankinwu.fntv.client.ui.component.ComponentNavigator
import com.jankinwu.fntv.client.ui.component.ImgLoadingError
import com.jankinwu.fntv.client.ui.component.ImgLoadingProgressRing
import com.jankinwu.fntv.client.viewmodel.ItemViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
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
                                .height((400 * store.scaleFactor).dp)
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
                                    .height((400 * store.scaleFactor).dp)
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
                                            startY = 150.dp.value, // 开始渐变的位置
                                            endY = 300.dp.value    // 结束渐变的位置
                                        )
                                    )
                            )
                        }
                    }
                }
                item { MediaBrief() }
                item {
                    CastScrollRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 64.dp),
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
fun MediaBrief() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 24.dp)
    ) {
        // 3. 顶部信息栏
        TopInfoBar(modifier = Modifier)

        // 4. 中间控制按钮
        MiddleControls(modifier = Modifier)

        MediaSource()

        BottomDisclaimer(modifier = Modifier)
    }
}

@Composable
fun MediaSource() {
    Row(
        modifier = Modifier
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        QualityTag(text = "4K HLG", active = true)
        QualityTag(text = "4K HLG", active = false)
    }
}

// --- 顶部信息栏 ---

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopInfoBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        LinearProgressIndicator(
            progress = {
                0.1f // 示例进度 10%
            },
            modifier = Modifier.fillMaxWidth(0.3f), // 进度条宽度
            color = Color(0xFF3B82F6), // 蓝色
            trackColor = Color.DarkGray.copy(alpha = 0.4f),
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "剩余 3小时14分钟",
            color = Color.LightGray,
            fontSize = 12.sp
        )
    }
}

// --- 中间控制按钮 ---

@Composable
fun MiddleControls(modifier: Modifier = Modifier) {
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
                colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF3B82F6)), // 蓝色背景
                shape = CircleShape, // 圆角
                modifier = Modifier.height(56.dp).width(160.dp)
            ) {
                Text("▶ 播放", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            // 收藏按钮
            CircleIconButton(icon = Icons.Default.Check, description = "收藏")

            // 更多按钮
            CircleIconButton(icon = Icons.Default.MoreHoriz, description = "更多")
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            // 右侧：评分、标签、Logo
            // 使用 FlowRow 可以在空间不足时自动换行
            FlowRow(
                modifier = Modifier.fillMaxWidth(0.6f), // 占据右侧约 60% 宽度
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp,
                    Alignment.End
                ), // 元素间距 16dp 并右对齐
                verticalArrangement = Arrangement.spacedBy(8.dp) // 行间距
            ) {
                Text(
                    "9.0 分",
                    color = Color(0xFFFACC15),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                InfoTag("2023")
                Text("剧情 / 历史 / 传记", color = Color.White, fontSize = 12.sp)
                Text("美国", color = Color.White, fontSize = 12.sp)
                Text("3小时", color = Color.White, fontSize = 12.sp)

                // 图标和文本标签
//                InfoIconText(Icons.Default.ClosedCaption, "中文字幕")
//                InfoIconText(Icons.Default.Web, "网页全屏")

                // Logo 占位符
//            LogoPlaceholder("Dolby Vision")
//            LogoPlaceholder("Dolby Atmos")
//            LogoPlaceholder("HDR")
            }
            // 第二行：4K 标签
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoIconText(Icons.Default.ClosedCaption, "中文字幕")

                LogoPlaceholder("Dolby Vision")
                LogoPlaceholder("Dolby Atmos")
//            QualityTag(text = "4K 超清", active = true)
//
            }
        }
    }
}

// --- 底部免责声明 ---

@Composable
fun BottomDisclaimer(modifier: Modifier = Modifier) {
    val disclaimerText =
        "当您深度参与内容创作和消费时，为保证这些信息能被有效传达，我们将对您的昵称和言论等信息进行展示。您理解并同意，在弹幕、评论及其他公开发言场景下，您的昵称、头像和言论内容将向公众展示。本片由41位画师历时2年绘制超百张巨幅油画，这可能是B站目前最“贵”的视频之一。艺术家们用油画再现了梵高的一生，这种表现形式本身就充满了艺术感和致敬的意味。......"
    Text(
        text = disclaimerText,
        color = Color.Gray,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        modifier = modifier.fillMaxWidth(0.85f) // 限制最大宽度
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
fun InfoIconText(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.Gray,
            modifier = Modifier.size(14.dp)
        )
        Text(text = text, color = Color.White, fontSize = 12.sp)
    }
}

/**
 * 右上角 Logo 的占位符
 */
@Composable
fun LogoPlaceholder(text: String) {
    Text(
        text = text,
        color = Color.Gray,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(2.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}

/**
 * 中间的圆形图标按钮
 */
@Composable
fun CircleIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, description: String) {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(56.dp)
            .background(Color.White.copy(alpha = 0.1f), CircleShape) // 半透明灰色背景
    ) {
        Icon(icon, contentDescription = description, tint = Color.White)
    }
}

/**
 * 中间的 "4K" 质量标签
 */
@Composable
fun QualityTag(text: String, active: Boolean) {
    val textColor = if (active) Color(0xFF3B82F6) else Color.White // 激活时为蓝色
    Surface(
        color = Color.White.copy(alpha = 0.1f), // 统一的半透明灰色背景
        shape = RoundedCornerShape(4.dp)
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