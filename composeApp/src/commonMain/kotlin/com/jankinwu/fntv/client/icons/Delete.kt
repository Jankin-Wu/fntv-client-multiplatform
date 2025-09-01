package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Delete: ImageVector
    get() {
        if (_Delete != null) {
            return _Delete!!
        }
        _Delete = ImageVector.Builder(
            name = "Delete",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 0.7f
            ) {
                moveTo(914.3f, 146.3f)
                curveToRelative(20.1f, 0f, 36.6f, 16.5f, 36.6f, 36.6f)
                reflectiveCurveToRelative(-16.5f, 36.6f, -36.6f, 36.6f)
                lineTo(877.7f, 219.4f)
                verticalLineToRelative(731.4f)
                arcToRelative(73.1f, 73.1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -73.1f, 73.1f)
                lineTo(219.4f, 1024f)
                arcToRelative(73.1f, 73.1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -73.1f, -73.1f)
                lineTo(146.3f, 219.4f)
                lineTo(109.7f, 219.4f)
                arcToRelative(36.6f, 36.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -36.6f, -36.6f)
                curveToRelative(0f, -20.1f, 16.5f, -36.6f, 36.6f, -36.6f)
                horizontalLineToRelative(804.6f)
                close()
                moveTo(804.6f, 950.9f)
                lineTo(804.6f, 219.4f)
                lineTo(219.4f, 219.4f)
                verticalLineToRelative(731.4f)
                horizontalLineToRelative(585.1f)
                close()
                moveTo(402.3f, 877.7f)
                arcToRelative(36.6f, 36.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -36.6f, -36.6f)
                verticalLineToRelative(-512f)
                curveToRelative(0f, -20.1f, 16.5f, -36.6f, 36.6f, -36.6f)
                reflectiveCurveToRelative(36.6f, 16.5f, 36.6f, 36.6f)
                verticalLineToRelative(512f)
                curveToRelative(0f, 20.1f, -16.5f, 36.6f, -36.6f, 36.6f)
                close()
                moveTo(621.7f, 877.7f)
                arcToRelative(36.6f, 36.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -36.6f, -36.6f)
                verticalLineToRelative(-512f)
                curveToRelative(0f, -20.1f, 16.5f, -36.6f, 36.6f, -36.6f)
                reflectiveCurveToRelative(36.6f, 16.5f, 36.6f, 36.6f)
                verticalLineToRelative(512f)
                curveToRelative(0f, 20.1f, -16.5f, 36.6f, -36.6f, 36.6f)
                close()
                moveTo(329.1f, 73.1f)
                arcTo(36.6f, 36.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 292.6f, 36.6f)
                curveToRelative(0f, -20.1f, 16.5f, -36.6f, 36.6f, -36.6f)
                horizontalLineToRelative(365.7f)
                curveToRelative(20.1f, 0f, 36.6f, 16.5f, 36.6f, 36.6f)
                reflectiveCurveToRelative(-16.5f, 36.6f, -36.6f, 36.6f)
                horizontalLineToRelative(-365.7f)
                close()
            }
        }.build()

        return _Delete!!
    }

@Suppress("ObjectPropertyName")
private var _Delete: ImageVector? = null
