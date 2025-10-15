package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val History: ImageVector
    get() {
        if (_History != null) {
            return _History!!
        }
        _History = ImageVector.Builder(
            name = "History",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(960f, 512f)
                arcTo(448f, 448f, 0f, isMoreThanHalf = false, isPositiveArc = false, 149.3f, 249f)
                verticalLineTo(106.7f)
                horizontalLineTo(64f)
                verticalLineToRelative(298.6f)
                horizontalLineToRelative(298.7f)
                verticalLineTo(320f)
                horizontalLineTo(204.3f)
                arcToRelative(364.6f, 364.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 135.9f, -127.5f)
                arcToRelative(362.7f, 362.7f, 0f, isMoreThanHalf = true, isPositiveArc = true, -188.9f, 357.8f)
                lineToRelative(-4.4f, -42.5f)
                lineToRelative(-84.9f, 8.9f)
                lineToRelative(4.5f, 42.4f)
                arcTo(448f, 448f, 0f, isMoreThanHalf = false, isPositiveArc = false, 960f, 512f)
                close()
                moveTo(469.3f, 256f)
                verticalLineToRelative(273.7f)
                lineToRelative(149.4f, 149.4f)
                lineToRelative(60.4f, -60.4f)
                lineToRelative(-124.4f, -124.4f)
                verticalLineTo(256f)
                horizontalLineTo(469.3f)
                close()
            }
        }.build()

        return _History!!
    }

@Suppress("ObjectPropertyName")
private var _History: ImageVector? = null
