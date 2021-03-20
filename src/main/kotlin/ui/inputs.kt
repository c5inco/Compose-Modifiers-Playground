package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import data.AvailableShapes
import utils.chunk

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
        Box(
            Modifier
                .size(swatchSize.dp)
                .clip(CircleShape)
                .background(colorValue)
                .border(width = 1.dp, color = Color.Black.copy(alpha = 0.25f), shape = CircleShape)
                .clickable { expanded = true }
        )
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
                            Box(Modifier
                                    .size(swatchSize.dp)
                                    .clip(CircleShape)
                                    .background(it)
                                    .border(width = 1.dp, color = Color.Black.copy(alpha = 0.25f), shape = CircleShape)
                                    .clickable {
                                        onValueChange(it)
                                        expanded = false
                                    }
                            ) {
                                if (it == Color.Transparent) {
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
                            }
                        }
                    }
                }
            }
        }
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
    var hovered = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .pointerMoveFilter(
                onEnter = {
                    hovered.value = true
                    false
                },
                onExit = {
                    hovered.value = false
                    false
                }
            )
            .border(
                width = 1.dp,
                color = if (hovered.value) Color.LightGray else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        for (pair in shapesList) {
            var mod = Modifier.clip(shape = RoundedCornerShape(4.dp))
            if (shapeValue == pair.second) mod = mod.background(MaterialTheme.colors.secondary)

            Icon(
                painter = svgResource("icons/${pair.first}.svg"),
                contentDescription = "${pair.first} shape button",
                modifier = mod.clickable {
                    onValueChange(pair.second, cornerValue)
                }
            )
        }
    }
    Spacer(Modifier.width(8.dp))
    DpInput(cornerValue, onValueChange = {
        onValueChange(shapeValue, it)
    })
}

@Composable
fun DpInput(value: Int, onValueChange: (Int) -> Unit) {
    var hasError by remember { mutableStateOf(false) }

    BasicTextField(
        value = if (value == 0) "" else value.toString(),
        onValueChange = {
            if (it.isBlank()) {
                onValueChange(0)
                hasError = false
            } else {
                val convertedValue = it.toIntOrNull()
                if (convertedValue != null) {
                    onValueChange(convertedValue)
                    hasError = false
                } else {
                    onValueChange(0)
                    hasError = true
                }
            }
        },
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .border(width = 1.dp, color = if (hasError) MaterialTheme.colors.error else Color.LightGray)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    innerTextField()
                    if (value == 0) Text("0", color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled))
                }
            }
        },
        modifier = Modifier.width(40.dp),
        textStyle = MaterialTheme.typography.body2
    )
}