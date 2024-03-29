package ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MenuDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> CompactDropdownItem(
    modifier: Modifier = Modifier,
    entry: T,
    onClick: (T) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()

    Row(
        modifier
            .clickable { onClick(entry) }
            .hoverable(interactionSource)
            .fillMaxWidth()
            .height(32.dp)
            .background(if (hovered) LocalContentColor.current.copy(alpha = 0.1f) else Color.Transparent)
            .padding(MenuDefaults.DropdownMenuItemContentPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$entry",
            style = MaterialTheme.typography.body2,
        )
    }
}