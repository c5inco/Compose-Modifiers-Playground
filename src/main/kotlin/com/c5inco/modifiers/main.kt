package com.c5inco.modifiers

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.Window
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c5inco.modifiers.data.*
import com.c5inco.modifiers.ui.*
import com.c5inco.modifiers.ui.controls.CompactDropdownItem
import com.c5inco.modifiers.ui.theme.Fonts
import com.c5inco.modifiers.ui.theme.appLightColors
import com.c5inco.modifiers.ui.theme.blue700
import com.c5inco.modifiers.utils.downTo
import com.c5inco.modifiers.utils.formatCode
import com.c5inco.modifiers.utils.until

fun main() = Window(
    title = "Modifiers Playground",
    size = IntSize(width = 1000, height = 750)
) {
    Application()
}

@Composable
fun Application() {
    val pinkSquare = Templates.Rainbow

    var activeTemplate by remember { mutableStateOf(pinkSquare) }

    Playground(activeTemplate, onTemplateChange = {
        activeTemplate = it.copy()
    })
}

@Composable
fun Playground(
    activeTemplate: Template,
    onTemplateChange: (Template) -> Unit
) {
    var parentElement = activeTemplate.parentElement
    var elementModifiersList = activeTemplate.parentModifiers.toMutableStateList()
    var childModifiersList = activeTemplate.childModifiers.toMutableStateList()
    var childScopeModifiersList = activeTemplate.childScopeModifiers.toMutableStateList()

    var showCode by remember { mutableStateOf(false) }

    PlaygroundTheme(
        colors = appLightColors
    ) {
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
                        // Subtle background
                        DotsBackground(Modifier.align(Alignment.Center))

                        val element = parentElement.type

                        val content: @Composable () -> Unit = {
                            val emojis = listOf("🥑", "☕", "🤖")
                            emojis.forEachIndexed { idx, emoji ->
                                var sm: Modifier = Modifier
                                when (element) {
                                    AvailableElements.Column -> {
                                        ColumnScope.apply {
                                            childScopeModifiersList[idx].forEach {
                                                val (data, visible) = it
                                                if (visible) {
                                                    when (data) {
                                                        is WeightModifierData -> {
                                                            val (weight) = data
                                                            sm = sm.then(Modifier.weight(weight))
                                                        }
                                                        is AlignColumnModifierData -> {
                                                            val (alignment) = data
                                                            sm = sm.then(Modifier.align(getHorizontalAlignments(alignment)))
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    AvailableElements.Row -> {
                                        RowScope.apply {
                                            childScopeModifiersList[idx].forEach {
                                                val (data, visible) = it
                                                if (visible) {
                                                    when (data) {
                                                        is WeightModifierData -> {
                                                            val (weight) = data
                                                            sm = sm.then(Modifier.weight(weight))
                                                        }
                                                        is AlignRowModifierData -> {
                                                            val (alignment) = data
                                                            sm = sm.then(Modifier.align(getVerticalAlignments(alignment)))
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else -> {
                                        BoxScope.apply {
                                            childScopeModifiersList[idx].forEach {
                                                val (data, visible) = it
                                                if (visible) {
                                                    when (data) {
                                                        is AlignBoxModifierData -> {
                                                            val (alignment) = data
                                                            sm = sm.then(Modifier.align(getContentAlignments(alignment)))
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                Text(emoji, fontSize = 48.sp, modifier = sm.then(buildModifiers(childModifiersList[idx])))
                            }
                        }
                        val elementModifiersChain = buildModifiers(elementModifiersList)

                        when (element) {
                            AvailableElements.Box -> {
                                val data = parentElement.data as BoxElementData
                                Box(
                                    modifier = elementModifiersChain,
                                    contentAlignment = getContentAlignments(data.contentAlignment)
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
                                    horizontalAlignment = getHorizontalAlignments(data.horizontalAlignment)
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
                                    verticalAlignment = getVerticalAlignments(data.verticalAlignment)
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
                                .shadow(elevation = 6.dp, shape = RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colors.surface)
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (showCode) Icons.Outlined.CodeOff else Icons.Outlined.Code,
                                contentDescription = "Toggle code on or off",
                                tint = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                            )
                        }
                    }

                    if (showCode) {
                        CodeView(
                            Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            parentElement,
                            elementModifiersList,
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
                            Spacer(Modifier.width(4.dp))
                            DropdownInput(
                                modifier = Modifier.weight(1f),
                                items = listOf(Templates.PinkSquare, Templates.Rainbow),
                                shape = RoundedCornerShape(6.dp),
                                hoverBackgroundColor = Color.Black.copy(alpha = 0.2f),
                                hoverBorderColor = Color.Transparent,
                                activeItem = activeTemplate,
                                onSelect = {
                                    onTemplateChange(it)
                                }
                            )
                        },
                        modifier = Modifier.shadow(elevation = 3.dp)
                    )
                    Box {
                        val verticalScrollState = rememberScrollState(0)

                        Column(Modifier.fillMaxSize().verticalScroll(verticalScrollState)) {
                            ParentGroup(parentElement, elementModifiersList, onChange = { element, modifiers ->
                                parentElement = element
                                elementModifiersList.clear()
                                elementModifiersList.addAll(modifiers)
                                childScopeModifiersList.clear()
                                childScopeModifiersList.add(mutableListOf())
                                childScopeModifiersList.add(mutableListOf())
                                childScopeModifiersList.add(mutableListOf())
                            })

                            val emojis = listOf("🥑", "☕", "🤖")
                            emojis.forEachIndexed { i, emoji ->
                                Divider()
                                ChildGroup(
                                    emoji,
                                    parentElement.type,
                                    scopeModifiersList = childScopeModifiersList[i].toMutableList(),
                                    modifiersList = childModifiersList[i].toMutableList(),
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
            modifier = Modifier.padding(8.dp),
        ) {
            ElementRow(
                model = baseElement,
                onValueChange = {
                    onChange(it.copy(), modifiersList.toList())
                }
            )
        }

        DottedLine(Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))

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
    ComponentHeader("$name element", expanded, onExpand = { expanded = !expanded })

    if (expanded) {
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

        PropertiesSection(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxHeight(),
            name = "Scope modifiers",
            actions = {
                AddChildModifierAction(
                    parentElement,
                    onSelect = {
                        scopeModifiersList.add(Pair(getNewScopeModifierData(it), true))
                        onChange(scopeModifiersList.toList(), modifiersList.toList())
                    })
            }
        ) {
            for (i in 0 until scopeModifiersList.size) {
                RowColumnScopeModifierEntry(
                    modifierData = scopeModifiersList[i],
                    order = i,
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

        DottedLine(Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))

        PropertiesSection(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxHeight(),
            name = "Modifiers",
            actions = {
                AddModifierAction(
                    onSelect = {
                        modifiersList.add(Pair(getNewModifierData(it), true))
                        onChange(scopeModifiersList.toList(), modifiersList.toList())
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
    actions: @Composable RowScope.() -> Unit = { },
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        name?.let {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it,
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

@Composable
private fun ComponentHeader(name: String, expanded: Boolean, onExpand: () -> Unit) {
    val expandAnim by animateFloatAsState(if (expanded) 180f else 0f)

    Row(Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(Modifier.width(4.dp).fillMaxHeight()) {
            drawRoundRect(
                color = blue700,
                size = Size(width = size.width, height = size.height),
                cornerRadius = CornerRadius(50f)
            )
        }
        Spacer(Modifier.width(6.dp))
        Text(
            name,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1f)
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

private fun getNewModifierData(modifierType: ModifierEntry): Any = (
    when (modifierType) {
        ModifierEntry.Alpha -> {
            AlphaModifierData()
        }
        ModifierEntry.Padding -> {
            PaddingModifierData()
        }
        ModifierEntry.Height -> {
            HeightModifierData()
        }
        ModifierEntry.Width -> {
            WidthModifierData()
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
        ModifierEntry.Clickable -> {
            ClickableModifierData()
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
        ModifierEntry.FillMaxWidth -> {
            FillMaxWidthModifierData()
        }
        ModifierEntry.FillMaxHeight -> {
            FillMaxHeightModifierData()
        }
        ModifierEntry.FillMaxSize -> {
            FillMaxSizeModifierData()
        }
        ModifierEntry.WrapContentHeight -> {
            WrapContentHeightModifierData()
        }
        ModifierEntry.WrapContentWidth -> {
            WrapContentWidthModifierData()
        }
        ModifierEntry.WrapContentSize -> {
            WrapContentSizeModifierData()
        }
        else -> {
            SizeModifierData()
        }
    }
)

private fun getNewScopeModifierData(modifierType: Any): Any = (
    when (modifierType) {
        ColumnScopeModifierEntry.Weight,
        RowScopeModifierEntry.Weight -> {
            WeightModifierData()
        }
        BoxScopeModifierEntry.Align -> {
            AlignBoxModifierData()
        }
        ColumnScopeModifierEntry.Align -> {
            AlignColumnModifierData()
        }
        RowScopeModifierEntry.Align -> {
            AlignRowModifierData()
        }
        else -> { }
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
            val select: (Any) -> Unit = {
                onSelect(it)
                expanded = false
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
}

@Composable
private fun DotsBackground(modifier: Modifier) {
    Canvas(
        modifier.fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val circleColor = SolidColor(Color.LightGray)
        val circleRadius = 2f
        val circleStep = 20

        val circle: (Int, Int) -> Unit = { x, y ->
            drawCircle(
                brush = circleColor,
                radius = circleRadius,
                center = Offset(x = x.toFloat(), y = y.toFloat())
            )
        }

        until((canvasWidth / 2).toInt(), canvasWidth.toInt(), circleStep) { uidx ->
            downTo((canvasHeight / 2).toInt(), 0, circleStep) { didx ->
                circle(uidx, didx)
            }
            until((canvasHeight / 2).toInt(), canvasHeight.toInt(), circleStep) { uidx2 ->
                circle(uidx, uidx2)
            }
        }
        downTo((canvasWidth / 2).toInt(), 0, circleStep) { uidx ->
            downTo((canvasHeight / 2).toInt(), 0, circleStep) { didx ->
                circle(uidx, didx)
            }
            until((canvasHeight / 2).toInt(), canvasHeight.toInt(), circleStep) { uidx2 ->
                circle(uidx, uidx2)
            }
        }
    }
}

@Composable
private fun DottedLine(modifier: Modifier = Modifier, color: Color = Color.Gray) {
    Canvas(
        modifier
            .fillMaxWidth()
    ) {
        until(0, size.width.toInt(), 10) {
            drawCircle(
                brush = SolidColor(color),
                radius = 1f,
                center = Offset(x = it.toFloat(), y = 0f)
            )
        }
    }
}

