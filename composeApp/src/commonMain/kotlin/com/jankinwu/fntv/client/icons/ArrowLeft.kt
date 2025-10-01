package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowLeft: ImageVector
    get() {
        if (_ArrowLeft != null) {
            return _ArrowLeft!!
        }
        _ArrowLeft = ImageVector.Builder(
            name = "ArrowLeft",
            defaultWidth = 1.dp,
            defaultHeight = 1.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(7.828f, 11f)
                horizontalLineTo(20f)
                verticalLineToRelative(2f)
                horizontalLineTo(7.828f)
                lineToRelative(5.364f, 5.364f)
                lineToRelative(-1.414f, 1.414f)
                lineTo(4f, 12f)
                lineToRelative(7.778f, -7.778f)
                lineToRelative(1.414f, 1.414f)
                lineTo(7.828f, 11f)
                close()
            }
        }.build()

        return _ArrowLeft!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowLeft: ImageVector? = null
