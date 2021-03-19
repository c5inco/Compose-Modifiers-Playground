
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun main() = Window(
    title = "Modifiers Playground",
    size = IntSize(width = 850, height = 700)
) {
    val defaultModifiers = listOf(
        Pair(Modifier.size(400.dp), SizeModifierData(400)),
        Pair(Modifier.background(Color.Magenta), BackgroundModifierData(color = Color.Magenta)),
        Pair(Modifier.padding(20.dp), PaddingModifierData(20)),
        Pair(Modifier.background(Color.Gray), BackgroundModifierData(color = Color.Gray)),
        Pair(Modifier, ShadowModifierData()),
        Pair(Modifier, BorderModifierData(color = Color.Blue)),
    )

    var modifiersList = remember {
        defaultModifiers.toMutableStateList()
    }

    MaterialTheme {
        Row {
            Surface(
                modifier = Modifier.weight(2f),
                color = Color(0xffe5e5e5)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = buildModifiers(modifiersList),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🥑", fontSize = 48.sp)
                    }
                }
            }
            Surface(Modifier.weight(1f)) {
                val move: (index: Int, up: Boolean) -> Unit = { index, up ->
                    val curr = modifiersList[index]
                    val targetIndex = if (up) index - 1 else index + 1

                    val prev = modifiersList.getOrNull(targetIndex)

                    if (prev != null) {
                        modifiersList.set(targetIndex, curr)
                        modifiersList.set(index, prev)
                    }
                }

                Column {
                    for (i in 0 until modifiersList.size) {
                        val m = modifiersList[i]

                        Row(Modifier.padding(4.dp)) {
                            Row(Modifier.weight(1f)) {
                                when (m.second) {
                                    is SizeModifierData -> {
                                        val data = m.second as SizeModifierData
                                        SizeModifier(
                                            sizeValue = data.size,
                                            onChange = { size ->
                                                modifiersList.set(i, Pair(Modifier.size(size.dp), SizeModifierData(size)))
                                            }
                                        )
                                    }
                                    is BackgroundModifierData -> {
                                        val data = m.second as BackgroundModifierData
                                        BackgroundModifier(
                                            colorValue = data.color,
                                            shapeValue = data.shape,
                                            cornerValue = data.corner,
                                            onChange = {
                                                modifiersList.set(i,
                                                    Pair(Modifier.background(
                                                        color = it.color,
                                                        shape = getShape(it.shape, it.corner)
                                                    ),
                                                    it.copy())
                                                )
                                            }
                                        )
                                    }
                                    is BorderModifierData -> {
                                        val data = m.second as BorderModifierData
                                        BorderModifier(
                                            widthValue = data.width,
                                            colorValue = data.color,
                                            shapeValue = data.shape,
                                            cornerValue = data.corner,
                                            onChange = {
                                                modifiersList.set(i,
                                                    Pair(Modifier.border(
                                                        width = (it.width).dp,
                                                        color = it.color,
                                                        shape = getShape(it.shape, it.corner)
                                                    ),
                                                    it.copy())
                                                )
                                            }
                                        )
                                    }
                                    is PaddingModifierData -> {
                                        val data = m.second as PaddingModifierData
                                        PaddingModifier(
                                            allValue = data.all,
                                            onChange = { all ->
                                                modifiersList.set(i, Pair(Modifier.padding(all.dp), PaddingModifierData(all)))
                                            }
                                        )
                                    }
                                    is ShadowModifierData -> {
                                        val data = m.second as ShadowModifierData
                                        ShadowModifier(
                                            elevationValue = data.elevation,
                                            shapeValue = data.shape,
                                            cornerValue = data.corner,
                                            onChange = {
                                                modifiersList.set(i,
                                                    Pair(Modifier.shadow(
                                                        elevation = it.elevation.dp,
                                                        shape = getShape(it.shape, it.corner)
                                                    ),
                                                    it.copy())
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Move modifier up",
                                    tint = LocalContentColor.current.copy(alpha = if (i != 0) 1f else ContentAlpha.disabled),
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable(enabled = i != 0) {
                                            move(i, true)
                                        })
                                Spacer(Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Move modifier down",
                                    tint = LocalContentColor.current.copy(alpha = if (i < modifiersList.size - 1) 1f else ContentAlpha.disabled),
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable(enabled = i < modifiersList.size - 1) {
                                            move(i, false)
                                        }
                                )
                            }
                        }
                        Divider()
                    }

                    Button(onClick = {
                        modifiersList.clear()
                        modifiersList.addAll(defaultModifiers)
                    }) {
                        Text("Reset")
                    }
                }
            }
        }
    }
}

fun getShape(shape: AvailableShapes, corner: Int): Shape {
    var realShape: Shape = RectangleShape

    when (shape) {
        AvailableShapes.Circle -> realShape = CircleShape
        AvailableShapes.RoundedCorner -> realShape = RoundedCornerShape(size = corner.dp)
        AvailableShapes.CutCorner -> realShape = CutCornerShape(size = corner.dp)
    }

    return realShape
}

data class SizeModifierData(
    val size: Int = 0
)

enum class AvailableShapes {
    Circle,
    RoundedCorner,
    CutCorner,
    Rectangle
}

data class BackgroundModifierData(
    val color: Color,
    val shape: AvailableShapes = AvailableShapes.Rectangle,
    val corner: Int = 0
)

data class BorderModifierData(
    val width: Int = 0,
    val color: Color,
    val shape: AvailableShapes = AvailableShapes.Rectangle,
    val corner: Int = 0
)

data class PaddingModifierData(
    val all: Int = 0
)

data class ShadowModifierData(
    val elevation: Int = 0,
    val shape: AvailableShapes = AvailableShapes.Rectangle,
    val corner: Int = 0
)

@Composable
fun ShadowModifier(elevationValue: Int, shapeValue: AvailableShapes, cornerValue: Int, onChange: (ShadowModifierData) -> Unit) {
    Column {
        Text("Shadow", style = MaterialTheme.typography.overline)
        Row {
            DpInput(elevationValue, onValueChange = {
                onChange(ShadowModifierData(it, shapeValue, cornerValue))
            })
            Spacer(Modifier.width(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Rect",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(ShadowModifierData(elevationValue, AvailableShapes.Rectangle, cornerValue)) }
                )
                Text(
                    text = "Circle",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(ShadowModifierData(elevationValue, AvailableShapes.Circle, cornerValue)) }
                )
                Text(
                    text = "Round",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(ShadowModifierData(elevationValue, AvailableShapes.RoundedCorner, cornerValue)) }
                )
                Text(
                    text = "Cut",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(ShadowModifierData(elevationValue, AvailableShapes.CutCorner, cornerValue)) }
                )
            }
            Spacer(Modifier.width(8.dp))
            DpInput(cornerValue, onValueChange = {
                onChange(ShadowModifierData(elevationValue, shapeValue, it))
            })
        }
    }
}

@Composable
fun SizeModifier(sizeValue: Int, onChange: (Int) -> Unit) {
    Column {
        Text("Size", style = MaterialTheme.typography.overline)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DpInput(sizeValue, onValueChange = {
                onChange(it)
            })
        }
    }
}

@Composable
fun BackgroundModifier(colorValue: Color, shapeValue: AvailableShapes, cornerValue: Int, onChange: (BackgroundModifierData) -> Unit) {
    Column {
        Text("Background", style = MaterialTheme.typography.overline)
        Row {
            Box(Modifier
                .size(20.dp)
                .background(colorValue)
            )
            Spacer(Modifier.width(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Rect",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(BackgroundModifierData(colorValue, AvailableShapes.Rectangle, cornerValue)) }
                )
                Text(
                    text = "Circle",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(BackgroundModifierData(colorValue, AvailableShapes.Circle, cornerValue)) }
                )
                Text(
                    text = "Round",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(BackgroundModifierData(colorValue, AvailableShapes.RoundedCorner, cornerValue)) }
                )
                Text(
                    text = "Cut",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(BackgroundModifierData(colorValue, AvailableShapes.CutCorner, cornerValue)) }
                )
            }
            Spacer(Modifier.width(8.dp))
            DpInput(cornerValue, onValueChange = {
                onChange(BackgroundModifierData(colorValue, shapeValue, it))
            })
        }
    }
}

@Composable
fun BorderModifier(widthValue: Int, colorValue: Color, shapeValue: AvailableShapes, cornerValue: Int, onChange: (BorderModifierData) -> Unit) {
    Column {
        Text("Border", style = MaterialTheme.typography.overline)
        Row {
            DpInput(widthValue, onValueChange = {
                onChange(BorderModifierData(it, colorValue, shapeValue, cornerValue))
            })
            Spacer(Modifier.width(16.dp))
            Box(Modifier
                .size(20.dp)
                .background(colorValue)
            )
            Spacer(Modifier.width(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Rect",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(BorderModifierData(widthValue, colorValue, AvailableShapes.Rectangle, cornerValue)) }
                )
                Text(
                    text = "Circle",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(BorderModifierData(widthValue, colorValue, AvailableShapes.Circle, cornerValue)) }
                )
                Text(
                    text = "Round",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(BorderModifierData(widthValue, colorValue, AvailableShapes.RoundedCorner, cornerValue)) }
                )
                Text(
                    text = "Cut",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { onChange(BorderModifierData(widthValue, colorValue, AvailableShapes.CutCorner, cornerValue)) }
                )
            }
            Spacer(Modifier.width(8.dp))
            DpInput(cornerValue, onValueChange = {
                onChange(BorderModifierData(widthValue, colorValue, shapeValue, it))
            })
        }
    }
}

@Composable
fun PaddingModifier(allValue: Int, onChange: (Int) -> Unit) {
    Column {
        Text("Padding", style = MaterialTheme.typography.overline)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DpInput(allValue, onValueChange = {
                onChange(it)
            })
        }
    }
}

fun buildModifiers(modifiersList: SnapshotStateList<Pair<Modifier, Any>>): Modifier {
    var modifier: Modifier = Modifier

    modifiersList.forEach {
        modifier = modifier.then(it.first)
    }

    return modifier
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
