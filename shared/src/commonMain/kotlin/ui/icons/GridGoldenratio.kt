package ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val AppIcons.GridGoldenratio: ImageVector
    get() {
        if (_gridGoldenratio != null) {
            return _gridGoldenratio!!
        }
        _gridGoldenratio = materialIcon(name = "Outlined.GridGoldenratio") {
            materialPath {
                moveTo(22.0f, 11.0f)
                verticalLineTo(9.0f)
                horizontalLineToRelative(-7.0f)
                verticalLineTo(2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(7.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineTo(2.0f)
                horizontalLineTo(9.0f)
                verticalLineToRelative(7.0f)
                horizontalLineTo(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(7.0f)
                verticalLineToRelative(2.0f)
                horizontalLineTo(2.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(7.0f)
                verticalLineToRelative(7.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-7.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(7.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-7.0f)
                horizontalLineToRelative(7.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-7.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineTo(22.0f)
                close()
                moveTo(13.0f, 13.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(13.0f)
                close()
            }
        }
        return _gridGoldenratio!!
    }

private var _gridGoldenratio: ImageVector? = null
