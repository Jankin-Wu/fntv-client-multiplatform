package com.jankinwu.fntv.client.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MediaLibrary: ImageVector
    get() {
        if (_MediaLibrary != null) {
            return _MediaLibrary!!
        }
        _MediaLibrary = ImageVector.Builder(
            name = "MediaLibrary",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(512f, 102.4f)
                arcToRelative(102.4f, 102.4f, 0f, isMoreThanHalf = false, isPositiveArc = true, 102.4f, 102.4f)
                arcToRelative(51.2f, 51.2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 51.2f, 51.2f)
                horizontalLineToRelative(204.8f)
                arcToRelative(102.4f, 102.4f, 0f, isMoreThanHalf = false, isPositiveArc = true, 102.4f, 102.4f)
                verticalLineToRelative(460.8f)
                arcToRelative(102.4f, 102.4f, 0f, isMoreThanHalf = false, isPositiveArc = true, -102.4f, 102.4f)
                lineTo(153.6f, 921.6f)
                arcToRelative(102.4f, 102.4f, 0f, isMoreThanHalf = false, isPositiveArc = true, -102.4f, -102.4f)
                lineTo(51.2f, 204.8f)
                arcToRelative(102.4f, 102.4f, 0f, isMoreThanHalf = false, isPositiveArc = true, 102.4f, -102.4f)
                close()
                moveTo(870.4f, 307.2f)
                lineTo(102.4f, 307.2f)
                verticalLineToRelative(512f)
                curveToRelative(0f, 14.1f, 5f, 26.2f, 15f, 36.2f)
                arcTo(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 153.6f, 870.4f)
                horizontalLineToRelative(716.8f)
                arcToRelative(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 36.2f, -15f)
                arcTo(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 921.6f, 819.2f)
                lineTo(921.6f, 358.4f)
                arcToRelative(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -15f, -36.2f)
                arcTo(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 870.4f, 307.2f)
                close()
                moveTo(486.4f, 442.8f)
                lineToRelative(153.6f, 97.3f)
                curveToRelative(34.2f, 21.7f, 34.2f, 75.7f, 0f, 97.3f)
                lineToRelative(-153.6f, 97.3f)
                curveToRelative(-34.2f, 21.7f, -76.8f, -5.4f, -76.8f, -48.6f)
                lineTo(409.6f, 491.5f)
                curveToRelative(0f, -43.3f, 42.6f, -70.3f, 76.8f, -48.6f)
                close()
                moveTo(461.4f, 487.6f)
                arcTo(10.2f, 10.2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 460.8f, 491.5f)
                verticalLineToRelative(194.7f)
                arcToRelative(10.2f, 10.2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.6f, 3.9f)
                lineToRelative(151.2f, -95.8f)
                curveToRelative(1.2f, -0.8f, 1.8f, -2.6f, 1.8f, -5.4f)
                curveToRelative(0f, -2.9f, -0.6f, -4.6f, -1.8f, -5.4f)
                lineToRelative(-151.2f, -95.7f)
                close()
                moveTo(512f, 153.6f)
                lineTo(153.6f, 153.6f)
                arcToRelative(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -36.2f, 15f)
                arcTo(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 102.4f, 204.8f)
                verticalLineToRelative(51.2f)
                horizontalLineToRelative(474.3f)
                arcTo(99f, 99f, 0f, isMoreThanHalf = false, isPositiveArc = true, 563.2f, 204.8f)
                arcToRelative(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -15f, -36.2f)
                arcTo(49.4f, 49.4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 512f, 153.6f)
                close()
            }
        }.build()

        return _MediaLibrary!!
    }

@Suppress("ObjectPropertyName")
private var _MediaLibrary: ImageVector? = null
