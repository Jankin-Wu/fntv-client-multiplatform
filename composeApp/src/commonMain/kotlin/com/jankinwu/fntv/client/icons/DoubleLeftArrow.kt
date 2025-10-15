package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val DoubleArrowLeft: ImageVector
    get() {
        if (_DoubleArrowLeft != null) {
            return _DoubleArrowLeft!!
        }
        _DoubleArrowLeft = ImageVector.Builder(
            name = "DoubleArrowLeft",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(15f, 512.2f)
                lineToRelative(-0.3f, -0.5f)
                curveToRelative(0f, -19.2f, 7.4f, -38.3f, 22f, -53f)
                lineTo(464.3f, 31.3f)
                curveToRelative(28.8f, -28.8f, 77f, -29.2f, 106.5f, 0.2f)
                curveToRelative(29.7f, 29.7f, 29.6f, 77.2f, 0.2f, 106.5f)
                lineTo(197.1f, 511.9f)
                lineToRelative(373.9f, 373.9f)
                curveToRelative(28.8f, 28.8f, 29.2f, 77f, -0.2f, 106.5f)
                curveToRelative(-29.7f, 29.7f, -77.2f, 29.6f, -106.5f, 0.2f)
                lineTo(36.8f, 565f)
                curveToRelative(-14.4f, -14.4f, -21.7f, -33.6f, -21.7f, -52.8f)
                close()
                moveTo(425.1f, 512.2f)
                lineToRelative(-0.3f, -0.5f)
                curveToRelative(0f, -19.2f, 7.4f, -38.3f, 22f, -53f)
                lineTo(874.4f, 31.3f)
                curveToRelative(28.8f, -28.8f, 77f, -29.2f, 106.5f, 0.2f)
                curveToRelative(29.7f, 29.7f, 29.6f, 77.2f, 0.2f, 106.5f)
                lineTo(607.3f, 511.9f)
                lineToRelative(373.9f, 373.9f)
                curveToRelative(28.8f, 28.8f, 29.2f, 77f, -0.2f, 106.5f)
                curveToRelative(-29.7f, 29.7f, -77.2f, 29.6f, -106.5f, 0.2f)
                lineTo(446.9f, 565f)
                curveToRelative(-14.4f, -14.4f, -21.7f, -33.6f, -21.7f, -52.8f)
                close()
            }
        }.build()

        return _DoubleArrowLeft!!
    }

@Suppress("ObjectPropertyName")
private var _DoubleArrowLeft: ImageVector? = null