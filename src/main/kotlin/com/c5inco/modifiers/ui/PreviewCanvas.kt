package com.c5inco.modifiers.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.CodeOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.c5inco.modifiers.buildModifiers
import com.c5inco.modifiers.data.*
import com.c5inco.modifiers.utils.DotsBackground

@Composable
fun PreviewCanvas(
    modifier: Modifier = Modifier,
    parentElement: ElementModel,
    childElements: List<Any>,
    childScopeModifiersList: SnapshotStateList<MutableList<Pair<Any, Boolean>>>,
    childModifiersList: SnapshotStateList<MutableList<Pair<Any, Boolean>>>,
    elementModifiersList: SnapshotStateList<Pair<Any, Boolean>>,
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

        val content: @Composable () -> Unit = {
            childElements.forEachIndexed { idx, childData ->
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

                when (childData) {
                    is TextChildData -> {
                        val (text, style, alpha) = childData
                        TextChildElement(text, style, alpha, sm.then(buildModifiers(childModifiersList[idx])))
                    }
                    is ImageChildData -> {
                        val (imagePath) = childData
                        ImageChildElement(imagePath, sm.then(buildModifiers(childModifiersList[idx])))
                    }
                    else -> {
                        val (emoji) = childData as EmojiChildData
                        EmojiChildElement(emoji, sm.then(buildModifiers(childModifiersList[idx])))
                    }
                }
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
            onClick = { onShowCode(!showCode) },
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
}