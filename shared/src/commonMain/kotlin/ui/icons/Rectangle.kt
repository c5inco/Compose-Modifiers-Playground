package ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Rectangle: ImageVector
    get() {
        if (_rectangle != null) {
            return _rectangle!!
        }
        _rectangle = Builder(name = "Rectangle", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(19.0f, 18.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(19.0f)
                verticalLineTo(18.0f)
                close()
            }
        }
        .build()
        return _rectangle!!
    }

private var _rectangle: ImageVector? = null
