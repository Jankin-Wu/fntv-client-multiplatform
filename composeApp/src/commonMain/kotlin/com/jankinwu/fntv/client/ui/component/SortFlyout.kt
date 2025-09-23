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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.icons.ArrowUp
import com.jankinwu.fntv.client.icons.TriangleDownFill
import com.jankinwu.fntv.client.icons.TriangleUpFill
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.FlyoutContainer
import io.github.composefluent.component.Icon
import io.github.composefluent.component.Text

@Composable
fun SortFlyout() {
    var isSelected by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("添加日期") }
    FlyoutContainer(
        flyout = {
            Column {


            }
        },
        content = {
            SortButton(
                isSelected = !isSelected,
                onClick = {
                    isFlyoutVisible = !isFlyoutVisible
                },
                buttonText = buttonText
            )
        }
    )
}

@Composable
fun SortButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val backgroundColor by animateColorAsState(
        targetValue = if (isHovered || isSelected) FluentTheme.colors.stroke.control.default else Color.Transparent
    )

    // 根据isSelected状态计算目标旋转角度
    val targetRotation = if (isSelected) -180f else 0f
    val animatedRotation by animateFloatAsState(targetValue = targetRotation)
    val isAsc by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .border(1.dp, Color.Gray.copy(alpha = 0.4f), CircleShape)
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .hoverable(interactionSource)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(4.dp, Alignment.CenterHorizontally)
    ) {
        Text(
            text = buttonText,
            color = FluentTheme.colors.text.text.primary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                TriangleUpFill,
                tint = if (isAsc) FluentTheme.colors.text.text.tertiary else FluentTheme.colors.text.text.disabled,
                contentDescription = "升序",
                modifier = Modifier
            )
            Icon(
                TriangleDownFill,
                tint = if (isAsc) FluentTheme.colors.text.text.tertiary else FluentTheme.colors.text.text.disabled,
                contentDescription = "升序",
                modifier = Modifier
            )
        }
        Icon(
            imageVector = ArrowUp,
            contentDescription = "下拉图标",
            tint = FluentTheme.colors.text.text.primary,
            modifier = Modifier
                .size(16.dp)
                .rotate(animatedRotation)
        )

    }

}