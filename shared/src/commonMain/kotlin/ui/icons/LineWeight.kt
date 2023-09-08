package ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val AppIcons.LineWeight: ImageVector
    get() {
        if (_lineWeight != null) {
            return _lineWeight!!
        }
        _lineWeight = materialIcon(name = "Outlined.LineWeight") {
            materialPath {
                moveTo(3.0f, 17.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(-2.0f)
                lineTo(3.0f, 15.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(3.0f, 20.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(-1.0f)
                lineTo(3.0f, 19.0f)
                verticalLineToRelative(1.0f)
                close()
                moveTo(3.0f, 13.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(-3.0f)
                lineTo(3.0f, 10.0f)
                verticalLineToRelative(3.0f)
                close()
                moveTo(3.0f, 4.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(18.0f)
                lineTo(21.0f, 4.0f)
                lineTo(3.0f, 4.0f)
                close()
            }
        }
        return _lineWeight!!
    }

private var _lineWeight: ImageVector? = null
