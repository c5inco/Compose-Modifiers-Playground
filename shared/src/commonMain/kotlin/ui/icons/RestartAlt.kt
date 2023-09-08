package ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val AppIcons.RestartAlt: ImageVector
    get() {
        if (_restartAlt != null) {
            return _restartAlt!!
        }
        _restartAlt = materialIcon(name = "Outlined.RestartAlt") {
            materialPath {
                moveTo(6.0f, 13.0f)
                curveToRelative(0.0f, -1.65f, 0.67f, -3.15f, 1.76f, -4.24f)
                lineTo(6.34f, 7.34f)
                curveTo(4.9f, 8.79f, 4.0f, 10.79f, 4.0f, 13.0f)
                curveToRelative(0.0f, 4.08f, 3.05f, 7.44f, 7.0f, 7.93f)
                verticalLineToRelative(-2.02f)
                curveTo(8.17f, 18.43f, 6.0f, 15.97f, 6.0f, 13.0f)
                close()
                moveTo(20.0f, 13.0f)
                curveToRelative(0.0f, -4.42f, -3.58f, -8.0f, -8.0f, -8.0f)
                curveToRelative(-0.06f, 0.0f, -0.12f, 0.01f, -0.18f, 0.01f)
                lineToRelative(1.09f, -1.09f)
                lineTo(11.5f, 2.5f)
                lineTo(8.0f, 6.0f)
                lineToRelative(3.5f, 3.5f)
                lineToRelative(1.41f, -1.41f)
                lineToRelative(-1.08f, -1.08f)
                curveTo(11.89f, 7.01f, 11.95f, 7.0f, 12.0f, 7.0f)
                curveToRelative(3.31f, 0.0f, 6.0f, 2.69f, 6.0f, 6.0f)
                curveToRelative(0.0f, 2.97f, -2.17f, 5.43f, -5.0f, 5.91f)
                verticalLineToRelative(2.02f)
                curveTo(16.95f, 20.44f, 20.0f, 17.08f, 20.0f, 13.0f)
                close()
            }
        }
        return _restartAlt!!
    }

private var _restartAlt: ImageVector? = null