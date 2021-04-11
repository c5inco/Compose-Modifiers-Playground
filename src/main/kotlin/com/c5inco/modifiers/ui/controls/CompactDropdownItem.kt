package com.c5inco.modifiers.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MenuDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp

@Composable
fun <T> CompactDropdownItem(entry: T, onClick: (T) -> Unit) {
    var hovered by remember { mutableStateOf(false) }
    Row(
        Modifier
            .clickable { onClick(entry) }
            .pointerMoveFilter(
                onEnter = {
                    hovered = true
                    false
                },
                onExit = {
                    hovered = false
                    false
                }
            )
            .background(if (hovered) LocalContentColor.current.copy(alpha = 0.1f) else Color.Transparent)
            .fillMaxWidth()
            .padding(MenuDefaults.DropdownMenuItemContentPadding)
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$entry")
    }
}