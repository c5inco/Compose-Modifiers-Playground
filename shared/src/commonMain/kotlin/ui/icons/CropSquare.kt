package ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val AppIcons.CropSquare: ImageVector
    get() {
        if (_cropSquare != null) {
            return _cropSquare!!
        }
        _cropSquare = materialIcon(name = "Outlined.CropSquare") {
            materialPath {
                moveTo(18.0f, 4.0f)
                lineTo(6.0f, 4.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                verticalLineToRelative(12.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(12.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                lineTo(20.0f, 6.0f)
                curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(18.0f, 18.0f)
                lineTo(6.0f, 18.0f)
                lineTo(6.0f, 6.0f)
                horizontalLineToRelative(12.0f)
                verticalLineToRelative(12.0f)
                close()
            }
        }
        return _cropSquare!!
    }

private var _cropSquare: ImageVector? = null
