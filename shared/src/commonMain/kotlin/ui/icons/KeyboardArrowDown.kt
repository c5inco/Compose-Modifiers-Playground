package ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val AppIcons.KeyboardArrowDown: ImageVector
    get() {
        if (_keyboardArrowDown != null) {
            return _keyboardArrowDown!!
        }
        _keyboardArrowDown = materialIcon(name = "Outlined.KeyboardArrowDown") {
            materialPath {
                moveTo(7.41f, 8.59f)
                lineTo(12.0f, 13.17f)
                lineToRelative(4.59f, -4.58f)
                lineTo(18.0f, 10.0f)
                lineToRelative(-6.0f, 6.0f)
                lineToRelative(-6.0f, -6.0f)
                lineToRelative(1.41f, -1.41f)
                close()
            }
        }
        return _keyboardArrowDown!!
    }

private var _keyboardArrowDown: ImageVector? = null