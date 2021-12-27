@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package com.c5inco.modifiers.ui.controls

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.c5inco.modifiers.data.*
import com.c5inco.modifiers.utils.chunk
import java.awt.Cursor
import kotlin.math.absoluteValue

@Composable
fun ColorInput(colorValue: Color, onValueChange: (Color) -> Unit) {
    val availableColors = listOf(
        Color.Black,
        Color.Gray,
        Color.White,
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
        Color.Magenta,
        Color.Transparent,
    )

    var expanded by remember { mutableStateOf(false) }
    val swatchSize = 24
    val horizontalPadding = 6

    Box() {
        ColorSwatch(swatchSize, colorValue, onClick = { expanded = true })
        DropdownMenu(
            modifier = Modifier.padding(horizontal = horizontalPadding.dp),
            expanded = expanded,
            offset = DpOffset(x = (-horizontalPadding).dp, 4.dp),
            onDismissRequest = { expanded = false }
        ) {
            val chunkSize = 4
            val spacerSize = 4

            Column(
                Modifier.width((swatchSize * chunkSize + spacerSize * (chunkSize - 1)).dp),
                verticalArrangement = Arrangement.spacedBy(spacerSize.dp)
            ) {
                val chunkedColors = chunk(availableColors, chunkSize)
                chunkedColors.forEach { colors ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(spacerSize.dp)
                    ) {
                        colors.forEach {
                            ColorSwatch(swatchSize, it, onClick = {
                                onValueChange(it)
                                expanded = false
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorSwatch(
    swatchSize: Int,
    colorValue: Color,
    onClick: () -> Unit
) {
    Box(Modifier
        .size(swatchSize.dp)
        .clip(CircleShape)
        .background(colorValue)
        .border(width = 1.dp, color = Color.Black.copy(alpha = 0.25f), shape = CircleShape)
        .clickable { onClick() }
    ) {
        if (colorValue == Color.Transparent) {
            TransparentBackground()
        }
    }
}

@Composable
private fun TransparentBackground() {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawLine(
            start = Offset(x = canvasWidth, y = 0f),
            end = Offset(x = 0f, y = canvasHeight),
            color = Color.Red.copy(alpha = 0.7f),
            strokeWidth = 3f
        )
    }
}

@Composable
fun ShapeInput(
    shapeValue: AvailableShapes,
    cornerValue: Int,
    onValueChange: (shape: AvailableShapes, corner: Int) -> Unit,
) {
    val shapesList = listOf(
        Pair("rectangle", AvailableShapes.Rectangle),
        Pair("circle", AvailableShapes.Circle),
        Pair("rounded-corner", AvailableShapes.RoundedCorner),
        Pair("cut-corner", AvailableShapes.CutCorner),
    )
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()

    Row(
        modifier = Modifier
            .hoverable(interactionSource)
            .border(
                width = 1.dp,
                color = if (hovered) LocalContentColor.current.copy(alpha = ContentAlpha.disabled) else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        for (pair in shapesList) {
            var mod: Modifier = Modifier
            val active = shapeValue == pair.second
            if (active) mod = mod.background(MaterialTheme.colors.secondary, RoundedCornerShape(4.dp))

            SmallIconButton(
                modifier = mod,
                onClick = { onValueChange(pair.second, cornerValue) }
            ) {
                Icon(
                    painter = painterResource("icons/${pair.first}.svg"),
                    contentDescription = "${pair.first} shape button",
                    modifier = Modifier.size(18.dp),
                    tint = if (active) contentColorFor(MaterialTheme.colors.secondary) else LocalContentColor.current
                )
            }
        }
    }

    if (shapeValue == AvailableShapes.RoundedCorner || shapeValue == AvailableShapes.CutCorner) {
        DpInput(
            cornerValue,
            label = {
                Icon(
                    imageVector = Icons.Outlined.RoundedCorner,
                    contentDescription = "Corner icon",
                    tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                    modifier = Modifier.size(18.dp)
                )
            },
            onValueChange = {
                onValueChange(shapeValue, it)
            }
        )
    }
}

@Composable
fun RowScope.PaddingInput(
    typeValue: AvailablePadding,
    cornerValues: CornerValues,
    onValueChange: (type: AvailablePadding, corners: CornerValues) -> Unit,
) {
    val shapesList = listOf(
        Pair(Icons.Outlined.CropSquare, AvailablePadding.All),
        Pair(Icons.Outlined.GridGoldenratio, AvailablePadding.Sides),
        Pair(Icons.Outlined.Fullscreen, AvailablePadding.Individual),
    )

    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()

    Row(
        modifier = Modifier
            .hoverable(interactionSource)
            .border(
                width = 1.dp,
                color = if (hovered) LocalContentColor.current.copy(alpha = ContentAlpha.disabled) else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        for (pair in shapesList) {
            var mod: Modifier = Modifier
            val active = typeValue == pair.second
            if (active) mod =
                mod.background(MaterialTheme.colors.secondary, RoundedCornerShape(4.dp))

            SmallIconButton(
                modifier = mod,
                onClick = { onValueChange(pair.second, cornerValues) }
            ) {
                Icon(
                    imageVector = pair.first,
                    contentDescription = "${pair.first} icon",
                    modifier = Modifier.size(18.dp),
                    tint = if (active) contentColorFor(MaterialTheme.colors.secondary) else LocalContentColor.current
                )
            }
        }
    }

    Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when (typeValue) {
            AvailablePadding.All -> {
                DpInput(
                    cornerValues.top,
                    label = {
                        Text(
                            "all",
                            style = MaterialTheme.typography.body2,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                        )
                    },
                    onValueChange = {
                        onValueChange(typeValue, CornerValues(it))
                    }
                )
            }
            AvailablePadding.Sides -> {
                DpInput(
                    cornerValues.start,
                    label = {
                        Text(
                            "H",
                            style = MaterialTheme.typography.body2,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                        )
                    },
                    onValueChange = {
                        onValueChange(typeValue, CornerValues(horizontal = it, vertical = cornerValues.top))
                    }
                )
                DpInput(
                    cornerValues.top,
                    label = {
                        Text(
                            "V",
                            style = MaterialTheme.typography.body2,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                        )
                    },
                    onValueChange = {
                        onValueChange(typeValue, CornerValues(horizontal = cornerValues.start, vertical = it))
                    }
                )
            }
            AvailablePadding.Individual -> {
                DpInput(
                    cornerValues.start,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        onValueChange(typeValue, cornerValues.copy(start = it))
                    }
                )
                DpInput(
                    cornerValues.top,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        onValueChange(typeValue, cornerValues.copy(top = it))
                    }
                )
                DpInput(
                    cornerValues.end,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        onValueChange(typeValue, cornerValues.copy(end = it))
                    }
                )
                DpInput(
                    cornerValues.bottom,
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                        onValueChange(typeValue, cornerValues.copy(bottom = it))
                    }
                )
            }
        }
    }
}

@Composable
fun HorizontalArrangementInput(
    arrangementValue: AvailableHorizontalArrangements = AvailableHorizontalArrangements.Start,
    spacingValue: Int = 0,
    onValueChange: (arrangement: AvailableHorizontalArrangements, spacing: Int) -> Unit,
) {
    val spacedBy = arrangementValue == AvailableHorizontalArrangements.SpacedBy
    ArrangementInput(
        spacedBy,
        AvailableHorizontalArrangements.values().toList(),
        arrangementValue,
        spacingValue,
        onArrangementChange = { arr, sp ->
            onValueChange(arr, sp)
        }
    )
}

@Composable
fun VerticalArrangementInput(
    arrangementValue: AvailableVerticalArrangements = AvailableVerticalArrangements.Top,
    spacingValue: Int = 0,
    onValueChange: (arrangement: AvailableVerticalArrangements, spacing: Int) -> Unit,
) {
    val spacedBy = arrangementValue == AvailableVerticalArrangements.SpacedBy
    ArrangementInput(
        spacedBy,
        AvailableVerticalArrangements.values().toList(),
        arrangementValue,
        spacingValue,
        onArrangementChange = { arr, sp ->
            onValueChange(arr, sp)
        }
    )
}

@Composable
private fun <T> ArrangementInput(
    spacedBy: Boolean,
    arrangements: List<T>,
    arrangementValue: T,
    spacingValue: Int,
    onArrangementChange: (arrangement: T, spacing: Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DropdownInput(
            modifier = if (spacedBy) Modifier.weight(1f) else Modifier.fillMaxWidth().padding(end = 8.dp),
            items = arrangements,
            activeItem = arrangementValue,
            onSelect = {
                onArrangementChange(it, spacingValue)
            }
        )
        if (spacedBy) {
            Spacer(Modifier.width(8.dp))
            DpInput(
                spacingValue,
                modifier = Modifier.padding(end = 8.dp),
                onValueChange = {
                    onArrangementChange(arrangementValue, it)
                }
            )
        }
    }
}

@Composable
fun HorizontalAlignmentInput(
    alignmentValue: AvailableHorizontalAlignments = AvailableHorizontalAlignments.Start,
    onValueChange: (alignment: AvailableHorizontalAlignments) -> Unit,
) {
    DropdownInput(
        items = AvailableHorizontalAlignments.values().toList(),
        activeItem = alignmentValue,
        onSelect = {
            onValueChange(it)
        }
    )
}

@Composable
fun VerticalAlignmentInput(
    alignmentValue: AvailableVerticalAlignments = AvailableVerticalAlignments.Top,
    onValueChange: (alignment: AvailableVerticalAlignments) -> Unit,
) {
    DropdownInput(
        items = AvailableVerticalAlignments.values().toList(),
        activeItem = alignmentValue,
        onSelect = {
            onValueChange(it)
        }
    )
}

@Composable
fun ContentAlignmentInput(
    alignmentValue: AvailableContentAlignments = AvailableContentAlignments.TopStart,
    onValueChange: (alignment: AvailableContentAlignments) -> Unit,
) {
    DropdownInput(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(end = 8.dp),
        items = AvailableContentAlignments.values().toList(),
        activeItem = alignmentValue,
        onSelect = {
            onValueChange(it)
        }
    )
}

@Composable
fun DpInput(
    value: Int,
    canBeNegative: Boolean = false,
    modifier: Modifier = Modifier.width(54.dp),
    label: @Composable () -> Unit = {},
    onValueChange: (Int) -> Unit
) {
    TextInput(
        modifier = modifier,
        value = value,
        label = label,
        convert = {
            it.toIntOrNull()
        },
        onValueChange = {
            if (it != null) {
                onValueChange(if (canBeNegative) it else it.absoluteValue)
            } else {
                onValueChange(value)
            }
        }
    )
}

@Composable
fun FloatInput(
    value: Float,
    modifier: Modifier = Modifier.width(60.dp),
    label: @Composable () -> Unit = {},
    onValueChange: (Float) -> Unit
) {
    TextInput(
        modifier = modifier,
        value = value,
        label = label,
        convert = {
            it.toFloatOrNull()
        },
        onValueChange = {
            if (it != value) {
                onValueChange(it ?: value)
            }
        }
    )
}

@Composable
fun <T> TextInput(
    modifier: Modifier = Modifier,
    value: T,
    label: @Composable () -> Unit = {},
    convert: (String) -> T,
    onValueChange: (T) -> Unit
) {
    var text by remember(value) { mutableStateOf(value.toString()) }
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    var focused by remember { mutableStateOf(false) }

    @Composable
    fun getBorderColor(): Color {
        if (focused) return MaterialTheme.colors.primary
        if (hovered) return LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
        return Color.Transparent
    }

    fun saveText() {
        val convertedValue: T = convert(text)
        if (convertedValue != null) {
            onValueChange(convertedValue)
        } else {
            text = value.toString()
            onValueChange(value)
        }
    }

    BasicTextField(
        value = text,
        onValueChange = { text = it },
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .border(width = 1.dp, color = getBorderColor())
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                label()
                innerTextField()
            }
        },
        modifier = modifier
            .onPreviewKeyEvent {
                when(it.key) {
                    Key.Enter,
                    Key.NumPadEnter -> {
                        saveText()
                        true
                    }
                    Key.Escape -> {
                        text = value.toString()
                        true
                    }
                    else -> false
                }
            }
            .onFocusChanged {
                focused = it.isFocused
                if (!focused) {
                    saveText()
                }
            }
            .hoverable(interactionSource)
            .height(24.dp)
        ,
        textStyle = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onSurface
        ),
        cursorBrush = SolidColor(MaterialTheme.colors.onSurface)
    )
}

@Composable
fun <T> DropdownInput(
    modifier: Modifier = Modifier.fillMaxWidth(),
    items: List<T>,
    shape: Shape = RectangleShape,
    hoverBackgroundColor: Color = Color.Transparent,
    hoverBorderColor: Color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
    activeItem: T,
    onSelect: (T) -> Unit
) {
    Box {
        val interactionSource = remember { MutableInteractionSource() }
        val hovered by interactionSource.collectIsHoveredAsState()
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier
                .clip(shape)
                .clickable { expanded = true }
                .hoverable(interactionSource)
                .then(if (hovered) Modifier.background(hoverBackgroundColor) else Modifier)
                .height(24.dp)
                .border(width = 1.dp, color = if (hovered) hoverBorderColor else Color.Transparent)
                .padding(start = 8.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (hovered) Arrangement.SpaceBetween else Arrangement.Start
        ) {
            Text("$activeItem", style = MaterialTheme.typography.body2)
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Dropdown icon",
                modifier = Modifier.size(18.dp)
            )
        }

        DropdownMenu(
            expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach {
                CompactDropdownItem(it, onClick = { item ->
                    onSelect(item)
                    expanded = false
                })
            }
        }
    }
}

private fun Modifier.cursorForHorizontalResize(isHorizontal: Boolean): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(if (isHorizontal) Cursor.E_RESIZE_CURSOR else Cursor.S_RESIZE_CURSOR)))