package ui.controls

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
@Suppress("ModifierParameter")
internal actual fun DropdownMenuEx(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    alignment: Alignment,
    offset: DpOffset,
    properties: PopupProperties,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (expanded) {
        val popupPositionProvider = DesktopDropdownMenuPositionProvider(
            contentOffset = offset,
            density = LocalDensity.current
        ) { parentBounds, menuBounds ->
            // transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
        }

        Popup(
            popupPositionProvider = popupPositionProvider,
            onDismissRequest = onDismissRequest,
            focusable = properties.focusable,
        ) {
            Surface(
                color = Color.White,
                elevation = 8.dp,
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(
                    Modifier
                        .width(IntrinsicSize.Max)
                        .padding(vertical = 8.dp)
                        .then(modifier)
                ) {
                    content()
                }
            }
        }
    }
}
