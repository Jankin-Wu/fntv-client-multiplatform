package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Forward10S: ImageVector
    get() {
        if (_Forward10S != null) {
            return _Forward10S!!
        }
        _Forward10S = ImageVector.Builder(
            name = "Forward10S",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            group(
                clipPathData = PathData {
                    moveTo(0f, 0f)
                    horizontalLineToRelative(24f)
                    verticalLineToRelative(24f)
                    horizontalLineToRelative(-24f)
                    close()
                }
            ) {
                path(fill = SolidColor(Color.Black)) {
                    moveTo(12f, 2f)
                    curveToRelative(2.588f, 0f, 4.949f, 0.985f, 6.724f, 2.598f)
                    lineToRelative(0.347f, 0.33f)
                    curveToRelative(0.253f, 0.253f, 0.585f, 0.608f, 0.929f, 0.98f)
                    verticalLineTo(4.5f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 2f, 0f)
                    verticalLineToRelative(4f)
                    lineToRelative(-0.002f, 0.031f)
                    verticalLineToRelative(0.044f)
                    lineToRelative(-0.007f, 0.053f)
                    arcToRelative(1.02f, 1.02f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.045f, 0.195f)
                    lineToRelative(-0.012f, 0.034f)
                    arcToRelative(0.942f, 0.942f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.048f, 0.107f)
                    lineToRelative(-0.014f, 0.023f)
                    lineToRelative(-0.028f, 0.05f)
                    lineToRelative(-0.028f, 0.04f)
                    arcToRelative(0.998f, 0.998f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.033f, 0.043f)
                    lineToRelative(-0.02f, 0.025f)
                    curveToRelative(-0.008f, 0.01f, -0.016f, 0.022f, -0.026f, 0.032f)
                    lineToRelative(-0.03f, 0.03f)
                    lineToRelative(-0.01f, 0.01f)
                    lineToRelative(-0.033f, 0.031f)
                    lineToRelative(-0.021f, 0.018f)
                    lineToRelative(-0.02f, 0.015f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.055f, 0.041f)
                    curveToRelative(-0.01f, 0.008f, -0.022f, 0.015f, -0.033f, 0.022f)
                    arcToRelative(0.988f, 0.988f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.053f, 0.031f)
                    lineToRelative(-0.021f, 0.012f)
                    arcToRelative(0.99f, 0.99f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.123f, 0.053f)
                    lineToRelative(-0.022f, 0.008f)
                    lineToRelative(-0.046f, 0.014f)
                    arcToRelative(0.994f, 0.994f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.065f, 0.017f)
                    lineToRelative(-0.018f, 0.002f)
                    arcToRelative(1.004f, 1.004f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.127f, 0.016f)
                    lineToRelative(-0.037f, 0.001f)
                    lineTo(21f, 9.5f)
                    horizontalLineToRelative(-4f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0f, -2f)
                    horizontalLineToRelative(1.747f)
                    curveToRelative(-0.41f, -0.446f, -0.81f, -0.878f, -1.09f, -1.157f)
                    lineToRelative(-0.278f, -0.265f)
                    arcToRelative(8f, 8f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, 11.844f)
                    lineToRelative(0.278f, -0.265f)
                    lineToRelative(0.075f, -0.07f)
                    arcToRelative(1.001f, 1.001f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.408f, 1.408f)
                    lineToRelative(-0.069f, 0.076f)
                    lineToRelative(-0.347f, 0.331f)
                    arcTo(9.966f, 9.966f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 22f)
                    curveTo(6.477f, 22f, 2f, 17.523f, 2f, 12f)
                    reflectiveCurveTo(6.477f, 2f, 12f, 2f)
                    close()
                }
                path(fill = SolidColor(Color.Black)) {
                    moveTo(9f, 9f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 1f)
                    verticalLineToRelative(5f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, -2f, 0f)
                    verticalLineToRelative(-5f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                    pathFillType = PathFillType.EvenOdd
                ) {
                    moveTo(14f, 9f)
                    arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.5f, 2.5f)
                    verticalLineToRelative(2f)
                    arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -5f, 0f)
                    verticalLineToRelative(-2f)
                    arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14f, 9f)
                    close()
                    moveTo(14f, 11f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.5f, 0.5f)
                    verticalLineToRelative(2f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1f, 0f)
                    verticalLineToRelative(-2f)
                    arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.5f, -0.5f)
                    close()
                }
            }
        }.build()

        return _Forward10S!!
    }

@Suppress("ObjectPropertyName")
private var _Forward10S: ImageVector? = null