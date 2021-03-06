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
import com.c5inco.modifiers.utils.DottedLine

@Composable
fun Playground(
    activeTemplate: Template,
    onTemplateChange: (Template) -> Unit
) {
    var parentElement by remember(activeTemplate) { mutableStateOf(activeTemplate.parentElement) }
    var elementModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.parentModifiers) }
    val childElements = activeTemplate.childElements
    var childModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.childModifiers) }
    var childScopeModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.childScopeModifiers) }

    var showCode by remember { mutableStateOf(false) }

    Row {
        Surface(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.background
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
                    Divider(color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled))
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
                .shadow(4.dp),
            elevation = 2.dp
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            "Template //",
                            style = MaterialTheme.typography.body2,
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        )
                        Spacer(Modifier.width(4.dp))
                        DropdownInput(
                            modifier = Modifier.weight(1f),
                            items = listOf(
                                Templates.PinkSquare,
                                Templates.Rainbow,
                                Templates.Sun,
                                Templates.SimpleCard
                            ),
                            shape = RoundedCornerShape(6.dp),
                            hoverBackgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                            hoverBorderColor = Color.Transparent,
                            activeItem = activeTemplate,
                            onSelect = {
                                onTemplateChange(it)
                            }
                        )
                    },
                    modifier = Modifier.shadow(elevation = 3.dp),
                    actions = {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                            ResetDefaultModifiersAction(onClick = {
                                parentElement = activeTemplate.parentElement
                                elementModifiersList = activeTemplate.parentModifiers.toList()
                                childModifiersList = activeTemplate.childModifiers.toList()
                                childScopeModifiersList = activeTemplate.childScopeModifiers.toList()
                            })
                            Spacer(Modifier.width(12.dp))
                        }
                    }
                )
                Box {
                    val verticalScrollState = rememberScrollState(0)

                    Column(Modifier.fillMaxSize().verticalScroll(verticalScrollState)) {
                        ParentGroup(parentElement, elementModifiersList, onChange = { element, modifiers ->
                            elementModifiersList = modifiers.toList()
                            if (parentElement.type != element.type) {
                                childScopeModifiersList = childScopeModifiersList.map { listOf() }
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
                                    childScopeModifiersList = childScopeModifiersList.mapIndexed { idx, list ->
                                        if (idx == i) scopeModifiers.toMutableList() else list
                                    }
                                    childModifiersList = childModifiersList.mapIndexed { idx, list ->
                                        if (idx == i) modifiers.toMutableList() else list
                                    }
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
    modifiersList: List<Pair<Any, Boolean>>,
    onChange: (ElementModel, List<Pair<Any, Boolean>>) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    var modifiers = modifiersList.toMutableList()

    ComponentHeader("Parent element", expanded, onExpand = { expanded = !expanded })

    if (expanded) {
        PropertiesSection(
            empty = modifiers.isEmpty(),
            modifier = Modifier.padding(8.dp),
        ) {
            ElementRow(
                model = baseElement,
                onValueChange = {
                    onChange(it.copy(), modifiers.toList())
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
            empty = modifiers.isEmpty(),
            actions = {
                AddModifierAction(
                    onSelect = {
                        modifiers.add(Pair(getNewModifierData(it), true))
                        onChange(baseElement, modifiers.toList())
                    })
            }
        ) {
            modifiersList.forEachIndexed { idx, modifier ->
                ModifierEntry(
                    modifierData = modifier,
                    order = idx,
                    size = modifiers.size,
                    move = { index, up ->
                        val curr = modifiers[index]
                        val targetIndex = if (up) index - 1 else index + 1

                        val prev = modifiers.getOrNull(targetIndex)

                        if (prev != null) {
                            modifiers.set(targetIndex, curr)
                            modifiers.set(index, prev)
                        }
                        onChange(baseElement, modifiers.toList())
                    },
                    onModifierChange = { order, data ->
                        modifiers.set(order, data)
                        onChange(baseElement, modifiers.toList())
                    },
                    onRemove = { order ->
                        modifiers.removeAt(order)
                        onChange(baseElement, modifiers.toList())
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
    scopeModifiersList: List<Pair<Any, Boolean>>,
    modifiersList: List<Pair<Any, Boolean>>,
    onChange: (scopeModifiers: List<Pair<Any, Boolean>>, modifiers: List<Pair<Any, Boolean>>) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    val scopeModifiers = scopeModifiersList.toMutableList()
    val modifiers = modifiersList.toMutableList()

    ComponentHeader(name, expanded, onExpand = { expanded = !expanded })

    if (expanded) {
        fun onAddChildModifier(newModifier: Any) {
            scopeModifiers.add(Pair(getNewScopeModifierData(newModifier), true))
            onChange(scopeModifiers.toList(), modifiers.toList())
        }

        PropertiesSection(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxHeight(),
            name = "${parentElement}Scope modifiers",
            empty = scopeModifiers.isEmpty(),
            actions = {
                AddChildModifierAction(
                    parentElement,
                    onSelect = { onAddChildModifier(it) }
                )
            }
        ) {
            scopeModifiers.forEachIndexed { idx, modifier ->
                RowColumnScopeModifierEntry(
                    modifierData = modifier,
                    order = idx,
                    size = scopeModifiers.size,
                    onModifierChange = { order, data ->
                        scopeModifiers.set(order, data)
                        onChange(scopeModifiers.toList(), modifiers.toList())
                    },
                    onRemove = { order ->
                        scopeModifiers.removeAt(order)
                        onChange(scopeModifiers.toList(), modifiers.toList())
                    }
                )
            }
        }

        DottedLine(Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))

        PropertiesSection(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxHeight(),
            name = "Modifiers",
            empty = modifiers.isEmpty(),
            actions = {
                AddModifierAction(
                    onSelect = {
                        modifiers.add(Pair(getNewModifierData(it), true))
                        onChange(scopeModifiers.toList(), modifiers.toList())
                    })
            }
        ) {
            modifiers.forEachIndexed { i, modifier ->
                ModifierEntry(
                    modifierData = modifier,
                    order = i,
                    size = modifiers.size,
                    move = { index, up ->
                        val curr = modifiers[index]
                        val targetIndex = if (up) index - 1 else index + 1

                        val prev = modifiers.getOrNull(targetIndex)

                        if (prev != null) {
                            modifiers.set(targetIndex, curr)
                            modifiers.set(index, prev)
                        }
                        onChange(scopeModifiers.toList(), modifiers.toList())
                    },
                    onModifierChange = { order, data ->
                        modifiers.set(order, data)
                        onChange(scopeModifiers.toList(), modifiers.toList())
                    },
                    onRemove = { order ->
                        modifiers.removeAt(order)
                        onChange(scopeModifiers.toList(), modifiers.toList())
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
        val primaryColor = MaterialTheme.colors.primary
        Canvas(Modifier.width(4.dp).fillMaxHeight()) {
            drawRoundRect(
                color = primaryColor,
                size = Size(width = size.width, height = size.height),
                cornerRadius = CornerRadius(50f)
            )
        }
        Spacer(Modifier.width(6.dp))
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
            tint = LocalContentColor.current.copy(alpha = ContentAlpha.high)
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