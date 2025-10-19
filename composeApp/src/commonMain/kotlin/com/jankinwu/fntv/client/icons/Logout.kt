package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Logout: ImageVector
    get() {
        if (_Logout != null) {
            return _Logout!!
        }
        _Logout = ImageVector.Builder(
            name = "Logout",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(904.8f, 512.2f)
                lineToRelative(-252.4f, 252.4f)
                lineToRelative(-60.4f, -60.4f)
                lineToRelative(149.4f, -149.3f)
                horizontalLineToRelative(-409f)
                lineTo(332.5f, 469.6f)
                horizontalLineToRelative(409f)
                lineTo(592.1f, 320.2f)
                lineToRelative(60.4f, -60.3f)
                lineToRelative(252.4f, 252.3f)
                close()
                moveTo(417.8f, 192.2f)
                lineTo(204.5f, 192.2f)
                verticalLineToRelative(640f)
                horizontalLineToRelative(213.3f)
                verticalLineToRelative(85.4f)
                lineTo(119.2f, 917.6f)
                lineTo(119.2f, 106.9f)
                horizontalLineToRelative(298.6f)
                verticalLineToRelative(85.3f)
                close()
            }
        }.build()

        return _Logout!!
    }

@Suppress("ObjectPropertyName")
private var _Logout: ImageVector? = null