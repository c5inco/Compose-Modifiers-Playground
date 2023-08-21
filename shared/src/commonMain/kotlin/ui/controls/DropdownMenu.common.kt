package ui.controls

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Suppress("ModifierParameter")
@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit
) = DropdownMenuEx(expanded, onDismissRequest, modifier,alignment, offset, properties, content)

@Suppress("ModifierParameter")
@Composable
internal expect fun DropdownMenuEx(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    alignment: Alignment,
    offset: DpOffset,
    properties: PopupProperties,
    content: @Composable ColumnScope.() -> Unit
)

class PopupProperties(val focusable: Boolean)