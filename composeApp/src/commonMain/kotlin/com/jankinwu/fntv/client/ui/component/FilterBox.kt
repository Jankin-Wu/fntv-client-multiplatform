package com.jankinwu.fntv.client.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.data.model.response.GenresResponse
import com.jankinwu.fntv.client.data.model.response.QueryTagResponse
import com.jankinwu.fntv.client.data.model.response.TagListResponse
import com.jankinwu.fntv.client.icons.ArrowUp
import com.jankinwu.fntv.client.icons.DoubleArrowUp
import com.jankinwu.fntv.client.icons.RefreshCircle
import com.jankinwu.fntv.client.viewmodel.UiState
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.Icon
import io.github.composefluent.component.ScrollbarContainer
import io.github.composefluent.component.Text
import io.github.composefluent.component.rememberScrollbarAdapter
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.Dismiss

val SelectedColor = Color(0xFF2073DF)

data class FilterGroup(
    val title: String,
    val options: List<FilterItem>
)

data class FilterItem(
    val label: String,
    val value: Any?
)

@Composable
fun FilterButton(
    isSelected: Boolean,
    selectedFilters: Map<String, FilterItem>,
    onFilterClear: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val backgroundColor by animateColorAsState(
        targetValue = if (isHovered || isSelected) FluentTheme.colors.background.card.default else Color.Transparent
    )

    // 根据isSelected状态计算目标旋转角度
    val targetRotation = if (isSelected) -180f else 0f
    val animatedRotation by animateFloatAsState(targetValue = targetRotation)
    // 使用 Row 布局来水平排列文本和图标
    Row(
        modifier = modifier
            // (a) 设置圆角矩形裁剪，使其成为一个胶囊形状
            .clip(CircleShape)
            .border(1.dp, Color.Gray.copy(alpha = 0.4f), CircleShape)
            // (b) 应用我们上面定义的动画背景色
            .background(backgroundColor)
            // (c) 添加点击事件，点击时翻转 isSelected 的状态
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .hoverable(interactionSource)
            // (d) 添加内边距，让内容和边框之间有一些空间
            .padding(horizontal = 12.dp, vertical = 6.dp),
        // 垂直居中对齐 Row 里面的所有内容
        verticalAlignment = Alignment.CenterVertically,
        // 水平居中对齐
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
    ) {
        // 显示文本
        Text(
            text = "筛选",
            color = FluentTheme.colors.text.text.primary,
            fontSize = 16.sp
        )
        // 显示图标
        Icon(
            imageVector = ArrowUp,
            contentDescription = "Filter Arrow",
            tint = FluentTheme.colors.text.text.primary,
            modifier = Modifier
                .size(16.dp)
                // (e) 应用我们上面定义的旋转动画
                .rotate(animatedRotation)
        )
        // 当筛选框收起且有选中项时显示胶囊
        if (!isSelected && selectedFilters.isNotEmpty()) {
            val nonDefaultFilters = selectedFilters.filter { entry ->
                entry.value.label != "全部" && entry.value.value != null
            }

            if (nonDefaultFilters.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    nonDefaultFilters.forEach { (title, filterItem) ->
                        FilterChip(
                            label = filterItem.label,
                            icon = Icons.Regular.Dismiss,
                            onClear = { onFilterClear(title) }
                        )
                    }
                    FilterChip(
                        label = "重置",
                        icon = RefreshCircle,
                        onClear = { onFilterClear("all") }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    icon: ImageVector,
    onClear: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(FluentTheme.colors.background.card.tertiary)
            .border(1.dp, Color.Gray.copy(alpha = 0.4f), CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClear
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            color = FluentTheme.colors.text.text.primary,
            fontSize = 12.sp,
            modifier = Modifier.padding(end = 4.dp)
        )
        Icon(
            imageVector = icon,
            contentDescription = "Clear",
            tint = FluentTheme.colors.text.text.tertiary,
            modifier = Modifier
                .size(12.dp)
        )
    }
}

/**
 * 2. 核心UI组件：完整的筛选界面
 */
@Composable
fun FilterBox(
    modifier: Modifier,
    tagListUiState: UiState<TagListResponse>,
    genresUiState: UiState<List<GenresResponse>>,
    iso3166State: UiState<List<QueryTagResponse>>,
    initialSelectedFilters: Map<String, FilterItem> = emptyMap(),
    onFilterChanged: (Map<String, FilterItem>) -> Unit = {},
    onFilterBoxCollapse: (() -> Unit)? = null
) {

    // 构建动态筛选数据
    val dynamicFilterData by remember(tagListUiState, genresUiState, iso3166State) {
        mutableStateOf(buildFilterData(tagListUiState, genresUiState, iso3166State))
    }

    // 使用 mutableStateMap 来存储每一行(由title唯一标识)的选中项
    // 初始时，使用传入的初始选中项或者默认每一行的第一个选项("全部")被选中
    val selectedOptions = remember(dynamicFilterData, initialSelectedFilters) {
        mutableStateMapOf<String, FilterItem>().apply {
            dynamicFilterData.forEach { group ->
                // 优先使用传入的初始选中项，如果没有则使用默认的第一个选项
                this[group.title] = initialSelectedFilters[group.title] ?: group.options.first()
            }
        }
    }

    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .background(FluentTheme.colors.background.card.default, RoundedCornerShape(8.dp))
    ) {
        ScrollbarContainer(
            adapter = rememberScrollbarAdapter(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                // 遍历所有筛选组数据，为每一组创建一个FilterRow
                dynamicFilterData.forEach { filterGroup ->
                    FilterRow(
                        title = filterGroup.title,
                        options = filterGroup.options,
                        selectedOption = selectedOptions[filterGroup.title]
                            ?: filterGroup.options.first(),
                        onOptionSelected = { newSelection ->
                            selectedOptions[filterGroup.title] = newSelection
                            onFilterChanged(selectedOptions.toMap())
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // 行间距
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    val isHovered by interactionSource.collectIsHoveredAsState()
                    Row(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null, // 移除点击波纹效果
                                onClick = { onFilterBoxCollapse?.invoke() }
                            )
                            .hoverable(interactionSource)
                            .pointerHoverIcon(PointerIcon.Hand),
                        horizontalArrangement = spacedBy(4.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "收起",
                            color = when {
                                isHovered -> FluentTheme.colors.text.text.primary
                                else -> FluentTheme.colors.text.text.tertiary
                            },
                            fontSize = 15.sp,
                            modifier = Modifier

                        )
                        Icon(
                            imageVector = DoubleArrowUp,
                            contentDescription = "收起",
                            tint = if (isHovered) FluentTheme.colors.text.text.primary else FluentTheme.colors.text.text.tertiary,
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * 3. 每一行的UI组件 (例如 "类型: 全部 剧情 喜剧 ...")
 *
 * @param title 行标题, e.g., "类型"
 * @param options 该行所有可选的字符串列表
 * @param selectedOption 当前选中的选项
 * @param onOptionSelected 当有新选项被点击时触发的回调
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterRow(
    title: String,
    options: List<FilterItem>,
    selectedOption: FilterItem,
    onOptionSelected: (FilterItem) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top // 顶部对齐，以防选项换行后标题位置看起来奇怪
    ) {
        // 左侧的标题
        Text(
            text = title,
            color = FluentTheme.colors.text.text.tertiary,
            fontSize = 14.sp,
            modifier = Modifier
                .width(90.dp)
                .padding(top = 4.dp) // 微调标题位置
        )
        Spacer(modifier = Modifier.width(16.dp))

        // 右侧的可选项目区域
        // 使用FlowRow可以在空间不足时自动换行
        FlowRow(
            modifier = Modifier.weight(1.0f),
            horizontalArrangement = Arrangement.spacedBy(24.dp), // 选项之间的水平间距
            verticalArrangement = Arrangement.spacedBy(8.dp) // 换行后选项之间的垂直间距
        ) {
            options.forEach { option ->
                val isSelected = (option == selectedOption)
                FilterOption(
                    text = option.label,
                    isSelected = isSelected,
                    onClick = {
                        onOptionSelected(option)
                    }
                )
            }
        }
    }
}

/**
 * 4. 单个可点击选项的UI组件
 *
 * @param text 选项显示的文字
 * @param isSelected 是否被选中
 * @param onClick 点击事件
 */
@Composable
fun FilterOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    Text(
        text = text,
        color = when {
            isSelected -> SelectedColor
            isHovered -> FluentTheme.colors.text.text.primary
            else -> FluentTheme.colors.text.text.tertiary
        },
        fontSize = 14.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null, // 移除点击波纹效果
                onClick = onClick
            )
            .hoverable(interactionSource)
            .pointerHoverIcon(PointerIcon.Hand)
    )
}

/**
 * 构建筛选数据
 */
private fun buildFilterData(
    tagListUiState: UiState<TagListResponse>,
    genresUiState: UiState<List<GenresResponse>>,
    iso3166State: UiState<List<QueryTagResponse>>
): List<FilterGroup> {
    val filterGroups = mutableListOf<FilterGroup>()

    // 固定的筛选组
    filterGroups.add(
        FilterGroup(
            title = "影视类型",
            options = listOf(
                FilterItem("全部", null),
                FilterItem("电影", "Movie"),
                FilterItem("电视剧", "TV")
            )
        )
    )

    // 类型 (genres)
    if (tagListUiState is UiState.Success && genresUiState is UiState.Success) {
        val genreOptions = mutableListOf(FilterItem("全部", null))
        val genreMap = genresUiState.data.associateBy { it.id }

        tagListUiState.data.genres.forEach { genreId ->
            genreMap[genreId]?.let { genre ->
                genreOptions.add(FilterItem(genre.value, genreId))
            }
        }

        filterGroups.add(
            FilterGroup(
                title = "类型",
                options = genreOptions
            )
        )
    } else {
        filterGroups.add(
            FilterGroup(
                title = "类型",
                options = listOf(FilterItem("全部", null))
            )
        )
    }

    // 分辨率
    if (tagListUiState is UiState.Success) {
        val resolutionOptions = mutableListOf(FilterItem("全部", null))
        tagListUiState.data.resolutions.forEach { resolution ->
            resolutionOptions.add(FilterItem(resolution, resolution))
        }

        filterGroups.add(
            FilterGroup(
                title = "分辨率",
                options = resolutionOptions
            )
        )
    } else {
        filterGroups.add(
            FilterGroup(
                title = "分辨率",
                options = listOf(FilterItem("全部", null))
            )
        )
    }

    // 视频动态范围
    if (tagListUiState is UiState.Success) {
        val colorRangeOptions = mutableListOf(FilterItem("全部", null))
        tagListUiState.data.colorRange.forEach { colorRange ->
            val label = when (colorRange) {
                "DolbyVision" -> "杜比视界"
                else -> colorRange
            }
            colorRangeOptions.add(FilterItem(label, colorRange))
        }

        filterGroups.add(
            FilterGroup(
                title = "视频动态范围",
                options = colorRangeOptions
            )
        )
    } else {
        filterGroups.add(
            FilterGroup(
                title = "视频动态范围",
                options = listOf(FilterItem("全部", null))
            )
        )
    }

    // 音频规格
    if (tagListUiState is UiState.Success) {
        val audioTypeOptions = mutableListOf(FilterItem("全部", null))
        tagListUiState.data.audioType.forEach { audioType ->
            val label = when (audioType) {
                "DolbySurround" -> "杜比环绕"
                "DolbyAtmos" -> "杜比全景声"
                "Stereo" -> "立体声"
                "Others" -> "其他"
                else -> audioType
            }
            audioTypeOptions.add(FilterItem(label, audioType))
        }

        filterGroups.add(
            FilterGroup(
                title = "音频规格",
                options = audioTypeOptions
            )
        )
    } else {
        filterGroups.add(
            FilterGroup(
                title = "音频规格",
                options = listOf(FilterItem("全部", null))
            )
        )
    }

    // 国家和地区
    if (tagListUiState is UiState.Success && iso3166State is UiState.Success) {
        val locateOptions = mutableListOf(FilterItem("全部", null))
        val iso3166Map = iso3166State.data.associateBy { it.key }

        tagListUiState.data.locate.forEach { locate ->
            val label = iso3166Map[locate]?.value ?: locate
            locateOptions.add(FilterItem(label, locate))
        }

        filterGroups.add(
            FilterGroup(
                title = "国家和地区",
                options = locateOptions
            )
        )
    } else {
        filterGroups.add(
            FilterGroup(
                title = "国家和地区",
                options = listOf(FilterItem("全部", null))
            )
        )
    }

    // 发行年份
    if (tagListUiState is UiState.Success) {
        val decadesOptions = mutableListOf(FilterItem("全部", null))
        tagListUiState.data.decades.forEach { decade ->
            val label = when {
                decade == "Recent" -> "今年"
                decade == "Others" -> "其他"
                decade.endsWith("s") -> decade.dropLast(1) + "年代"
                else -> decade
            }
            decadesOptions.add(FilterItem(label, decade))
        }

        filterGroups.add(
            FilterGroup(
                title = "发行年份",
                options = decadesOptions
            )
        )
    } else {
        filterGroups.add(
            FilterGroup(
                title = "发行年份",
                options = listOf(FilterItem("全部", null))
            )
        )
    }

    // 匹配状态
    if (tagListUiState is UiState.Success) {
        val recognitionStatusOptions = mutableListOf(FilterItem("全部", null))
        tagListUiState.data.recognitionStatus.forEach { status ->
            val label = when (status) {
                1 -> "未匹配"
                2 -> "已匹配"
                3 -> "NFO匹配"
                else -> status.toString()
            }
            recognitionStatusOptions.add(FilterItem(label, status))
        }
        filterGroups.add(
            FilterGroup(
                title = "匹配状态",
                options = recognitionStatusOptions
            )
        )
    } else {
        filterGroups.add(
            FilterGroup(
                title = "匹配状态",
                options = listOf(FilterItem("全部", null))
            )
        )
    }


    // 是否已观看
    filterGroups.add(
        FilterGroup(
            title = "是否已观看",
            options = listOf(
                FilterItem("全部", null),
                FilterItem("已观看", "1"),
                FilterItem("未观看", "0")
            )
        )
    )

    return filterGroups
}