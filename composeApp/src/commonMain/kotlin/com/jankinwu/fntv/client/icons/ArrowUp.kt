package com.jankinwu.fntv.client.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowUp: ImageVector
    get() {
        if (_ArrowUp != null) {
            return _ArrowUp!!
        }
        _ArrowUp = ImageVector.Builder(
            name = "ArrowUp",
            defaultWidth = 1.dp,
            defaultHeight = 1.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 13.061f)
                lineToRelative(-4.95f, -4.95f)
                lineToRelative(-1.413f, 1.414f)
                lineTo(12f, 15.89f)
                lineToRelative(6.364f, -6.364f)
                lineToRelative(-1.414f, -1.414f)
                lineToRelative(-4.95f, 4.95f)
                close()
            }
        }.build()

        return _ArrowUp!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowUp: ImageVector? = null
