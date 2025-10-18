package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CategoryIcon: ImageVector
    get() {
        if (_Category != null) {
            return _Category!!
        }
        _Category = ImageVector.Builder(
            name = "Category",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(288f, 158.2f)
                curveToRelative(34.8f, 0f, 67.5f, 13.5f, 92.1f, 38.1f)
                curveToRelative(24.6f, 24.6f, 38.1f, 57.3f, 38.1f, 92.1f)
                verticalLineToRelative(130.2f)
                horizontalLineTo(288f)
                curveToRelative(-34.8f, 0f, -67.5f, -13.5f, -92.1f, -38.1f)
                curveToRelative(-24.6f, -24.6f, -38.1f, -57.3f, -38.1f, -92.1f)
                curveToRelative(0f, -34.9f, 13.5f, -67.7f, 38f, -92.2f)
                reflectiveCurveToRelative(57.3f, -38f, 92.2f, -38f)
                moveToRelative(0f, -60f)
                curveToRelative(-105.4f, 0f, -190.2f, 84.9f, -190.2f, 190.2f)
                curveToRelative(0f, 105.1f, 85.2f, 190.2f, 190.2f, 190.2f)
                horizontalLineToRelative(178.2f)
                curveToRelative(6.6f, 0f, 12f, -5.4f, 12f, -12f)
                verticalLineTo(288.5f)
                curveTo(478.2f, 183.4f, 393f, 98.2f, 288f, 98.2f)
                close()
                moveTo(418.2f, 607.8f)
                verticalLineToRelative(128.5f)
                curveToRelative(0f, 34.9f, -13.7f, 68f, -38.5f, 93.1f)
                curveToRelative(-24.7f, 25f, -57.3f, 38.8f, -91.8f, 38.8f)
                horizontalLineToRelative(-1f)
                curveToRelative(-34.4f, -0.3f, -66.7f, -13.8f, -91.1f, -38.1f)
                curveToRelative(-24.3f, -24.3f, -37.9f, -56.7f, -38.1f, -91.1f)
                curveToRelative(-0.3f, -34.3f, 13.2f, -67f, 37.9f, -91.8f)
                curveToRelative(12.3f, -12.4f, 26.6f, -22.1f, 42.5f, -28.9f)
                curveToRelative(16.4f, -7f, 33.7f, -10.5f, 51.6f, -10.5f)
                horizontalLineToRelative(128.5f)
                moveToRelative(48f, -60f)
                horizontalLineTo(289.7f)
                curveToRelative(-105.4f, 0f, -192.7f, 86.3f, -191.9f, 191.7f)
                curveToRelative(0.8f, 103.9f, 84.9f, 188f, 188.8f, 188.8f)
                horizontalLineToRelative(1.4f)
                curveToRelative(104.8f, 0f, 190.3f, -87f, 190.3f, -191.9f)
                verticalLineTo(559.8f)
                curveToRelative(-0.1f, -6.6f, -5.5f, -12f, -12.1f, -12f)
                close()
                moveTo(737.6f, 158.2f)
                curveToRelative(34.7f, 0f, 67.4f, 13.5f, 92f, 38.1f)
                curveToRelative(24.6f, 24.5f, 38.2f, 57.2f, 38.2f, 91.9f)
                curveToRelative(0.1f, 34.1f, -13.5f, 66.5f, -38.2f, 91.3f)
                curveToRelative(-12.3f, 12.3f, -26.5f, 22f, -42.4f, 28.7f)
                curveToRelative(-16.3f, 6.9f, -33.5f, 10.5f, -51.2f, 10.5f)
                horizontalLineTo(607.4f)
                verticalLineTo(288.5f)
                curveToRelative(0f, -34.8f, 13.5f, -67.5f, 38.1f, -92.1f)
                reflectiveCurveToRelative(57.3f, -38.2f, 92.1f, -38.2f)
                moveToRelative(0f, -60f)
                curveToRelative(-105.1f, 0f, -190.2f, 85.2f, -190.2f, 190.2f)
                verticalLineToRelative(178.2f)
                curveToRelative(0f, 6.6f, 5.4f, 12f, 12f, 12f)
                horizontalLineTo(736f)
                curveToRelative(104.8f, 0f, 192f, -85.8f, 191.9f, -190.6f)
                curveToRelative(-0.3f, -104.8f, -85.4f, -189.8f, -190.3f, -189.8f)
                close()
                moveTo(735.9f, 607.8f)
                curveToRelative(17.8f, 0f, 35.2f, 3.5f, 51.6f, 10.5f)
                curveToRelative(15.9f, 6.8f, 30.2f, 16.5f, 42.5f, 28.9f)
                curveToRelative(24.7f, 24.9f, 38.1f, 57.5f, 37.9f, 91.8f)
                curveToRelative(-0.3f, 34.4f, -13.8f, 66.7f, -38.1f, 91.1f)
                curveToRelative(-24.3f, 24.3f, -56.7f, 37.9f, -91.1f, 38.1f)
                horizontalLineToRelative(-1f)
                curveToRelative(-34.4f, 0f, -67f, -13.8f, -91.8f, -38.8f)
                curveToRelative(-24.8f, -25.1f, -38.5f, -58.2f, -38.5f, -93.1f)
                verticalLineTo(607.8f)
                horizontalLineToRelative(128.5f)
                moveToRelative(0f, -60f)
                horizontalLineTo(559.4f)
                curveToRelative(-6.6f, 0f, -12f, 5.4f, -12f, 12f)
                verticalLineToRelative(176.5f)
                curveToRelative(0f, 105f, 85.5f, 191.9f, 190.3f, 191.9f)
                horizontalLineToRelative(1.4f)
                curveToRelative(103.9f, -0.8f, 188f, -84.9f, 188.8f, -188.8f)
                curveToRelative(0.7f, -105.3f, -86.6f, -191.6f, -192f, -191.6f)
                close()
            }
        }.build()

        return _Category!!
    }

@Suppress("ObjectPropertyName")
private var _Category: ImageVector? = null
