package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Warning: ImageVector
    get() {
        if (_Warning != null) {
            return _Warning!!
        }
        _Warning = ImageVector.Builder(
            name = "Warning",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(23f, 12f)
                curveTo(23f, 18.075f, 18.075f, 23f, 12f, 23f)
                curveTo(5.925f, 23f, 1f, 18.075f, 1f, 12f)
                curveTo(1f, 5.925f, 5.925f, 1f, 12f, 1f)
                curveTo(18.075f, 1f, 23f, 5.925f, 23f, 12f)
                close()
                moveTo(13.5f, 17.5f)
                curveTo(13.5f, 16.672f, 12.828f, 16f, 12f, 16f)
                curveTo(11.172f, 16f, 10.5f, 16.672f, 10.5f, 17.5f)
                curveTo(10.5f, 18.328f, 11.172f, 19f, 12f, 19f)
                curveTo(12.828f, 19f, 13.5f, 18.328f, 13.5f, 17.5f)
                close()
                moveTo(12f, 5f)
                curveTo(10.914f, 5f, 10.051f, 5.912f, 10.111f, 6.997f)
                lineTo(10.417f, 12.502f)
                curveTo(10.464f, 13.343f, 11.158f, 14f, 12f, 14f)
                curveTo(12.842f, 14f, 13.536f, 13.343f, 13.583f, 12.502f)
                lineTo(13.889f, 6.997f)
                curveTo(13.949f, 5.912f, 13.086f, 5f, 12f, 5f)
                close()
            }
        }.build()

        return _Warning!!
    }

@Suppress("ObjectPropertyName")
private var _Warning: ImageVector? = null
