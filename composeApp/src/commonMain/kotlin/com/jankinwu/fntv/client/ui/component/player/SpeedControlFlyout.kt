package com.jankinwu.fntv.client.ui.component.player

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.jankinwu.fntv.client.LocalTypography
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val FlyoutBackgroundColor = Color.Black.copy(alpha = 0.9f)
private val FlyoutBorderColor = Color.White
private val SelectedTextColor = Color(0xFF2073DF)
private val DefaultTextColor = Color.White.copy(alpha = 0.7843f)
private val HoverBackgroundColor = Color.White.copy(alpha = 0.1f)
private val FlyoutShape = RoundedCornerShape(8.dp)
private const val HIDE_DELAY_MS = 200L // 增加延迟时间以减少闪烁

data class SpeedItem(
    val label: String,
    val value: Float
)

val speeds = listOf(
    SpeedItem("2.0x", 2.0f),
    SpeedItem("1.75x", 1.175f),
    SpeedItem("1.5x", 1.5f),
    SpeedItem("1.25x", 1.25f),
    SpeedItem("1.0x", 1.0f),
    SpeedItem("0.75x", 0.75f),
    SpeedItem("0.5x", 0.5f)
)



/**
 * 倍速调节 Flyout 组件
 *
 * @param modifier The modifier to be applied to the component.
 * @param speeds 可用的倍速选项列表.
 * @param defaultSpeed 默认选中的倍速.
 * @param onSpeedSelected 当选择新的倍速时触发的回调.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpeedControlFlyout(
    modifier: Modifier = Modifier,
    defaultSpeed: SpeedItem = speeds[4],
    yOffset: Int = 0,
    onHoverStateChanged: ((Boolean) -> Unit)? = null,
    onSpeedSelected: (SpeedItem) -> Unit = {}
) {
    var selectedSpeed by remember { mutableStateOf(defaultSpeed) }
    var isExpanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var hideJob by remember { mutableStateOf<Job?>(null) }
    var isButtonHovered by remember { mutableStateOf(false) }
    var popupHovered by remember { mutableStateOf(false) }

    // 函数：取消隐藏任务并显示选择框
    fun showFlyout() {
        hideJob?.cancel()
        isExpanded = true
        onHoverStateChanged?.invoke(true)
    }

    // 函数：启动一个带延迟的隐藏任务
    fun hideFlyoutWithDelay() {
        hideJob = coroutineScope.launch {
            delay(HIDE_DELAY_MS)
            // 只有当按钮和弹出框都不处于悬停状态时才隐藏
            if (!isButtonHovered && !popupHovered) {
                isExpanded = false
                onHoverStateChanged?.invoke(false)
            }
        }
    }

    Box(
        modifier = modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .onPointerEvent(PointerEventType.Enter) { 
                isButtonHovered = true
                showFlyout()
            }
            .onPointerEvent(PointerEventType.Exit) { 
                isButtonHovered = false
                hideFlyoutWithDelay()
            },
        contentAlignment = Alignment.Center
    ) {
        // 如果展开，则显示 Popup
        if (isExpanded) {
            // 使用 Popup 实现悬浮
            Popup(
                offset = IntOffset(0, -yOffset),
                alignment = Alignment.BottomCenter,
                properties = PopupProperties(
                    clippingEnabled = false,
                    focusable = false
                ),
                onDismissRequest = { 
                    // 只有当鼠标不在按钮或弹出框上时才关闭
                    if (!isButtonHovered && !popupHovered) {
                        isExpanded = false
                    }
                }
            ) {
                // 这个Box用于捕获鼠标事件，以防止在移动到选择框上时其消失
                Box(
                    modifier = Modifier
                        .pointerHoverIcon(PointerIcon.Hand)
                        .onPointerEvent(PointerEventType.Enter) { 
                            popupHovered = true
                            hideJob?.cancel()
                        }
                        .onPointerEvent(PointerEventType.Exit) { 
                            popupHovered = false
                            hideFlyoutWithDelay()
                        }

                ) {
                    FlyoutContent(
                        speeds = speeds,
                        selectedSpeed = selectedSpeed,
                        onSpeedClick = { speed ->
                            selectedSpeed = speed
                            isExpanded = false // 点击后立即隐藏
                            onSpeedSelected(speed)
                        }
                    )
                }
            }
        }
        Text(
            text = if (selectedSpeed.label == "1.0x") "倍速" else selectedSpeed.label,
            style = LocalTypography.current.title,
            color = if (isButtonHovered) Color.White else DefaultTextColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FlyoutContent(
    speeds: List<SpeedItem>,
    selectedSpeed: SpeedItem,
    onSpeedClick: (SpeedItem) -> Unit
) {
    Surface(
        shape = FlyoutShape,
        color = FlyoutBackgroundColor,
        border = BorderStroke(1.dp, FlyoutBorderColor),
    ) {
        Column(
            modifier = Modifier
                .width(120.dp)
                .padding(vertical = 10.dp)
        ) {
            speeds.forEach { speed ->
                FlyoutItem(
                    speed = speed,
                    isSelected = speed == selectedSpeed,
                    onClick = { onSpeedClick(speed) }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun FlyoutItem(
    speed: SpeedItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (isHovered) HoverBackgroundColor else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = speed.label,
            color = if (isSelected) SelectedTextColor else DefaultTextColor,
            fontSize = 14.sp
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = DefaultTextColor,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}