package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Completed: ImageVector
    get() {
        if (_Completed != null) {
            return _Completed!!
        }
        _Completed = ImageVector.Builder(
            name = "Completed",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(12f, 23f)
                curveTo(18.075f, 23f, 23f, 18.075f, 23f, 12f)
                curveTo(23f, 5.925f, 18.075f, 1f, 12f, 1f)
                curveTo(5.925f, 1f, 1f, 5.925f, 1f, 12f)
                curveTo(1f, 18.075f, 5.925f, 23f, 12f, 23f)
                close()
                moveTo(17.883f, 9.822f)
                lineTo(11.685f, 17.411f)
                curveTo(11.403f, 17.781f, 10.965f, 17.998f, 10.5f, 18f)
                curveTo(10.035f, 18.002f, 9.595f, 17.788f, 9.31f, 17.421f)
                lineTo(5.816f, 13.421f)
                curveTo(5.307f, 12.767f, 5.425f, 11.825f, 6.079f, 11.316f)
                curveTo(6.733f, 10.807f, 7.675f, 10.925f, 8.184f, 11.579f)
                lineTo(10.484f, 14.044f)
                lineTo(15.5f, 8f)
                curveTo(16.003f, 7.342f, 16.945f, 7.216f, 17.603f, 7.72f)
                curveTo(18.261f, 8.223f, 18.386f, 9.164f, 17.883f, 9.822f)
                close()
            }
        }.build()

        return _Completed!!
    }

@Suppress("ObjectPropertyName")
private var _Completed: ImageVector? = null
