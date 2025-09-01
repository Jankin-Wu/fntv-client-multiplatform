package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Lifted: ImageVector
    get() {
        if (_Lifted != null) {
            return _Lifted!!
        }
        _Lifted = ImageVector.Builder(
            name = "Lifted",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color(0xFF5A5A5A))) {
                moveTo(533.2f, 155.1f)
                arcToRelative(217.6f, 217.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 307.8f, 307.7f)
                lineToRelative(-36.2f, 36.2f)
                lineToRelative(-81.5f, 81.5f)
                lineToRelative(-54.3f, -54.3f)
                lineToRelative(81.4f, -81.4f)
                lineToRelative(36.2f, -36.2f)
                arcToRelative(140.8f, 140.8f, 0f, isMoreThanHalf = false, isPositiveArc = false, -199.1f, -199.1f)
                lineToRelative(-36.2f, 36.2f)
                lineToRelative(-81.5f, 81.5f)
                lineToRelative(-54.3f, -54.3f)
                lineTo(497.1f, 191.4f)
                lineToRelative(36.2f, -36.2f)
                close()
                moveTo(307f, 490f)
                lineTo(225.5f, 571.5f)
                lineToRelative(-36.2f, 36.2f)
                arcToRelative(140.8f, 140.8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 199.2f, 199.1f)
                lineToRelative(36.2f, -36.2f)
                lineToRelative(81.5f, -81.5f)
                lineToRelative(54.3f, 54.3f)
                lineToRelative(-81.5f, 81.5f)
                lineToRelative(-36.2f, 36.2f)
                arcTo(217.6f, 217.6f, 0f, isMoreThanHalf = true, isPositiveArc = true, 135f, 553.4f)
                lineToRelative(36.2f, -36.2f)
                lineToRelative(81.5f, -81.5f)
                lineToRelative(54.3f, 54.3f)
                close()
                moveTo(312.3f, 50.2f)
                lineToRelative(53.1f, 197.8f)
                lineToRelative(-74.2f, 19.9f)
                lineToRelative(-53f, -197.8f)
                lineToRelative(74.2f, -19.9f)
                close()
                moveTo(755.8f, 638.5f)
                lineToRelative(197.8f, 53f)
                lineToRelative(-19.8f, 74.2f)
                lineToRelative(-197.9f, -53.1f)
                lineToRelative(19.9f, -74.2f)
                close()
                moveTo(233.3f, 325.8f)
                lineToRelative(-197.8f, -53.1f)
                lineToRelative(-19.9f, 74.2f)
                lineToRelative(197.8f, 53f)
                lineToRelative(19.9f, -74.2f)
                close()
                moveTo(731.1f, 968.4f)
                lineToRelative(-53.1f, -197.8f)
                lineToRelative(-74.2f, 19.8f)
                lineToRelative(53f, 197.8f)
                lineToRelative(74.2f, -19.8f)
                close()
            }
        }.build()

        return _Lifted!!
    }

@Suppress("ObjectPropertyName")
private var _Lifted: ImageVector? = null