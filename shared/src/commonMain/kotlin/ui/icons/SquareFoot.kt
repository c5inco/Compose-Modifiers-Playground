package ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val AppIcons.SquareFoot: ImageVector
    get() {
        if (_squareFoot != null) {
            return _squareFoot!!
        }
        _squareFoot = materialIcon(name = "Outlined.SquareFoot") {
            materialPath {
                moveTo(17.66f, 17.66f)
                lineToRelative(-1.06f, 1.06f)
                lineToRelative(-0.71f, -0.71f)
                lineToRelative(1.06f, -1.06f)
                lineToRelative(-1.94f, -1.94f)
                lineToRelative(-1.06f, 1.06f)
                lineToRelative(-0.71f, -0.71f)
                lineToRelative(1.06f, -1.06f)
                lineToRelative(-1.94f, -1.94f)
                lineToRelative(-1.06f, 1.06f)
                lineToRelative(-0.71f, -0.71f)
                lineToRelative(1.06f, -1.06f)
                lineTo(9.7f, 9.7f)
                lineToRelative(-1.06f, 1.06f)
                lineToRelative(-0.71f, -0.71f)
                lineToRelative(1.06f, -1.06f)
                lineTo(7.05f, 7.05f)
                lineTo(5.99f, 8.11f)
                lineTo(5.28f, 7.4f)
                lineToRelative(1.06f, -1.06f)
                lineTo(4.0f, 4.0f)
                verticalLineToRelative(14.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(14.0f)
                lineTo(17.66f, 17.66f)
                close()
                moveTo(7.0f, 17.0f)
                verticalLineToRelative(-5.76f)
                lineTo(12.76f, 17.0f)
                horizontalLineTo(7.0f)
                close()
            }
        }
        return _squareFoot!!
    }

private var _squareFoot: ImageVector? = null
