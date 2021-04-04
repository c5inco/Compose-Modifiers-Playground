package com.c5inco.modifiers.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.c5inco.modifiers.data.*
import com.c5inco.modifiers.ui.controls.CompactDropdownItem
import com.c5inco.modifiers.ui.controls.DropdownInput
import com.c5inco.modifiers.ui.controls.SmallIconButton
import com.c5inco.modifiers.ui.theme.pink400
import com.c5inco.modifiers.utils.DottedLine

@Composable
fun Playground(
    activeTemplate: Template,
    onTemplateChange: (Template) -> Unit
) {
    var parentElement by remember(activeTemplate) { mutableStateOf(activeTemplate.parentElement) }
    var elementModifiersList = activeTemplate.parentModifiers.toMutableStateList()
    val childElements = activeTemplate.childElements
    var childModifiersList = activeTemplate.childModifiers.toMutableStateList()
    var childScopeModifiersList = activeTemplate.childScopeModifiers.toMutableStateList()

    var showCode by remember { mutableStateOf(false) }

    PlaygroundTheme {
        Row {
            Surface(
                modifier = Modifier.weight(1f),
                color = Color(0xffe5e5e5)
            ) {
                Column {
                    PreviewCanvas(
                        Modifier.weight(2f).fillMaxSize(),
                        parentElement,
                        childElements,
                        childScopeModifiersList,
                        childModifiersList,
                        elementModifiersList,
                        showCode,
                        onShowCode = { showCode = it }
                    )

                    if (showCode) {
                        CodeView(
                            Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            parentElement,
                            elementModifiersList,
                            childElements,
                            childModifiersList,
                            childScopeModifiersList
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
                Column {
                    TopAppBar(
                        title = {
                            Text(
                                "Template //",
                                style = MaterialTheme.typography.body2,
                                color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                            )
                            Spacer(androidx.compose.ui.Modifier.width(4.dp))
                            DropdownInput(
                                modifier = Modifier.weight(1f),
                                items = listOf(
                                    Templates.PinkSquare,
                                    Templates.Rainbow,
                                    Templates.Sun,
                                    Templates.SimpleCard
                                ),
                                shape = RoundedCornerShape(6.dp),
                                hoverBackgroundColor = Color.Black.copy(alpha = 0.2f),
                                hoverBorderColor = Color.Transparent,
                                activeItem = activeTemplate,
                                onSelect = {
                                    onTemplateChange(it)
                                }
                            )
                        },
                        modifier = Modifier.shadow(elevation = 3.dp),
                        actions = {
                            ResetDefaultModifiersAction(onClick = {
                                parentElement = activeTemplate.parentElement
                                elementModifiersList.clear()
                                elementModifiersList.addAll(activeTemplate.parentModifiers)
                                childModifiersList.clear()
                                childModifiersList.addAll(activeTemplate.childModifiers)
                                childScopeModifiersList.clear()
                                childScopeModifiersList.addAll(activeTemplate.childScopeModifiers)
                            })
                            Spacer(androidx.compose.ui.Modifier.width(12.dp))
                        }
                    )
                    Box {
                        val verticalScrollState = rememberScrollState(0)

                        Column(Modifier.fillMaxSize().verticalScroll(verticalScrollState)) {
                            ParentGroup(parentElement, elementModifiersList, onChange = { element, modifiers ->
                                elementModifiersList.clear()
                                elementModifiersList.addAll(modifiers)
                                if (parentElement.type != element.type) {
                                    childScopeModifiersList.forEach {
                                        it.clear()
                                    }
                                }
                                parentElement = element
                            })

                            childElements.forEachIndexed { i, element ->
                                Divider()
                                ChildGroup(
                                    getChildElementHeader(element),
                                    parentElement.type,
                                    scopeModifiersList = childScopeModifiersList[i],
                                    modifiersList = childModifiersList[i],
                                    onChange = { scopeModifiers, modifiers ->
                                        childScopeModifiersList.set(i, scopeModifiers.toMutableList())
                                        childModifiersList.set(i, modifiers.toMutableList())
                                    }
                                )
                            }
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
}

fun getChildElementHeader(data: Any): String = (
    when (data) {
        is TextChildData -> {
            val (text) = data
            "Text(\"$text\")"
        }
        is ImageChildData -> {
            val (imagePath) = data
            "Image(\"$imagePath\")"
        }
        else -> {
            val (emoji) = data as EmojiChildData
            "$emoji emoji"
        }
    }
)

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
            empty = modifiersList.isEmpty(),
            modifier = Modifier.padding(8.dp),
        ) {
            ElementRow(
                model = baseElement,
                onValueChange = {
                    onChange(it.copy(), modifiersList.toList())
                }
            )
        }

        DottedLine(Modifier.padding(horizontal = 16.dp))
        Spacer(androidx.compose.ui.Modifier.height(8.dp))

        PropertiesSection(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxHeight(),
            name = "Modifiers",
            empty = modifiersList.isEmpty(),
            actions = {
                AddModifierAction(
                    onSelect = {
                        modifiersList.add(Pair(getNewModifierData(it), true))
                        onChange(baseElement, modifiersList.toList())
                    })
            }
        ) {
            modifiersList.forEachIndexed { idx, modifier ->
                ModifierEntry(
                    modifierData = modifier,
                    order = idx,
                    size = modifiersList.size,
                    move = { index, up ->
                        val curr = modifiersList[index]
                        val targetIndex = if (up) index - 1 else index + 1

                        val prev = modifiersList.getOrNull(targetIndex)

                        if (prev != null) {
                            modifiersList.set(targetIndex, curr)
                            modifiersList.set(index, prev)
                        }
                        onChange(baseElement, modifiersList.toList())
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
    parentElement: AvailableElements,
    scopeModifiersList: MutableList<Pair<Any, Boolean>>,
    modifiersList: MutableList<Pair<Any, Boolean>>,
    onChange: (scopeModifiers: List<Pair<Any, Boolean>>, modifiers: List<Pair<Any, Boolean>>) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    ComponentHeader(name, expanded, onExpand = { expanded = !expanded })

    if (expanded) {
        /*
        PropertiesSection {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        EditorTheme.colors.backgroundDark,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    formatCode("Text(\"$name\", fontSize = 48.sp)"),
                    fontSize = 14.sp,
                    fontFamily = Fonts.jetbrainsMono()
                )
            }
        }

        DottedLine(Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))
        */
        fun onAddChildModifier(newModifier: Any) {
            scopeModifiersList.add(Pair(getNewScopeModifierData(newModifier), true))
            onChange(scopeModifiersList.toList(), modifiersList.toList())
        }

        PropertiesSection(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxHeight(),
            name = "${parentElement}Scope modifiers",
            empty = scopeModifiersList.isEmpty(),
            actions = {
                AddChildModifierAction(
                    parentElement,
                    onSelect = { onAddChildModifier(it) }
                )
            }
        ) {
            scopeModifiersList.forEachIndexed { idx, modifier ->
                RowColumnScopeModifierEntry(
                    modifierData = modifier,
                    order = idx,
                    size = scopeModifiersList.size,
                    onModifierChange = { order, data ->
                        scopeModifiersList.set(order, data)
                        onChange(scopeModifiersList.toList(), modifiersList.toList())
                    },
                    onRemove = { order ->
                        scopeModifiersList.removeAt(order)
                        onChange(scopeModifiersList.toList(), modifiersList.toList())
                    }
                )
            }
        }

        DottedLine(Modifier.padding(horizontal = 16.dp))
        Spacer(androidx.compose.ui.Modifier.height(8.dp))

        PropertiesSection(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxHeight(),
            name = "Modifiers",
            empty = modifiersList.isEmpty(),
            actions = {
                AddModifierAction(
                    onSelect = {
                        modifiersList.add(Pair(getNewModifierData(it), true))
                        onChange(scopeModifiersList.toList(), modifiersList.toList())
                    })
            }
        ) {
            modifiersList.forEachIndexed { i, modifier ->
                ModifierEntry(
                    modifierData = modifier,
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
                        onChange(scopeModifiersList.toList(), modifiersList.toList())
                    },
                    onModifierChange = { order, data ->
                        modifiersList.set(order, data)
                        onChange(scopeModifiersList.toList(), modifiersList.toList())
                    },
                    onRemove = { order ->
                        modifiersList.removeAt(order)
                        onChange(scopeModifiersList.toList(), modifiersList.toList())
                    }
                )
            }
        }
    }
}

@Composable
fun PropertiesSection(
    modifier: Modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    name: String? = null,
    empty: Boolean = false,
    actions: @Composable RowScope.() -> Unit = { },
    content: @Composable ColumnScope.() -> Unit
) {
    var hovered by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(if (hovered || !empty) 1f else ContentAlpha.disabled)

    Column {
        name?.let {
            Row(
                Modifier
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
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .alpha(alphaAnim),
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
        }
        Column(modifier) {
            content()
        }
    }
}

@Composable
private fun ComponentHeader(name: String, expanded: Boolean, onExpand: () -> Unit) {
    val expandAnim by animateFloatAsState(if (expanded) 180f else 0f)

    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(androidx.compose.ui.Modifier.width(4.dp).fillMaxHeight()) {
            drawRoundRect(
                color = pink400,
                size = Size(width = size.width, height = size.height),
                cornerRadius = CornerRadius(50f)
            )
        }
        Spacer(androidx.compose.ui.Modifier.width(6.dp))
        Text(
            name,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f).padding(end = 4.dp)
        )
        SmallIconButton(onClick = { onExpand() }) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Expand/collapse component group",
                modifier = Modifier.rotate(expandAnim)
            )
        }
    }
}

@Composable
private fun ResetDefaultModifiersAction(onClick: () -> Unit) {
    SmallIconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Outlined.RestartAlt,
            contentDescription = "Reset default modifiers",
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colors.onPrimary
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

        val defaultVerticalPadding = 8
        val dropdownHeight = 32

        DropdownMenu(
            modifier = Modifier
                .sizeIn(
                    minHeight = (defaultVerticalPadding * 2 + dropdownHeight).dp,
                    maxHeight = (defaultVerticalPadding * 2 + dropdownHeight * 10.5).dp
                ),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val select: (ModifierEntry) -> Unit = {
                onSelect(it)
                expanded = false
            }

            ModifierEntry.values().toList().forEach { entry ->
                CompactDropdownItem(entry, onClick = { select(entry) })
            }
        }
    }
}

@Composable
private fun AddChildModifierAction(parentType: AvailableElements, onSelect: (Any) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        SmallIconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add child modifier",
                modifier = Modifier.size(18.dp)
            )
        }

        AddScopeModifiersDropdownMenu(
            expanded,
            parentType,
            onDismiss = { expanded = false },
            onSelect = {
                onSelect(it)
                expanded = false
            })
    }
}

@Composable
private fun AddScopeModifiersDropdownMenu(
    expanded: Boolean,
    parentType: AvailableElements,
    onDismiss: () -> Unit,
    onSelect: (Any) -> Unit
) {
    val defaultVerticalPadding = 8
    val dropdownHeight = 32

    DropdownMenu(
        modifier = Modifier
            .sizeIn(
                minHeight = (defaultVerticalPadding * 2 + dropdownHeight).dp,
                maxHeight = (defaultVerticalPadding * 2 + dropdownHeight * 10.5).dp
            ),
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        val select: (Any) -> Unit = {
            onSelect(it)
        }

        when (parentType) {
            AvailableElements.Box -> {
                BoxScopeModifierEntry.values().toList().forEach { entry ->
                    CompactDropdownItem(entry, onClick = { select(entry) })
                }
            }
            AvailableElements.Column -> {
                ColumnScopeModifierEntry.values().toList().forEach { entry ->
                    CompactDropdownItem(entry, onClick = { select(entry) })
                }
            }
            AvailableElements.Row -> {
                RowScopeModifierEntry.values().toList().forEach { entry ->
                    CompactDropdownItem(entry, onClick = { select(entry) })
                }
            }
        }
    }
}