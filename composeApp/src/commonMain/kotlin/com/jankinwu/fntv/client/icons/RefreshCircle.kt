package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RefreshCircle: ImageVector
    get() {
        if (_Refresh != null) {
            return _Refresh!!
        }
        _Refresh = ImageVector.Builder(
            name = "Refresh",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color(0xFFE6E6E6))) {
                moveTo(874.7f, 426.7f)
                horizontalLineToRelative(-213.3f)
                arcToRelative(21.3f, 21.3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -21.3f, -21.3f)
                verticalLineToRelative(-12.4f)
                arcToRelative(20.9f, 20.9f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.4f, -15.4f)
                lineToRelative(75.9f, -75.9f)
                arcTo(295.7f, 295.7f, 0f, isMoreThanHalf = false, isPositiveArc = false, 512f, 213.3f)
                arcToRelative(298.7f, 298.7f, 0f, isMoreThanHalf = true, isPositiveArc = false, 298.7f, 318.7f)
                arcToRelative(21.3f, 21.3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 21.3f, -20.1f)
                horizontalLineToRelative(42.7f)
                arcToRelative(22.2f, 22.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15.4f, 6.8f)
                arcToRelative(21.3f, 21.3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.5f, 15.8f)
                arcToRelative(384f, 384f, 0f, isMoreThanHalf = true, isPositiveArc = true, -111.8f, -294f)
                lineToRelative(63.6f, -63.6f)
                arcToRelative(20.9f, 20.9f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14.9f, -6.4f)
                horizontalLineToRelative(12.4f)
                arcToRelative(21.3f, 21.3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 21.3f, 21.3f)
                verticalLineToRelative(213.3f)
                arcToRelative(21.3f, 21.3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -21.3f, 21.3f)
                close()
            }
        }.build()

        return _Refresh!!
    }

@Suppress("ObjectPropertyName")
private var _Refresh: ImageVector? = null