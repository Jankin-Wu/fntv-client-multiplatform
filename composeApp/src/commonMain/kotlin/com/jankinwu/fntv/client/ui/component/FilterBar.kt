package com.jankinwu.fntv.client.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.icons.ArrowUp
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.Icon
import io.github.composefluent.component.Text

@Composable
fun FilterButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) FluentTheme.colors.background.card.tertiary.copy(alpha = 0.2f) else Color.Transparent
    )

    // 根据isSelected状态计算目标旋转角度
    val targetRotation = if (isSelected) -180f else 0f
    val animatedRotation by animateFloatAsState(targetValue = targetRotation)

    // 使用 Row 布局来水平排列文本和图标
    Row(
        modifier = Modifier
            // (a) 设置圆角矩形裁剪，使其成为一个胶囊形状
            .clip(CircleShape)
            .border(1.dp, Color.Gray.copy(alpha = 0.4f), CircleShape)
            // (b) 应用我们上面定义的动画背景色
            .background(backgroundColor)
            // (c) 添加点击事件，点击时翻转 isSelected 的状态
            .clickable(onClick = onClick)
            // (d) 添加内边距，让内容和边框之间有一些空间
            .padding(horizontal = 12.dp, vertical = 6.dp),
        // 垂直居中对齐 Row 里面的所有内容
        verticalAlignment = Alignment.CenterVertically,
        // 水平居中对齐
        horizontalArrangement = Arrangement.Center
    ) {
        // 显示文本
        Text(
            text = "筛选",
            color = FluentTheme.colors.text.text.primary,
            fontSize = 16.sp
        )
        // 在文本和图标之间添加一点点间距
        Spacer(modifier = Modifier.width(4.dp))
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
    }
}