package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.*
import ui.panel.PropertiesPanel

@Composable
fun Playground(
    modifier: Modifier = Modifier,
    activeTemplate: Template,
    onTemplateChange: (Template) -> Unit
) {
    var parentElement by remember(activeTemplate) { mutableStateOf(activeTemplate.parentElement) }
    var elementModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.parentModifiers) }
    val childElements = activeTemplate.childElements
    var childModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.childModifiers) }
    var childScopeModifiersList by remember(activeTemplate) { mutableStateOf(activeTemplate.childScopeModifiers) }

    var showCode by remember { mutableStateOf(false) }

    Row(modifier = modifier) {
        Surface(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.background
        ) {
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
                    Divider(color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled))
                    CodeView(
                        modifier = Modifier.weight(1f),
                        elementModel = parentElement,
                        elementModifiers = elementModifiersList,
                        childElements = childElements,
                        childModifiersList = childModifiersList,
                        childScopeModifiersList = childScopeModifiersList
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