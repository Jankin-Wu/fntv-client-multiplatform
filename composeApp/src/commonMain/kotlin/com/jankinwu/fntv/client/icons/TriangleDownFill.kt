package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val TriangleDownFill: ImageVector
    get() {
        if (_TriangleDownFill != null) {
            return _TriangleDownFill!!
        }
        _TriangleDownFill = ImageVector.Builder(
            name = "TriangleDownFill",
            defaultWidth = 300.dp,
            defaultHeight = 300.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12.707f, 15.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.414f, 0f)
                lineToRelative(-3.586f, -3.586f)
                curveToRelative(-0.63f, -0.63f, -0.184f, -1.707f, 0.707f, -1.707f)
                horizontalLineToRelative(7.172f)
                curveToRelative(0.89f, 0f, 1.337f, 1.077f, 0.707f, 1.707f)
                lineToRelative(-3.586f, 3.586f)
                close()
            }
        }.build()

        return _TriangleDownFill!!
    }

@Suppress("ObjectPropertyName")
private var _TriangleDownFill: ImageVector? = null