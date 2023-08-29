package ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import data.*
import ui.panel.PropertiesPanel
import utils.calculateWindowSize

@Composable
fun Playground(
    modifier: Modifier = Modifier,
    activeTemplate: Template,
    onTemplateChange: (Template) -> Unit
) {
    val density = LocalDensity.current
    val windowSize = calculateWindowSize()

    var parentElement by remember(activeTemplate) { mutableStateOf(activeTemplate.parentElement) }
    var elementModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.parentModifiers) }
    val childElements = activeTemplate.childElements
    var childModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.childModifiers) }
    var childScopeModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.childScopeModifiers) }

    var showCode by remember { mutableStateOf(false) }

    Row(modifier = modifier) {
        val initialCodeViewHeight by remember(windowSize) {
            derivedStateOf {
                with (density) {
                    (windowSize.height / 3).roundToPx()
                }
            }
        }

        var codeViewHeight by remember(initialCodeViewHeight) {
            mutableStateOf(initialCodeViewHeight)
        }

        val dividerInteractionSource = remember { MutableInteractionSource() }
        val dividerHovered by dividerInteractionSource.collectIsHoveredAsState()

        Surface(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.background
        ) {
            Box {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    PreviewCanvas(
                        modifier = Modifier.weight(2f),
                        parentElement = parentElement,
                        childElements = childElements,
                        childScopeModifiersList = childScopeModifiersList,
                        childModifiersList = childModifiersList,
                        elementModifiersList = elementModifiersList,
                        showCode = showCode,
                        onShowCode = { showCode = it }
                    )

                    if (showCode) {
                        Divider(
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                        )
                        CodeView(
                            modifier = Modifier.height(with (density) { codeViewHeight.toDp() }),
                            elementModel = parentElement,
                            elementModifiers = elementModifiersList,
                            childElements = childElements,
                            childModifiersList = childModifiersList,
                            childScopeModifiersList = childScopeModifiersList
                        )
                    }
                }
                if (showCode) {
                    val dividerDragHeight = 7

                    Divider(
                        modifier = Modifier
                            .height(dividerDragHeight.dp)
                            .align(Alignment.BottomCenter)
                            .offset { IntOffset(x = 0, y = -codeViewHeight + (dividerDragHeight / 2).dp.roundToPx()) }
                            .draggable(
                                interactionSource = dividerInteractionSource,
                                orientation = Orientation.Vertical,
                                state = rememberDraggableState { delta ->
                                    codeViewHeight -= delta.toInt()
                                }
                            )
                            .pointerHoverIcon(PointerIcon.Hand)
                            .hoverable(dividerInteractionSource),

                        color = if (dividerHovered) MaterialTheme.colors.primary else Color.Transparent
                    )
                }
            }
        }

        PropertiesPanel(
            activeTemplate = activeTemplate,
            parentElement = parentElement,
            parentModifiersList = elementModifiersList,
            childScopeModifiersList = childScopeModifiersList,
            childModifiersList = childModifiersList,
            onTemplateChange = onTemplateChange,
            onTemplateReset = {
                parentElement = activeTemplate.parentElement
                elementModifiersList = activeTemplate.parentModifiers.toList()
                childModifiersList = activeTemplate.childModifiers.toList()
                childScopeModifiersList = activeTemplate.childScopeModifiers.toList()
            },
            onParentElementChange = { element ->
                if (parentElement.type != element.type) {
                    childScopeModifiersList = childScopeModifiersList.map { listOf() }
                }
                parentElement = element
            },
            onParentModifiersChange = { newList ->
                elementModifiersList = newList
            },
            onChildScopeModifiersChange = { newModifiers, index ->
                childScopeModifiersList = childScopeModifiersList.mapIndexed { idx, list ->
                    if (idx == index) newModifiers else list
                }
            },
            onChildModifiersChange = { newModifiers, index ->
                childModifiersList = childModifiersList.mapIndexed { idx, list ->
                    if (idx == index) newModifiers else list
                }
            }
        )
    }
}