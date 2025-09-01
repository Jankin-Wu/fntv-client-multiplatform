package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val HeartFilled: ImageVector
    get() {
        if (_HeartFill != null) {
            return _HeartFill!!
        }
        _HeartFill = ImageVector.Builder(
            name = "HeartFill",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(923f, 283.6f)
                curveToRelative(-13.4f, -31.1f, -32.6f, -58.9f, -56.9f, -82.8f)
                curveToRelative(-24.3f, -23.8f, -52.5f, -42.4f, -84f, -55.5f)
                curveToRelative(-32.5f, -13.5f, -66.9f, -20.3f, -102.4f, -20.3f)
                curveToRelative(-49.3f, 0f, -97.4f, 13.5f, -139.2f, 39f)
                curveToRelative(-10f, 6.1f, -19.5f, 12.8f, -28.5f, 20.1f)
                curveToRelative(-9f, -7.3f, -18.5f, -14f, -28.5f, -20.1f)
                curveToRelative(-41.8f, -25.5f, -89.9f, -39f, -139.2f, -39f)
                curveToRelative(-35.5f, 0f, -69.9f, 6.8f, -102.4f, 20.3f)
                curveToRelative(-31.4f, 13f, -59.7f, 31.7f, -84f, 55.5f)
                curveToRelative(-24.4f, 23.9f, -43.5f, 51.7f, -56.9f, 82.8f)
                curveToRelative(-13.9f, 32.3f, -21f, 66.6f, -21f, 101.9f)
                curveToRelative(0f, 33.3f, 6.8f, 68f, 20.3f, 103.3f)
                curveToRelative(11.3f, 29.5f, 27.5f, 60.1f, 48.2f, 91f)
                curveToRelative(32.8f, 48.9f, 77.9f, 99.9f, 133.9f, 151.6f)
                curveToRelative(92.8f, 85.7f, 184.7f, 144.9f, 188.6f, 147.3f)
                lineToRelative(23.7f, 15.2f)
                curveToRelative(10.5f, 6.7f, 24f, 6.7f, 34.5f, 0f)
                lineToRelative(23.7f, -15.2f)
                curveToRelative(3.9f, -2.5f, 95.7f, -61.6f, 188.6f, -147.3f)
                curveToRelative(56f, -51.7f, 101.1f, -102.7f, 133.9f, -151.6f)
                curveToRelative(20.7f, -30.9f, 37f, -61.5f, 48.2f, -91f)
                curveToRelative(13.5f, -35.3f, 20.3f, -70f, 20.3f, -103.3f)
                curveToRelative(0.1f, -35.3f, -7f, -69.6f, -20.9f, -101.9f)
                close()
            }
        }.build()

        return _HeartFill!!
    }

@Suppress("ObjectPropertyName")
private var _HeartFill: ImageVector? = null