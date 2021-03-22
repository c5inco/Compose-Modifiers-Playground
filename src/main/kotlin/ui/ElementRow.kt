package ui

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
import androidx.compose.ui.unit.sp
import data.AvailableElements
import data.BoxElementData
import data.ColumnElementData
import data.RowElementData

@Composable
fun ElementRow(
    elementValue: Pair<AvailableElements, Any>,
    onValueChange: (Pair<AvailableElements, Any>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val element = elementValue.first
    val elementData: Any = elementValue.second

    Column(Modifier.fillMaxWidth()) {
        Row {
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
                    Text("$element", style = MaterialTheme.typography.body2)
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Dropdown icon",
                        modifier = Modifier.size(18.dp)
                    )
                }

                val select: (AvailableElements) -> Unit = {
                    expanded = false
                    var newElementData: Any = BoxElementData()

                    when (it) {
                        AvailableElements.Column -> newElementData = ColumnElementData()
                        AvailableElements.Row -> newElementData = RowElementData()
                    }

                    onValueChange(Pair(
                        it,
                        newElementData
                    ))
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
        }

        when (element) {
            AvailableElements.Box -> {
                val data = elementData as BoxElementData
                Row(
                    Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "contentAlignment",
                        fontSize = 12.sp,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                    Spacer(Modifier.weight(1f))
                    ContentAlignmentInput(data.contentAlignment, onValueChange = { it ->
                        onValueChange(Pair(elementValue.first, BoxElementData(it)))
                    })
                }
            }
            AvailableElements.Column -> {
                val data = elementData as ColumnElementData
                Column {
                    Row(
                        Modifier.padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "verticalArrangement",
                            fontSize = 12.sp,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        )
                        Spacer(Modifier.weight(1f))
                        VerticalArrangementInput(onValueChange = { arrangement, spacing ->
                            onValueChange(Pair(elementValue.first, ColumnElementData(arrangement, spacing, data.horizontalAlignment)))
                        })
                    }
                    Row(
                        Modifier.padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("horizontalAlignment", fontSize = 12.sp, color = LocalContentColor.current.copy(alpha = ContentAlpha.medium))
                        Spacer(Modifier.weight(1f))
                        HorizontalAlignmentInput(onValueChange = {
                            onValueChange(Pair(elementValue.first, ColumnElementData(elementData.verticalArrangement, elementData.verticalSpacing, it)))
                        })
                    }
                }
            }
            AvailableElements.Row -> {
                val data = elementData as RowElementData
                Column {
                    Row(
                        Modifier.padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "horizontalArrangement",
                            fontSize = 12.sp,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        )
                        Spacer(Modifier.weight(1f))
                        HorizontalArrangementInput(onValueChange = { arrangement, spacing ->
                            onValueChange(Pair(elementValue.first, RowElementData(arrangement, spacing, data.verticalAlignment)))
                        })
                    }
                    Row(
                        Modifier.padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("verticalAlignment", fontSize = 12.sp, color = LocalContentColor.current.copy(alpha = ContentAlpha.medium))
                        Spacer(Modifier.weight(1f))
                        VerticalAlignmentInput(onValueChange = {
                            onValueChange(Pair(elementValue.first, RowElementData(elementData.horizontalArrangement, elementData.horizontalSpacing, it)))
                        })
                    }
                }
            }
        }
    }
}