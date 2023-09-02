package ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Placeholder: ImageVector
    get() {
        if (_placeholder != null) {
            return _placeholder!!
        }
        _placeholder = Builder(name = "IcPlaceholder", defaultWidth = 64.0.dp, defaultHeight =
        64.0.dp, viewportWidth = 64.0f, viewportHeight = 64.0f).apply {
            path(fill = null, stroke = null, strokeLineWidth = 0.0f, strokeLineCap = Butt,
                strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(64.0f)
                verticalLineToRelative(64.0f)
                horizontalLineToRelative(-64.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFB8B8B8)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd) {
                moveTo(34.5076f, 23.1299f)
                lineTo(27.0f, 36.0f)
                horizontalLineTo(41.0f)
                curveTo(41.0f, 44.8366f, 33.8366f, 52.0f, 25.0f, 52.0f)
                curveTo(16.1634f, 52.0f, 9.0f, 44.8366f, 9.0f, 36.0f)
                curveTo(9.0f, 27.1634f, 16.1634f, 20.0f, 25.0f, 20.0f)
                curveTo(28.5605f, 20.0f, 31.8495f, 21.163f, 34.5076f, 23.1299f)
                close()
                moveTo(34.5076f, 23.1299f)
                lineTo(41.0f, 12.0f)
                lineTo(55.0f, 36.0f)
                horizontalLineTo(41.0f)
                curveTo(41.0f, 30.724f, 38.4463f, 26.0444f, 34.5076f, 23.1299f)
                close()
            }
        }
            .build()
        return _placeholder!!
    }

private var _placeholder: ImageVector? = null
