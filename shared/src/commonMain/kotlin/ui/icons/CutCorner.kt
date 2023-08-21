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

val AppIcons.CutCorner: ImageVector
    get() {
        if (_cutCorner != null) {
            return _cutCorner!!
        }
        _cutCorner = Builder(name = "CutCorner", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.0f, 5.338f)
                lineTo(19.4948f, 12.0f)
                lineTo(12.0f, 18.662f)
                lineTo(4.5052f, 12.0f)
                lineTo(12.0f, 5.338f)
                close()
            }
        }
        .build()
        return _cutCorner!!
    }

private var _cutCorner: ImageVector? = null
