
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.*
import ui.ElementRow
import ui.ModifierEntry
import ui.SmallIconButton

fun main() = Window(
    title = "Modifiers Playground",
    size = IntSize(width = 900, height = 750)
) {
    val defaultModifiers = listOf(
        Triple(Modifier.size(400.dp), SizeModifierData(width = 400, height = 400), true),
        Triple(Modifier.background(Color.Magenta), BackgroundModifierData(color = Color.Magenta), true),
        Triple(Modifier.padding(20.dp), PaddingModifierData(20), true),
        Triple(Modifier.background(Color.Gray), BackgroundModifierData(color = Color.Gray), true),
        Triple(Modifier, ShadowModifierData(), true),
        Triple(Modifier.border(width = 2.dp, color = Color.Blue), BorderModifierData(), true),
    )

    var baseElement by remember {
        mutableStateOf<Pair<AvailableElements, Any>>(
            Pair(
                AvailableElements.Box,
                BoxElementData()
            )
        )
    }

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
                    val element = baseElement.first

                    val content: @Composable () -> Unit = {
                        Text("🥑", fontSize = 48.sp)
                    }
                    val modifiersChain = buildModifiers(modifiersList)

                    when (element) {
                        AvailableElements.Box -> {
                            val data = baseElement.second as BoxElementData
                            Box(
                                modifier = modifiersChain,
                                contentAlignment = data.contentAlignment
                            ) {
                                content()
                            }
                        }
                        AvailableElements.Column -> {
                            val data = baseElement.second as ColumnElementData
                            Column(
                                modifier = modifiersChain,
                                horizontalAlignment = data.horizontalAlignment
                            ) {
                                content()
                            }
                        }
                        AvailableElements.Row -> {
                            val data = baseElement.second as RowElementData
                            Row(
                                modifier = modifiersChain,
                                verticalAlignment = data.verticalAlignment
                            ) {
                                content()
                            }
                        }
                    }
                }
            }
            Surface(
                Modifier.width(350.dp)
            ) {
                Column {
                    PropertiesSection(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                        name = "Base element"
                    ) {
                        ElementRow(
                            elementValue = baseElement,
                            onValueChange = {
                                baseElement = it
                            }
                        )
                    }
                    Divider(Modifier.height(1.dp))
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
                            ResetDefaultModifiersAction(
                                onClick = {
                                    modifiersList.clear()
                                    modifiersList.addAll(defaultModifiers)
                                }
                            )
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
            newModifier = Pair(Modifier.border(width = 2.dp, color = Color.Blue), BorderModifierData())
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

@Composable
private fun ResetDefaultModifiersAction(onClick: () -> Unit) {
    SmallIconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Outlined.RestartAlt,
            contentDescription = "Reset default modifiers",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun AddModifierAction(onSelect: (ModifierEntry) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        SmallIconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add modifier",
                modifier = Modifier.size(18.dp)
            )
        }
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
    actions: @Composable RowScope.() -> Unit = { },
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                actions()
            }
        }
        Column(modifier) {
            content()
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

