package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MediaLibrary: ImageVector
    get() {
        if (_MediaLibrary != null) {
            return _MediaLibrary!!
        }
        _MediaLibrary = ImageVector.Builder(
            name = "MediaLibrary",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(694.6f, 397.3f)
                lineTo(564.1f, 318.5f)
                arcTo(34.4f, 34.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 512f, 347.9f)
                verticalLineToRelative(157.6f)
                curveToRelative(0f, 26.8f, 29.2f, 43.2f, 52.1f, 29.4f)
                lineToRelative(130.6f, -78.8f)
                arcToRelative(34.3f, 34.3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -58.7f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(564.1f, 534.9f)
                lineToRelative(130.6f, -78.8f)
                arcToRelative(34.3f, 34.3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -58.7f)
                lineTo(564.1f, 318.5f)
                arcTo(34.4f, 34.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 512f, 347.9f)
                verticalLineToRelative(157.6f)
                curveToRelative(0f, 26.8f, 29.2f, 43.2f, 52.1f, 29.4f)
                close()
                moveTo(601.7f, 426.6f)
                lineTo(585.1f, 416.8f)
                verticalLineToRelative(20f)
                lineToRelative(16.6f, -10f)
                close()
                moveTo(99.5f, 347.4f)
                arcToRelative(36.6f, 36.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 36.6f, 36.6f)
                verticalLineToRelative(369.8f)
                curveToRelative(0f, 42.6f, 34.6f, 77.2f, 77.2f, 77.2f)
                horizontalLineToRelative(483.6f)
                arcToRelative(36.6f, 36.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 73.1f)
                lineTo(213.3f, 904.1f)
                arcToRelative(150.3f, 150.3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -150.3f, -150.3f)
                lineTo(63f, 384f)
                arcToRelative(36.6f, 36.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 36.6f, -36.6f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(810.7f, 660.3f)
                curveToRelative(42.6f, 0f, 77.2f, -34.6f, 77.2f, -77.2f)
                lineTo(887.9f, 270.2f)
                curveToRelative(0f, -42.6f, -34.5f, -77.2f, -77.2f, -77.2f)
                lineTo(384f, 193f)
                curveToRelative(-42.6f, 0f, -77.2f, 34.5f, -77.2f, 77.2f)
                verticalLineToRelative(312.9f)
                curveToRelative(0f, 42.6f, 34.5f, 77.2f, 77.2f, 77.2f)
                horizontalLineToRelative(426.7f)
                close()
                moveTo(961f, 583.1f)
                curveToRelative(0f, 83f, -67.3f, 150.4f, -150.3f, 150.4f)
                lineTo(384f, 733.5f)
                arcToRelative(150.4f, 150.4f, 0f, isMoreThanHalf = false, isPositiveArc = true, -150.3f, -150.4f)
                lineTo(233.7f, 270.2f)
                curveToRelative(0f, -83f, 67.3f, -150.3f, 150.3f, -150.3f)
                horizontalLineToRelative(426.7f)
                curveToRelative(83f, 0f, 150.3f, 67.3f, 150.3f, 150.3f)
                verticalLineToRelative(312.9f)
                close()
            }
        }.build()

        return _MediaLibrary!!
    }

@Suppress("ObjectPropertyName")
private var _MediaLibrary: ImageVector? = null
