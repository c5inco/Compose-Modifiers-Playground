package ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val AppIcons.Remove: ImageVector
    get() {
        if (_remove != null) {
            return _remove!!
        }
        _remove = materialIcon(name = "Outlined.Remove") {
            materialPath {
                moveTo(19.0f, 13.0f)
                horizontalLineTo(5.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(14.0f)
                verticalLineToRelative(2.0f)
                close()
            }
        }
        return _remove!!
    }

private var _remove: ImageVector? = null