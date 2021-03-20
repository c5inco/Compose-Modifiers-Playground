
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.*
import ui.ColorInput
import ui.DpInput
import ui.ModifierEntry
import ui.ShapeInput

fun main() = Window(
    title = "Modifiers Playground",
    size = IntSize(width = 900, height = 750)
) {
    val defaultModifiers = listOf(
        Triple(Modifier.size(400.dp), SizeModifierData(400), true),
        Triple(Modifier.background(Color.Magenta), BackgroundModifierData(color = Color.Magenta), true),
        Triple(Modifier.padding(20.dp), PaddingModifierData(20), true),
        Triple(Modifier.background(Color.Gray), BackgroundModifierData(color = Color.Gray), true),
        Triple(Modifier, ShadowModifierData(), true),
        Triple(Modifier, BorderModifierData(color = Color.Blue), true),
    )

    var modifiersList = remember {
        defaultModifiers.toMutableStateList()
    }

    MaterialTheme {
        Row {
            Surface(
                modifier = Modifier.weight(1f),
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
            Surface(
                Modifier.width(350.dp)
            ) {
                Column {
                    PropertiesSection(name = "Base element") {
                        Text("put selection here")
                    }
                    Divider(Modifier.height(8.dp))
                    PropertiesSection(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxHeight(),
                        name = "Modifiers",
                        actions = {
                            AddModifierAction(
                                onSelect = {
                                    val newModifier = getModifier(it)
                                    modifiersList.add(Triple(newModifier.first, newModifier.second, true))
                                })
                        }
                    ) {
                        for (i in 0 until modifiersList.size) {
                            ModifierEntry(
                                modifierData = modifiersList[i],
                                order = i,
                                size = modifiersList.size,
                                move = { index, up ->
                                    val curr = modifiersList[index]
                                    val targetIndex = if (up) index - 1 else index + 1

                                    val prev = modifiersList.getOrNull(targetIndex)

                                    if (prev != null) {
                                        modifiersList.set(targetIndex, curr)
                                        modifiersList.set(index, prev)
                                    }
                                },
                                onModifierChange = { order, data ->
                                    modifiersList.set(order, data)
                                },
                                onRemove = { order ->
                                    modifiersList.removeAt(order)
                                }
                            )
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
}

private fun getModifier(modifierType: ModifierEntry): Pair<Modifier, Any> {
    var newModifier: Pair<Modifier, Any> = Pair(Modifier.size(0.dp), SizeModifierData())

    when (modifierType) {
        ModifierEntry.Padding -> {
            newModifier = Pair(Modifier.padding(0.dp), PaddingModifierData())
        }
        ModifierEntry.Background -> {
            newModifier = Pair(Modifier.background(Color.Yellow), BackgroundModifierData())
        }
        ModifierEntry.Border -> {
            newModifier = Pair(Modifier.border(width = 10.dp, color = Color.Cyan), BorderModifierData(width = 10))
        }
        ModifierEntry.Shadow -> {
            newModifier = Pair(Modifier, ShadowModifierData())
        }
        ModifierEntry.Offset -> {
            newModifier = Pair(Modifier.offset(), OffsetDesignModifierData())
        }
    }

    return newModifier
}

enum class ModifierEntry {
    Size,
    Padding,
    Border,
    Background,
    Shadow,
    Offset
}

@Composable
private fun AddModifierAction(onSelect: (ModifierEntry) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Add modifier",
            modifier = Modifier
                .size(18.dp)
                .clickable {
                    expanded = true
                }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val select: (ModifierEntry) -> Unit = {
                onSelect(it)
                expanded = false
            }

            DropdownMenuItem(onClick = { select(ModifierEntry.Size) }) {
                Text("Size")
            }
            DropdownMenuItem(onClick = { select(ModifierEntry.Padding) }) {
                Text("Padding")
            }
            DropdownMenuItem(onClick = { select(ModifierEntry.Border) }) {
                Text("Border")
            }
            DropdownMenuItem(onClick = { select(ModifierEntry.Background) }) {
                Text("Background")
            }
            DropdownMenuItem(onClick = { select(ModifierEntry.Shadow) }) {
                Text("Shadow")
            }
            DropdownMenuItem(onClick = { select(ModifierEntry.Offset) }) {
                Text("Offset")
            }
        }
    }
}

@Composable
fun PropertiesSection(
    modifier: Modifier = Modifier.padding(16.dp),
    name: String = "<section>",
    actions: @Composable () -> Unit = { },
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle2,
            )
            actions()
        }
        Column(modifier) {
            content()
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

@Composable
fun ShadowModifier(elevationValue: Int, shapeValue: AvailableShapes, cornerValue: Int, onChange: (ShadowModifierData) -> Unit) {
    Column {
        Text("Shadow", style = MaterialTheme.typography.overline)
        Row {
            DpInput(elevationValue, onValueChange = {
                onChange(ShadowModifierData(it, shapeValue, cornerValue))
            })
            Spacer(Modifier.width(16.dp))
            ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
                onChange(ShadowModifierData(elevationValue, shape, corner))
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
            ColorInput(colorValue, onValueChange = { color ->
                onChange(BackgroundModifierData(color, shapeValue, cornerValue))
            })
            Spacer(Modifier.width(16.dp))
            ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
                onChange(BackgroundModifierData(colorValue, shape, corner))
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
            ColorInput(colorValue, onValueChange = { color ->
                onChange(BorderModifierData(widthValue, color, shapeValue, cornerValue))
            })
            Spacer(Modifier.width(16.dp))
            ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
                onChange(BorderModifierData(widthValue, colorValue, shape, corner))
            })
        }
    }
}

@Composable
fun PaddingModifier(allValue: Int, onChange: (PaddingModifierData) -> Unit) {
    Column {
        Text("Padding", style = MaterialTheme.typography.overline)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DpInput(allValue, onValueChange = {
                onChange(PaddingModifierData(it))
            })
        }
    }
}

@Composable
fun OffsetDesignModifier(xValue: Int, yValue: Int, onChange: (OffsetDesignModifierData) -> Unit) {
    Column {
        Text("Offset", style = MaterialTheme.typography.overline)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DpInput(xValue, onValueChange = {
                onChange(OffsetDesignModifierData(it, yValue))
            })
            DpInput(yValue, onValueChange = {
                onChange(OffsetDesignModifierData(xValue, it))
            })
        }
    }
}

fun buildModifiers(modifiersList: SnapshotStateList<Triple<Modifier, Any, Boolean>>): Modifier {
    var modifier: Modifier = Modifier

    modifiersList.forEach {
        val visible = it.third

        if (visible) {
            modifier = modifier.then(it.first)
        }
    }

    return modifier
}

