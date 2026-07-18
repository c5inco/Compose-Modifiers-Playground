package ui.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
actual fun DropdownAction(
    modifier: Modifier,
    onSelect: (Any) -> Unit,
    onDismiss: () -> Unit,
    menuContent: @Composable (onSelect: (Any) -> Unit) -> Unit,
    content: @Composable (onClick: () -> Unit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    // var size by remember { mutableStateOf(IntSize.Zero) }
    // var windowSize by remember { mutableStateOf(IntSize.Zero) }
    // var windowPosition by remember { mutableStateOf(Offset.Zero) }
    // var boundsInWindow by remember { mutableStateOf(Rect.Zero) }

    Box(
        // modifier = modifier.onGloballyPositioned {
        //     size = it.size
        //     windowPosition = it.positionInWindow()
        //     boundsInWindow = it.boundsInWindow()
        //     windowSize = it.findRootCoordinates().size
        // }
    ) {
        content { expanded = true }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            menuContent {
                onSelect(it)
                expanded = false
            }
        }
    }
}