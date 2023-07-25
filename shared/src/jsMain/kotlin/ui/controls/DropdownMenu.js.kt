package ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
    // TODO: Implement JS version
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
            Column(
                Modifier
                    .shadow(8.dp)
                    .background(Color.White)
                    .width(IntrinsicSize.Min)
            ) {
                content()
            }
        }
    }
}
