package com.c5inco.modifiers.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c5inco.modifiers.data.*

@Composable
fun ElementRow(
    model: ElementModel,
    onValueChange: (ElementModel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val element = model.type
    val elementData: Any = model.data

    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth(0.5f)
                .padding(end = 8.dp)
        ) {
            DropdownInput(
                items = AvailableElements.values().toList(),
                activeItem = element,
                onSelect = {
                    //expanded = false
                    var newElementData: Any = BoxElementData()

                    when (it) {
                        AvailableElements.Column -> newElementData = ColumnElementData()
                        AvailableElements.Row -> newElementData = RowElementData()
                    }

                    onValueChange(ElementModel(it, newElementData))
                }
            )
        }

        when (element) {
            AvailableElements.Box -> {
                val data = elementData as BoxElementData
                Column(Modifier.padding(vertical = 8.dp)) {
                    Text(
                        "contentAlignment",
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 12.sp,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                    Spacer(Modifier.height(4.dp))
                    ContentAlignmentInput(data.contentAlignment, onValueChange = { it ->
                        onValueChange(ElementModel(model.type, BoxElementData(it)))
                    })
                }
            }
            AvailableElements.Column -> {
                val data = elementData as ColumnElementData
                Row(Modifier.padding(vertical = 8.dp)) {
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Text(
                            "verticalArrangement",
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 12.sp,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        )
                        Spacer(Modifier.height(4.dp))
                        VerticalArrangementInput(
                            data.verticalArrangement,
                            data.verticalSpacing,
                            onValueChange = { arrangement, spacing ->
                                onValueChange(
                                    ElementModel(
                                        model.type,
                                        ColumnElementData(
                                            arrangement,
                                            spacing,
                                            data.horizontalAlignment
                                        )
                                    )
                                )
                            }
                        )
                    }
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Text(
                            "horizontalAlignment",
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 12.sp,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        )
                        Spacer(Modifier.height(4.dp))
                        HorizontalAlignmentInput(data.horizontalAlignment, onValueChange = {
                            onValueChange(
                                ElementModel(
                                    model.type,
                                    ColumnElementData(
                                        data.verticalArrangement,
                                        data.verticalSpacing,
                                        it
                                    )
                                )
                            )
                        })
                    }
                }
            }
            AvailableElements.Row -> {
                val data = elementData as RowElementData
                Row(Modifier.padding(vertical = 8.dp)) {
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Text(
                            "horizontalArrangement",
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 12.sp,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        )
                        Spacer(Modifier.height(4.dp))
                        HorizontalArrangementInput(
                            data.horizontalArrangement,
                            data.horizontalSpacing,
                            onValueChange = { arrangement, spacing ->
                                onValueChange(
                                    ElementModel(
                                        model.type,
                                        RowElementData(
                                            arrangement,
                                            spacing,
                                            data.verticalAlignment
                                        )
                                    )
                                )
                            }
                        )
                    }
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Text(
                            "verticalAlignment",
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 12.sp,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        )
                        Spacer(Modifier.height(4.dp))
                        VerticalAlignmentInput(data.verticalAlignment, onValueChange = {
                            onValueChange(
                                ElementModel(
                                    model.type,
                                    RowElementData(
                                        elementData.horizontalArrangement,
                                        elementData.horizontalSpacing,
                                        it
                                    )
                                )
                            )
                        })
                    }
                }
            }
        }
    }
}