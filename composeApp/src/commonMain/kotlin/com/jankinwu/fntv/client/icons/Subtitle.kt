package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Subtitle: ImageVector
    get() {
        if (_Subtitle != null) {
            return _Subtitle!!
        }
        _Subtitle = ImageVector.Builder(
            name = "Subtitle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            group(
                clipPathData = PathData {
                    moveTo(0f, 0f)
                    horizontalLineToRelative(24f)
                    verticalLineToRelative(24f)
                    horizontalLineToRelative(-24f)
                    close()
                }
            ) {
                path(fill = SolidColor(Color.Black)) {
                    moveTo(20f, 3f)
                    arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 3f)
                    verticalLineToRelative(12f)
                    arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3f, 3f)
                    lineTo(4f, 21f)
                    arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3f, -3f)
                    lineTo(1f, 6f)
                    arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, -3f)
                    horizontalLineToRelative(16f)
                    close()
                    moveTo(9f, 8f)
                    curveToRelative(-2.208f, 0f, -4f, 1.792f, -4f, 4f)
                    arcToRelative(4.001f, 4.001f, 0f, isMoreThanHalf = false, isPositiveArc = false, 6.041f, 3.44f)
                    curveToRelative(0.474f, -0.282f, 0.47f, -0.929f, 0.08f, -1.319f)
                    curveToRelative(-0.39f, -0.39f, -1.029f, -0.356f, -1.559f, -0.201f)
                    arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0.001f, -3.84f)
                    curveToRelative(0.53f, 0.155f, 1.17f, 0.19f, 1.56f, -0.201f)
                    curveToRelative(0.39f, -0.39f, 0.394f, -1.036f, -0.08f, -1.318f)
                    arcTo(3.979f, 3.979f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9f, 8f)
                    close()
                    moveTo(16f, 8f)
                    curveToRelative(-2.208f, 0f, -4f, 1.792f, -4f, 4f)
                    arcToRelative(4.001f, 4.001f, 0f, isMoreThanHalf = false, isPositiveArc = false, 6.041f, 3.44f)
                    curveToRelative(0.474f, -0.282f, 0.47f, -0.928f, 0.08f, -1.319f)
                    curveToRelative(-0.39f, -0.39f, -1.028f, -0.357f, -1.557f, -0.201f)
                    arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0f, -3.84f)
                    curveToRelative(0.53f, 0.155f, 1.168f, 0.19f, 1.559f, -0.201f)
                    curveToRelative(0.39f, -0.39f, 0.394f, -1.036f, -0.08f, -1.318f)
                    arcTo(3.979f, 3.979f, 0f, isMoreThanHalf = false, isPositiveArc = false, 16f, 8f)
                    close()
                }
            }
        }.build()

        return _Subtitle!!
    }

@Suppress("ObjectPropertyName")
private var _Subtitle: ImageVector? = null
