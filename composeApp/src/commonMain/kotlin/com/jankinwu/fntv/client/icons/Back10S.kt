package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Back10S: ImageVector
    get() {
        if (_Back10S != null) {
            return _Back10S!!
        }
        _Back10S = ImageVector.Builder(
            name = "Back10S",
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
                    curveToRelative(5.523f, 0f, 10f, 4.477f, 10f, 10f)
                    reflectiveCurveToRelative(-4.477f, 10f, -10f, 10f)
                    arcToRelative(9.967f, 9.967f, 0f, isMoreThanHalf = false, isPositiveArc = true, -6.724f, -2.598f)
                    lineToRelative(-0.347f, -0.33f)
                    lineToRelative(-0.069f, -0.077f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.408f, -1.407f)
                    lineToRelative(0.075f, 0.07f)
                    lineToRelative(0.278f, 0.264f)
                    arcToRelative(8f, 8f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, -11.844f)
                    lineToRelative(-0.278f, 0.265f)
                    curveToRelative(-0.28f, 0.279f, -0.68f, 0.711f, -1.09f, 1.157f)
                    lineTo(7f, 7.5f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 2f)
                    lineTo(3f, 9.5f)
                    lineToRelative(-0.023f, -0.002f)
                    horizontalLineToRelative(-0.038f)
                    arcToRelative(1.004f, 1.004f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.126f, -0.017f)
                    lineToRelative(-0.018f, -0.002f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.066f, -0.017f)
                    lineToRelative(-0.045f, -0.014f)
                    lineToRelative(-0.022f, -0.008f)
                    arcToRelative(0.995f, 0.995f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.123f, -0.053f)
                    lineToRelative(-0.021f, -0.012f)
                    arcToRelative(0.999f, 0.999f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.053f, -0.031f)
                    curveToRelative(-0.011f, -0.007f, -0.023f, -0.014f, -0.033f, -0.022f)
                    arcToRelative(1.01f, 1.01f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.055f, -0.04f)
                    lineToRelative(-0.02f, -0.016f)
                    lineToRelative(-0.021f, -0.018f)
                    lineToRelative(-0.032f, -0.031f)
                    lineToRelative(-0.011f, -0.01f)
                    lineToRelative(-0.03f, -0.03f)
                    curveToRelative(-0.01f, -0.01f, -0.018f, -0.022f, -0.027f, -0.032f)
                    lineToRelative(-0.02f, -0.025f)
                    arcToRelative(0.99f, 0.99f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.06f, -0.084f)
                    lineToRelative(-0.028f, -0.049f)
                    lineToRelative(-0.014f, -0.023f)
                    arcToRelative(0.987f, 0.987f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.048f, -0.107f)
                    lineToRelative(-0.012f, -0.034f)
                    arcToRelative(0.985f, 0.985f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.03f, -0.105f)
                    lineToRelative(-0.006f, -0.032f)
                    arcToRelative(1.003f, 1.003f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.01f, -0.058f)
                    lineToRelative(-0.005f, -0.053f)
                    lineToRelative(-0.001f, -0.044f)
                    lineTo(2f, 8.5f)
                    verticalLineToRelative(-4f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 0f)
                    verticalLineToRelative(1.407f)
                    curveToRelative(0.344f, -0.371f, 0.676f, -0.726f, 0.929f, -0.978f)
                    lineToRelative(0.347f, -0.331f)
                    arcTo(9.967f, 9.967f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 2f)
                    close()
                    moveTo(9f, 9f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 1f)
                    verticalLineToRelative(5f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, -2f, 0f)
                    verticalLineToRelative(-5f)
                    arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
                    close()
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

        return _Back10S!!
    }

@Suppress("ObjectPropertyName")
private var _Back10S: ImageVector? = null
