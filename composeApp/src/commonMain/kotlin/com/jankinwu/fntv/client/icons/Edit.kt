package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Edit: ImageVector
    get() {
        if (_Edit != null) {
            return _Edit!!
        }
        _Edit = ImageVector.Builder(
            name = "Edit",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(257.7f, 752f)
                curveToRelative(2f, 0f, 4f, -0.2f, 6f, -0.5f)
                lineTo(431.9f, 722f)
                curveToRelative(2f, -0.4f, 3.9f, -1.3f, 5.3f, -2.8f)
                lineToRelative(423.9f, -423.9f)
                arcToRelative(10f, 10f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -14.1f)
                lineTo(694.9f, 114.9f)
                curveToRelative(-1.9f, -1.9f, -4.4f, -2.9f, -7.1f, -2.9f)
                reflectiveCurveToRelative(-5.2f, 1f, -7.1f, 2.9f)
                lineTo(256.8f, 538.8f)
                curveToRelative(-1.5f, 1.5f, -2.4f, 3.3f, -2.8f, 5.3f)
                lineToRelative(-29.5f, 168.2f)
                arcToRelative(33.5f, 33.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.4f, 29.8f)
                curveToRelative(6.6f, 6.4f, 14.9f, 9.9f, 23.8f, 9.9f)
                close()
                moveTo(325.1f, 577.6f)
                lineTo(687.8f, 215f)
                lineToRelative(73.3f, 73.3f)
                lineToRelative(-362.7f, 362.6f)
                lineToRelative(-88.9f, 15.7f)
                lineToRelative(15.6f, -89f)
                close()
                moveTo(880f, 836f)
                lineTo(144f, 836f)
                curveToRelative(-17.7f, 0f, -32f, 14.3f, -32f, 32f)
                verticalLineToRelative(36f)
                curveToRelative(0f, 4.4f, 3.6f, 8f, 8f, 8f)
                horizontalLineToRelative(784f)
                curveToRelative(4.4f, 0f, 8f, -3.6f, 8f, -8f)
                verticalLineToRelative(-36f)
                curveToRelative(0f, -17.7f, -14.3f, -32f, -32f, -32f)
                close()
            }
        }.build()

        return _Edit!!
    }

@Suppress("ObjectPropertyName")
private var _Edit: ImageVector? = null