package ui.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import utils.toDp

@Composable
actual fun DropdownAction(
    modifier: Modifier,
    onSelect: (Any) -> Unit,
    onDismiss: () -> Unit,
    menuContent: @Composable (onSelect: (Any) -> Unit) -> Unit,
    content: @Composable (onClick: () -> Unit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    var windowSize by remember { mutableStateOf(IntSize.Zero) }
    var windowPosition by remember { mutableStateOf(Offset.Zero) }
    var boundsInWindow by remember { mutableStateOf(Rect.Zero) }

    Box(
        modifier = modifier.onGloballyPositioned {
            size = it.size
            windowPosition = it.positionInWindow()
            boundsInWindow = it.boundsInWindow()
            windowSize = it.findRootCoordinates().size
        }
    ) {
        content { expanded = true }

        DropdownMenu(
            alignment = if (boundsInWindow.toDp().left < windowSize.toDp().width - DropdownMinWidth - 8.dp) {
                Alignment.TopStart
            } else {
                Alignment.TopEnd
            },
            offset = DpOffset(x = 0.dp, y = with (LocalDensity.current) { size.height.toDp() }),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Column {
                // Text("pos: ${windowPosition.toDp()}")
                // Text("bounds: ${boundsInWindow.toDp()}")
                // Text("window: ${windowSize.toDp()}")
                menuContent {
                    onSelect(it)
                    expanded = false
                }
            }
        }
    }
}