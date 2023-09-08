package ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val AppIcons.Add: ImageVector
    get() {
        if (_add != null) {
            return _add!!
        }
        _add = materialIcon(name = "Outlined.Add") {
            materialPath {
                moveTo(19.0f, 13.0f)
                horizontalLineToRelative(-6.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-6.0f)
                horizontalLineTo(5.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(6.0f)
                verticalLineTo(5.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(2.0f)
                close()
            }
        }
        return _add!!
    }

private var _add: ImageVector? = null