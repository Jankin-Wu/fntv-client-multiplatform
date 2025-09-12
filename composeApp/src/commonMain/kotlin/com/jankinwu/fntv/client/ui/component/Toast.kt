package com.jankinwu.fntv.client.ui.component

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.composefluent.FluentTheme
import kotlinx.coroutines.delay

data class ToastMessage(
    val id: String = java.util.UUID.randomUUID().toString(), // 唯一标识符
    val message: String, // 提示文字
    val icon: ImageVector? = null, // 图标
    val duration: Long = 2000L // 显示时长（毫秒）
)

@Composable
fun Toast(
    message: String,
    icon: ImageVector? = null,
    duration: Long = 2000L,
    onDismiss: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // 淡入动画
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 300)
        )

        // 等待指定时长
        delay(duration)

        // 淡出动画
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 300)
        )

        // 动画结束后通知消失
        isVisible = false
        onDismiss()
    }

    if (isVisible) {
        Box(
            modifier = Modifier
//                .width(32.dp)
//                .padding(horizontal = 32.dp)
                .background(
                    color = FluentTheme.colors.background.card.tertiary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                Text(
                    text = message,
                    style = TextStyle(
                        color = FluentTheme.colors.text.text.primary,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}