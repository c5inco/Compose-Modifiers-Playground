package com.c5inco.modifiers.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.c5inco.modifiers.data.*
import com.c5inco.modifiers.ui.controls.SmallIconButton

@Composable
fun ModifierEntry(
    modifierData: Pair<Any, Boolean>,
    order: Int,
    size: Int,
    move: (index: Int, up: Boolean) -> Unit,
    onChange: (Int, Pair<Any, Boolean>) -> Unit,
    onRemove: (Int) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val rowHovered by interactionSource.collectIsHoveredAsState()
    val visible = modifierData.second

    fun modifierChange(data: Any) {
        onChange(order, Pair(data, visible))
    }

    Box(
        modifier = Modifier
            .hoverable(interactionSource)
            //.height(48.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (rowHovered && size > 1) {
            Column(
                modifier = Modifier
                    .offset(x = (-18).dp)
            ) {
                val upRequester = remember { FocusRequester() }
                val downRequester = remember { FocusRequester() }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Move modifier up",
                    tint = LocalContentColor.current.copy(alpha = if (order != 0) 1f else ContentAlpha.disabled),
                    modifier = Modifier
                        .size(18.dp)
                        .focusRequester(upRequester)
                        .focusable()
                        .clickable(enabled = order != 0) {
                            move(order, true)
                            upRequester.requestFocus()
                        })
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Move modifier down",
                    tint = LocalContentColor.current.copy(alpha = if (order < size - 1) 1f else ContentAlpha.disabled),
                    modifier = Modifier
                        .size(18.dp)
                        .focusRequester(downRequester)
                        .focusable()
                        .clickable(enabled = order < size - 1) {
                            move(order, false)
                            downRequester.requestFocus()
                        }
                )
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(Modifier
                .alpha(if (visible) 1f else 0.4f)
                .weight(1f)
                .padding(end = 16.dp)
            ) {
                when (modifierData.first) {
                    is AlphaModifierData -> {
                        val (alpha) = modifierData.first as AlphaModifierData
                        AlphaModifier(
                            alphaValue = alpha,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is HeightModifierData -> {
                        val data = modifierData.first as HeightModifierData
                        HeightModifier(
                            heightValue = data.height,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is WidthModifierData -> {
                        val data = modifierData.first as WidthModifierData
                        WidthModifier(
                            widthValue = data.width,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is SizeModifierData -> {
                        val data = modifierData.first as SizeModifierData
                        SizeModifier(
                            widthValue = data.width,
                            heightValue = data.height,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is BackgroundModifierData -> {
                        val data = modifierData.first as BackgroundModifierData
                        BackgroundModifier(
                            colorValue = data.color,
                            shapeValue = data.shape,
                            cornerValue = data.corner,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is BorderModifierData -> {
                        val data = modifierData.first as BorderModifierData
                        BorderModifier(
                            widthValue = data.width,
                            colorValue = data.color,
                            shapeValue = data.shape,
                            cornerValue = data.corner,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is PaddingModifierData -> {
                        val data = modifierData.first as PaddingModifierData
                        PaddingModifier(
                            typeValue = data.type,
                            cornerValues = data.corners,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is ShadowModifierData -> {
                        val data = modifierData.first as ShadowModifierData
                        ShadowModifier(
                            elevationValue = data.elevation,
                            shapeValue = data.shape,
                            cornerValue = data.corner,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is OffsetDesignModifierData -> {
                        val data = modifierData.first as OffsetDesignModifierData
                        OffsetDesignModifier(
                            xValue = data.x,
                            yValue = data.y,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is ClickableModifierData -> {
                        val (enabled) = modifierData.first as ClickableModifierData
                        ClickableModifier(
                            enabledValue = enabled,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is ClipModifierData -> {
                        val (shape, corner) = modifierData.first as ClipModifierData
                        ClipModifier(
                            shapeValue = shape,
                            cornerValue = corner,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is RotateModifierData -> {
                        val (degrees) = modifierData.first as RotateModifierData
                        RotateModifier(
                            degreesValue = degrees,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is ScaleModifierData -> {
                        val (scale) = modifierData.first as ScaleModifierData
                        ScaleModifier(
                            scaleValue = scale,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is AspectRatioModifierData -> {
                        val (ratio) = modifierData.first as AspectRatioModifierData
                        AspectRatioModifier(
                            ratioValue = ratio,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is FillMaxWidthModifierData -> {
                        val (fraction) = modifierData.first as FillMaxWidthModifierData
                        FillMaxWidthModifier(
                            fractionValue = fraction,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is FillMaxHeightModifierData -> {
                        val (fraction) = modifierData.first as FillMaxHeightModifierData
                        FillMaxHeightModifier(
                            fractionValue = fraction,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is FillMaxSizeModifierData -> {
                        val (fraction) = modifierData.first as FillMaxSizeModifierData
                        FillMaxSizeModifier(
                            fractionValue = fraction,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is WrapContentHeightModifierData -> {
                        val (unbounded) = modifierData.first as WrapContentHeightModifierData
                        WrapContentHeightModifier(
                            unboundedValue = unbounded,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is WrapContentWidthModifierData -> {
                        val (unbounded) = modifierData.first as WrapContentWidthModifierData
                        WrapContentWidthModifier(
                            unboundedValue = unbounded,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is WrapContentSizeModifierData -> {
                        val (unbounded) = modifierData.first as WrapContentSizeModifierData
                        WrapContentSizeModifier(
                            unboundedValue = unbounded,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                }
            }
            Row {
                SmallIconButton(
                    onClick = {
                        onChange(order, Pair(modifierData.first, !visible))
                    }
                ) {
                    Icon(
                        imageVector = if (visible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = "Toggle visibility",
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                SmallIconButton(
                    requestFocus = false,
                    onClick = { onRemove(order) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Remove,
                        contentDescription = "Remove modifier",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
    if (order != size - 1) Divider(Modifier.padding(horizontal = 16.dp))
}

@Composable
fun RowColumnScopeModifierEntry(
    modifierData: Pair<Any, Boolean>,
    order: Int,
    size: Int,
    onModifierChange: (Int, Pair<Any, Boolean>) -> Unit,
    onRemove: (Int) -> Unit,
) {
    val visible = modifierData.second

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(Modifier
                .alpha(if (visible) 1f else 0.4f)
                .weight(1f)
                .padding(end = 16.dp)
            ) {
                fun modifierChange(data: Any) {
                    onModifierChange(order, Pair(data, visible))
                }

                val data = modifierData.first
                when (data) {
                    is WeightModifierData -> {
                        val (weight) = data
                        WeightModifier(
                            weightValue = weight,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is AlignBoxModifierData -> {
                        val (alignment) = data
                        AlignBoxModifier(
                            alignmentValue = alignment,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is AlignColumnModifierData -> {
                        val (alignment) = data
                        AlignColumnModifier(
                            alignmentValue = alignment,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                    is AlignRowModifierData -> {
                        val (alignment) = data
                        AlignRowModifier(
                            alignmentValue = alignment,
                            onChange = {
                                modifierChange(it.copy())
                            }
                        )
                    }
                }
            }
            Row {
                SmallIconButton(onClick = {
                    onModifierChange(order, Pair(modifierData.first, !visible))
                }) {
                    Icon(
                        imageVector = if (visible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = "Toggle visibility",
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                SmallIconButton(onClick = {
                    onRemove(order)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Remove,
                        contentDescription = "Remove modifier",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
    if (order != size - 1) Divider(Modifier.padding(horizontal = 16.dp))
}

enum class ModifierEntry {
    Alpha,
    AspectRatio,
    Background,
    Border,
    Clickable,
    Clip,
    Height,
    FillMaxHeight,
    FillMaxSize,
    FillMaxWidth,
    Offset,
    Padding,
    Rotate,
    Shadow,
    Size,
    Scale,
    Width,
    WrapContentHeight,
    WrapContentSize,
    WrapContentWidth,
}

enum class BoxScopeModifierEntry {
    Align,
}

enum class ColumnScopeModifierEntry {
    Align,
    Weight,
}

enum class RowScopeModifierEntry {
    Align,
    Weight,
}

fun getShape(shape: AvailableShapes, corner: Int): Shape = (
    when (shape) {
        AvailableShapes.Circle -> CircleShape
        AvailableShapes.RoundedCorner -> RoundedCornerShape(size = corner.dp)
        AvailableShapes.CutCorner -> CutCornerShape(size = corner.dp)
        else -> RectangleShape
    }
)