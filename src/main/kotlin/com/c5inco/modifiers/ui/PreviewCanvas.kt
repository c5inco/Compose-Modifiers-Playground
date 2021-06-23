package com.c5inco.modifiers.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.CodeOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.c5inco.modifiers.data.*
import com.c5inco.modifiers.utils.DotsBackground

@Composable
fun PreviewCanvas(
    modifier: Modifier = Modifier,
    parentElement: ElementModel,
    childElements: List<Any>,
    childScopeModifiersList: List<List<Pair<Any, Boolean>>>,
    childModifiersList: List<List<Pair<Any, Boolean>>>,
    elementModifiersList: List<Pair<Any, Boolean>>,
    showCode: Boolean,
    onShowCode: (Boolean) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Subtle background
        DotsBackground(Modifier.align(Alignment.Center))

        val element = parentElement.type
        val elementModifiersChain = buildModifiers(elementModifiersList)

        when (element) {
            AvailableElements.Box -> {
                val data = parentElement.data as BoxElementData
                Box(
                    modifier = elementModifiersChain,
                    contentAlignment = getContentAlignments(data.contentAlignment)
                ) {
                    childElements.forEachIndexed { idx, childData ->
                        var sm: Modifier = Modifier
                        var scopeModifiers = childScopeModifiersList.getOrNull(idx)
                        var childModifiers = childModifiersList.getOrNull(idx)

                        scopeModifiers?.forEach {
                            val (data, visible) = it
                            if (visible) {
                                sm = sm.then(applyBoxModifiers(data))
                            }
                        }

                        emitChildren(childData, parentElement, sm, childModifiers)
                    }
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
                    childElements.forEachIndexed { idx, childData ->
                        var sm: Modifier = Modifier
                        var scopeModifiers = childScopeModifiersList.getOrNull(idx)
                        var childModifiers = childModifiersList.getOrNull(idx)

                        scopeModifiers?.forEach {
                            val (data, visible) = it
                            if (visible) {
                                sm = sm.then(applyColumnModifiers(data))
                            }
                        }

                        emitChildren(childData, parentElement, sm, childModifiers)
                    }
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
                    childElements.forEachIndexed { idx, childData ->
                        var sm: Modifier = Modifier
                        var scopeModifiers = childScopeModifiersList.getOrNull(idx)
                        var childModifiers = childModifiersList.getOrNull(idx)

                        scopeModifiers?.forEach {
                            val (data, visible) = it
                            if (visible) {
                                sm = sm.then(applyRowModifiers(data))
                            }
                        }

                        emitChildren(childData, parentElement, sm, childModifiers)
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp, bottom = 8.dp)
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .size(32.dp),
            elevation = 2.dp
        ) {

            IconButton(
                onClick = { onShowCode(!showCode) }
            ) {
                Icon(
                    imageVector = if (showCode) Icons.Outlined.CodeOff else Icons.Outlined.Code,
                    contentDescription = "Toggle code on or off",
                    tint = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                )
            }
        }
    }
}

@Composable
private fun emitChildren(
    data: Any,
    parentElement: ElementModel,
    scopeModifier: Modifier,
    modifiers: List<Pair<Any, Boolean>>?
) {
    when (data) {
        is TextChildData -> {
            val (text, style, alpha) = data
            TextChildElement(
                text,
                style,
                if (!parentElement.themeAware) Color.Black else LocalContentColor.current,
                alpha,
                scopeModifier.then(buildModifiers(modifiers))
            )
        }
        is ImageChildData -> {
            val (imagePath) = data
            ImageChildElement(imagePath, scopeModifier.then(buildModifiers(modifiers)))
        }
        else -> {
            val (emoji) = data as EmojiChildData
            EmojiChildElement(emoji, scopeModifier.then(buildModifiers(modifiers)))
        }
    }
}

private fun ColumnScope.applyColumnModifiers(
    data: Any,
): Modifier {
    when (data) {
        is WeightModifierData -> {
            val (weight) = data
            return Modifier.weight(weight)
        }
        is AlignColumnModifierData -> {
            val (alignment) = data
            return Modifier.align(getHorizontalAlignments(alignment))
        }
    }
    return Modifier
}

private fun RowScope.applyRowModifiers(
    data: Any,
): Modifier {
    when (data) {
        is WeightModifierData -> {
            val (weight) = data
            return Modifier.weight(weight)
        }
        is AlignRowModifierData -> {
            val (alignment) = data
            return Modifier.align(getVerticalAlignments(alignment))
        }
    }
    return Modifier
}

private fun BoxScope.applyBoxModifiers(
    data: Any,
): Modifier {
    when (data) {
        is AlignBoxModifierData -> {
            val (alignment) = data
            return Modifier.align(getContentAlignments(alignment))
        }
    }
    return Modifier
}

private fun buildModifiers(modifiersList: List<Pair<Any, Boolean>>?): Modifier {
    var modifier: Modifier = Modifier

    modifiersList?.forEach {
        val visible = it.second

        if (visible) {
            modifier = modifier.then(getModifier(it.first))
        }
    }

    return modifier
}