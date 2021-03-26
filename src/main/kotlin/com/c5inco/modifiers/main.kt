package com.c5inco.modifiers

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c5inco.modifiers.data.*
import com.c5inco.modifiers.ui.CodeView
import com.c5inco.modifiers.ui.ElementRow
import com.c5inco.modifiers.ui.ModifierEntry
import com.c5inco.modifiers.ui.SmallIconButton

fun main() = Window(
    title = "Modifiers Playground",
    size = IntSize(width = 900, height = 750)
) {
    Playground()
}

@Composable
fun Playground() {
    val defaultParentModifiers = listOf(
        Pair(ShadowModifierData(elevation = 20, shape = AvailableShapes.RoundedCorner, corner = 60), true),
        Pair(SizeModifierData(width = 360, height = 360), true),
        Pair(BackgroundModifierData(color = Color.Magenta), true),
        Pair(PaddingModifierData(40), true),
        Pair(BorderModifierData(width = 20, color = Color.Cyan, shape = AvailableShapes.RoundedCorner, corner = 40), true),
        Pair(PaddingModifierData(20), true),
        Pair(BackgroundModifierData(color = Color.White), true),
    )

    val defaultChildModifiers = listOf<Pair<Any, Boolean>>(
        Pair(BackgroundModifierData(color = Color.Red), true),
    )

    var parentElement by remember {
        mutableStateOf(
            ElementModel(
                AvailableElements.Row,
                RowElementData(
                    horizontalArrangement = AvailableHorizontalArrangements.SpacedAround,
                    verticalAlignment = Alignment.CenterVertically)
            )
        )
    }

    var elementModifiersList = remember {
        defaultParentModifiers.toMutableStateList()
    }

    var childModifiersList = remember {
        defaultChildModifiers.toMutableStateList()
    }

    var showCode by remember { mutableStateOf(false) }

    DesktopMaterialTheme {
        Row {
            Surface(
                modifier = Modifier.weight(1f),
                color = Color(0xffe5e5e5)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        val element = parentElement.type

                        val content: @Composable () -> Unit = {
                            Text("🥑", fontSize = 48.sp, modifier = buildModifiers(childModifiersList))
                            Text("☕", fontSize = 48.sp, modifier = buildModifiers(childModifiersList))
                            Text("🤖", fontSize = 48.sp, modifier = buildModifiers(childModifiersList))
                        }
                        val elementModifiersChain = buildModifiers(elementModifiersList)

                        when (element) {
                            AvailableElements.Box -> {
                                val data = parentElement.data as BoxElementData
                                Box(
                                    modifier = elementModifiersChain,
                                    contentAlignment = data.contentAlignment
                                ) {
                                    content()
                                }
                            }
                            AvailableElements.Column -> {
                                val data = parentElement.data as ColumnElementData
                                Column(
                                    modifier = elementModifiersChain,
                                    verticalArrangement = getVerticalArrangementObject(
                                        data.verticalArrangement,
                                        data.verticalSpacing
                                    ),
                                    horizontalAlignment = data.horizontalAlignment
                                ) {
                                    content()
                                }
                            }
                            AvailableElements.Row -> {
                                val data = parentElement.data as RowElementData
                                Row(
                                    modifier = elementModifiersChain,
                                    horizontalArrangement = getHorizontalArrangementObject(
                                        data.horizontalArrangement,
                                        data.horizontalSpacing
                                    ),
                                    verticalAlignment = data.verticalAlignment
                                ) {
                                    content()
                                }
                            }
                        }

                        IconButton(
                            onClick = { showCode = !showCode },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 8.dp, bottom = 8.dp)
                        ) {
                            Icon(
                                imageVector = if (showCode) Icons.Outlined.CodeOff else Icons.Outlined.Code,
                                contentDescription = "Toggle code on or off",
                                tint = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                            )
                        }
                    }

                    if (showCode) {
                        CodeView(
                            Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            parentElement,
                            elementModifiersList
                        )
                    }
                }
            }

            var propertiesHovered by remember { mutableStateOf(false) }
            Surface(
                Modifier
                    .pointerMoveFilter(
                        onEnter = {
                            propertiesHovered = true
                            false
                        },
                        onExit = {
                            propertiesHovered = false
                            false
                        }
                    )
                    .width(350.dp)
                    .shadow(4.dp)
            ) {
                Box {
                    val verticalScrollState = rememberScrollState(0)

                    Column(Modifier.fillMaxSize().verticalScroll(verticalScrollState)) {
                        ParentGroup(parentElement, elementModifiersList, onChange = { element, modifiers ->
                            parentElement = element
                            elementModifiersList.clear()
                            elementModifiersList.addAll(modifiers)
                        })
                        ChildGroup("🥑", childModifiersList, onChange = {
                            childModifiersList.clear()
                            childModifiersList.addAll(it)
                        })
                        ChildGroup("☕", childModifiersList, onChange = {
                            childModifiersList.clear()
                            childModifiersList.addAll(it)
                        })
                        ChildGroup("🤖", childModifiersList, onChange = {
                            childModifiersList.clear()
                            childModifiersList.addAll(it)
                        })
                    }

                    if (propertiesHovered) {
                        VerticalScrollbar(
                            modifier = Modifier.align(Alignment.CenterEnd)
                                .fillMaxHeight(),
                            adapter = rememberScrollbarAdapter(verticalScrollState),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ParentGroup(
    baseElement: ElementModel,
    modifiersList: MutableList<Pair<Any, Boolean>>,
    onChange: (ElementModel, List<Pair<Any, Boolean>>) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    ComponentHeader("Parent element", expanded, onExpand = { expanded = !expanded })

    if (expanded) {
        PropertiesSection(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            name = "Base element"
        ) {
            ElementRow(
                model = baseElement,
                onValueChange = {
                    onChange(it.copy(), modifiersList.toList())
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
                        modifiersList.add(Pair(getNewModifierData(it), true))
                        onChange(baseElement, modifiersList.toList())
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
                        onChange(baseElement, modifiersList.toList())
                    },
                    onRemove = { order ->
                        modifiersList.removeAt(order)
                        onChange(baseElement, modifiersList.toList())
                    }
                )
            }
        }
    }
}

@Composable
fun ChildGroup(
    name: String,
    modifiersList: MutableList<Pair<Any, Boolean>>,
    onChange: (List<Pair<Any, Boolean>>) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    ComponentHeader("$name element", expanded, onExpand = { expanded = !expanded })

    if (expanded) {
        PropertiesSection(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            name = "Base element"
        ) {
            Text("Text")
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
                        modifiersList.add(Pair(getNewModifierData(it), true))
                        onChange(modifiersList.toList())
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
                        onChange(modifiersList.toList())
                    },
                    onRemove = { order ->
                        modifiersList.removeAt(order)
                        onChange(modifiersList.toList())
                    }
                )
            }
        }
    }
}

@Composable
private fun ComponentHeader(name: String, expanded: Boolean, onExpand: () -> Unit) {
    Row(Modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name.toUpperCase(), style = MaterialTheme.typography.subtitle1)
        SmallIconButton(onClick = { onExpand() }) {
            Icon(
                imageVector = if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Expand/collapse component group"
            )
        }
    }
}

private fun getNewModifierData(modifierType: ModifierEntry): Any = (
    when (modifierType) {
        ModifierEntry.Padding -> {
            PaddingModifierData()
        }
        ModifierEntry.Background -> {
            BackgroundModifierData()
        }
        ModifierEntry.Border -> {
            BorderModifierData()
        }
        ModifierEntry.Shadow -> {
            ShadowModifierData()
        }
        ModifierEntry.Offset -> {
            OffsetDesignModifierData()
        }
        ModifierEntry.Clip -> {
            ClipModifierData()
        }
        ModifierEntry.Rotate -> {
            RotateModifierData()
        }
        ModifierEntry.Scale -> {
            ScaleModifierData()
        }
        else -> {
            SizeModifierData()
        }
    }
)

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
            DropdownMenuItem(onClick = { select(ModifierEntry.Clip) }) {
                Text("Clip")
            }
            DropdownMenuItem(onClick = { select(ModifierEntry.Rotate) }) {
                Text("Rotate")
            }
            DropdownMenuItem(onClick = { select(ModifierEntry.Scale) }) {
                Text("Scale")
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

fun buildModifiers(modifiersList: List<Pair<Any, Boolean>>): Modifier {
    var modifier: Modifier = Modifier

    modifiersList.forEach {
        val visible = it.second

        if (visible) {
            modifier = modifier.then(getModifier(it.first))
        }
    }

    return modifier
}

