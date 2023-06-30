package ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

val DropdownMinWidth = 200.dp
val DropdownMaxWidth = 400.dp

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
    val intOffset = with(LocalDensity.current) {
        IntOffset(x = offset.x.roundToPx(), y = offset.y.roundToPx())
    }

    // TODO: Implement JS version
    if (expanded) {
        Popup(
            alignment = alignment,
            offset = intOffset,
            onDismissRequest = onDismissRequest,
            focusable = properties.focusable,
        ) {
            Column(
                Modifier
                    .shadow(8.dp)
                    .sizeIn(maxWidth = DropdownMaxWidth)
                    .background(Color.White)
            ) {
                content()
            }
        }
    }
}
