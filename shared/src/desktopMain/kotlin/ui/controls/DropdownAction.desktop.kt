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

    Box(
        modifier = modifier
    ) {
        content { expanded = true }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuContent {
                onSelect(it)
                expanded = false
            }
        }
    }
}