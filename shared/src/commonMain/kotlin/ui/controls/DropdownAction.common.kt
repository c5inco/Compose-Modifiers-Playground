package ui.controls

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
internal expect fun DropdownAction(
    modifier: Modifier = Modifier,
    onSelect: (Any) -> Unit,
    onDismiss: () -> Unit,
    menuContent: @Composable (onSelect: (Any) -> Unit) -> Unit,
    content: @Composable (onClick: () -> Unit) -> Unit
)
