package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val TriangleUpFill: ImageVector
    get() {
        if (_TriangleUpFill != null) {
            return _TriangleUpFill!!
        }
        _TriangleUpFill = ImageVector.Builder(
            name = "TriangleUpFill",
            defaultWidth = 1.dp,
            defaultHeight = 1.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(11.293f, 8.707f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.414f, 0f)
                lineToRelative(3.586f, 3.586f)
                curveToRelative(0.63f, 0.63f, 0.184f, 1.707f, -0.707f, 1.707f)
                horizontalLineTo(8.414f)
                curveToRelative(-0.89f, 0f, -1.337f, -1.077f, -0.707f, -1.707f)
                lineToRelative(3.586f, -3.586f)
                close()
            }
        }.build()

        return _TriangleUpFill!!
    }

@Suppress("ObjectPropertyName")
private var _TriangleUpFill: ImageVector? = null