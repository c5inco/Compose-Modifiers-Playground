package ui

import BaseElementData
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp

@Composable
fun ElementRow(elementValue: AvailableElements, widthValue: Int, heightValue: Int, onValueChange: (BaseElementData) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(Modifier.fillMaxWidth()) {
        Box {
            var hovered by remember { mutableStateOf(false) }

            Row(
                Modifier
                    .clickable { expanded = true }
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
                    .height(24.dp)
                    .width(120.dp)
                    .border(width = 1.dp, color = if (hovered) Color.LightGray else Color.Transparent)
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (hovered) Arrangement.SpaceBetween else Arrangement.Start
            ) {
                Text("$elementValue", style = MaterialTheme.typography.body2)
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Dropdown icon",
                    modifier = Modifier.size(18.dp)
                )
            }

            val select: (AvailableElements) -> Unit = {
                expanded = false
                onValueChange(BaseElementData(it, widthValue, heightValue))
            }

            DropdownMenu(
                expanded,
                onDismissRequest = { expanded = false }
            ) {
                AvailableElements.values().forEach {
                    DropdownMenuItem(onClick = { select(it) }) {
                        Text("$it")
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        DpInput(widthValue, onValueChange = {
            onValueChange(BaseElementData(elementValue, it, heightValue))
        })
        Spacer(Modifier.width(8.dp))
        DpInput(heightValue, onValueChange = {
            onValueChange(BaseElementData(elementValue, widthValue, it))
        })
    }
}

enum class AvailableElements {
    Box,
    Column,
    Row,
}