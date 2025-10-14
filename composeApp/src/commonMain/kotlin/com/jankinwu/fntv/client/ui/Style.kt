package com.jankinwu.fntv.client.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.CheckBoxColor
import io.github.composefluent.component.SwitcherStyle
import io.github.composefluent.scheme.PentaVisualScheme

@Stable
@Composable
fun selectedSwitcherStyle(
    default: SwitcherStyle = SwitcherStyle(
        fillColor = Color(0xFF3A7BFF),
        labelColor = FluentTheme.colors.text.text.primary,
        controlColor = FluentTheme.colors.text.onAccent.primary,
        controlSize = DpSize(width = 12.dp, height = 12.dp),
        borderBrush = SolidColor(Color.Transparent)
    ),
    hovered: SwitcherStyle = default.copy(
        fillColor = Color(0xFF3A7BFF),
        controlSize = DpSize(width = 14.dp, height = 14.dp)
    ),
    pressed: SwitcherStyle = default.copy(
        fillColor = Color(0xFF3A7BFF),
        controlSize = DpSize(width = 17.dp, height = 14.dp)
    ),
    disabled: SwitcherStyle = default.copy(
        fillColor = FluentTheme.colors.fillAccent.disabled,
        borderBrush = SolidColor(FluentTheme.colors.fillAccent.disabled),
        controlColor = FluentTheme.colors.text.onAccent.disabled,
        labelColor = FluentTheme.colors.text.text.disabled
    )
) = SwitcherStyleScheme(
    default = default,
    hovered = hovered,
    pressed = pressed,
    disabled = disabled
)

typealias SwitcherStyleScheme = PentaVisualScheme<SwitcherStyle>

@Stable
@Composable
fun selectedCheckBoxColors(
    default: CheckBoxColor = CheckBoxColor(
        fillColor = Color(0xFF3A7BFF),
        contentColor = Color.White,
        borderColor = Color.Transparent,
        labelTextColor = FluentTheme.colors.text.text.primary
    ),
    hovered: CheckBoxColor = default.copy(
        fillColor = Color(0xFF3A7BFF),
    ),
    pressed: CheckBoxColor = default.copy(
        fillColor = FluentTheme.colors.fillAccent.tertiary,
        contentColor = FluentTheme.colors.text.onAccent.secondary
    ),
    disabled: CheckBoxColor = CheckBoxColor(
        fillColor = FluentTheme.colors.fillAccent.disabled,
        contentColor = FluentTheme.colors.text.onAccent.disabled,
        borderColor = FluentTheme.colors.fillAccent.disabled,
        labelTextColor = FluentTheme.colors.text.text.primary
    )
) = CheckBoxColorScheme(
    default = default,
    hovered = hovered,
    pressed = pressed,
    disabled = disabled
)

typealias CheckBoxColorScheme = PentaVisualScheme<CheckBoxColor>