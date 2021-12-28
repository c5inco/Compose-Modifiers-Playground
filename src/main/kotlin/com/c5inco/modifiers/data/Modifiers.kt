package com.c5inco.modifiers.data

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

enum class AvailableShapes {
    Circle,
    RoundedCorner,
    CutCorner,
    Rectangle
}

fun getShape(shape: AvailableShapes, corner: Int): Shape = (
    when (shape) {
        AvailableShapes.Circle -> CircleShape
        AvailableShapes.RoundedCorner -> RoundedCornerShape(size = corner.dp)
        AvailableShapes.CutCorner -> CutCornerShape(size = corner.dp)
        else -> RectangleShape
    }
)

enum class AvailableHorizontalArrangements {
    Start,
    End,
    Center,
    SpaceEvenly,
    SpaceAround,
    SpaceBetween,
    SpacedBy
}

fun getHorizontalArrangementObject(arrangement: AvailableHorizontalArrangements, spacing: Int): Arrangement.Horizontal = (
    when (arrangement) {
        AvailableHorizontalArrangements.End -> Arrangement.End
        AvailableHorizontalArrangements.Center -> Arrangement.Center
        AvailableHorizontalArrangements.SpaceEvenly -> Arrangement.SpaceEvenly
        AvailableHorizontalArrangements.SpaceAround -> Arrangement.SpaceAround
        AvailableHorizontalArrangements.SpaceBetween -> Arrangement.SpaceBetween
        AvailableHorizontalArrangements.SpacedBy -> Arrangement.spacedBy(spacing.dp)
        else -> Arrangement.Start
    }
)

enum class AvailableVerticalArrangements {
    Top,
    Bottom,
    Center,
    SpaceEvenly,
    SpaceAround,
    SpaceBetween,
    SpacedBy
}

fun getVerticalArrangementObject(arrangement: AvailableVerticalArrangements, spacing: Int): Arrangement.Vertical = (
    when (arrangement) {
        AvailableVerticalArrangements.Bottom -> Arrangement.Bottom
        AvailableVerticalArrangements.Center -> Arrangement.Center
        AvailableVerticalArrangements.SpaceEvenly -> Arrangement.SpaceEvenly
        AvailableVerticalArrangements.SpaceAround -> Arrangement.SpaceAround
        AvailableVerticalArrangements.SpaceBetween -> Arrangement.SpaceBetween
        AvailableVerticalArrangements.SpacedBy -> Arrangement.spacedBy(spacing.dp)
        else -> Arrangement.Top
    }
)

enum class AvailableHorizontalAlignments {
    Start,
    End,
    CenterHorizontally
}

fun getHorizontalAlignments(alignment: AvailableHorizontalAlignments): Alignment.Horizontal = (
    when (alignment) {
        AvailableHorizontalAlignments.End -> {
            Alignment.End
        }
        AvailableHorizontalAlignments.CenterHorizontally -> {
            Alignment.CenterHorizontally
        }
        else -> {
            Alignment.Start
        }
    }
)

enum class AvailableVerticalAlignments {
    Top,
    Bottom,
    CenterVertically
}

fun getVerticalAlignments(alignment: AvailableVerticalAlignments): Alignment.Vertical = (
    when (alignment) {
        AvailableVerticalAlignments.Bottom -> {
            Alignment.Bottom
        }
        AvailableVerticalAlignments.CenterVertically -> {
            Alignment.CenterVertically
        }
        else -> {
            Alignment.Top
        }
    }
)

enum class AvailableContentAlignments {
    TopStart,
    TopEnd,
    TopCenter,
    CenterStart,
    CenterEnd,
    Center,
    BottomStart,
    BottomEnd,
    BottomCenter
}

fun getContentAlignments(alignment: AvailableContentAlignments): Alignment = (
    when (alignment) {
        AvailableContentAlignments.TopEnd -> {
            Alignment.TopEnd
        }
        AvailableContentAlignments.TopCenter -> {
            Alignment.TopCenter
        }
        AvailableContentAlignments.CenterStart -> {
            Alignment.CenterStart
        }
        AvailableContentAlignments.CenterEnd -> {
            Alignment.CenterEnd
        }
        AvailableContentAlignments.Center -> {
            Alignment.Center
        }
        AvailableContentAlignments.BottomStart -> {
            Alignment.BottomStart
        }
        AvailableContentAlignments.BottomEnd -> {
            Alignment.BottomEnd
        }
        AvailableContentAlignments.BottomCenter -> {
            Alignment.BottomCenter
        }
        else -> {
            Alignment.TopStart
        }
    }
)

enum class AvailablePadding {
    All,
    Sides,
    Individual
}

enum class ModifierChangeEvent {
    ADD,
    REMOVE,
    REORDER,
    EDIT
}

data class ModifierEntryData(
    val order: Int,
    val data: Pair<Any, Boolean>
)

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

data class AlphaModifierData(
    val alpha: Float = 1f
)

data class HeightModifierData(
    val height: Int = 0
)

data class WidthModifierData(
    val width: Int = 0
)

data class SizeModifierData(
    val width: Int = 0,
    val height: Int = 0
)

data class FillMaxWidthModifierData(
    val fraction: Float = 1f
)

data class FillMaxHeightModifierData(
    val fraction: Float = 1f
)

data class FillMaxSizeModifierData(
    val fraction: Float = 1f
)

data class BackgroundModifierData(
    val color: Color = Color.Yellow,
    val shape: AvailableShapes = AvailableShapes.Rectangle,
    val corner: Int = 0
)

data class BorderModifierData(
    val width: Int = 2,
    val color: Color = Color.Blue,
    val shape: AvailableShapes = AvailableShapes.Rectangle,
    val corner: Int = 0
)

data class PaddingModifierData(
    val type: AvailablePadding = AvailablePadding.All,
    val corners: CornerValues = CornerValues()
) {
    constructor(all: Int) : this(
        corners = CornerValues(all)
    )
    constructor(horizontal: Int, vertical: Int) : this(
        type = AvailablePadding.Sides,
        corners = CornerValues(horizontal, vertical)
    )
    constructor(start: Int, top: Int, end: Int, bottom: Int) : this(
        type = AvailablePadding.Individual,
        corners = CornerValues(start, top, end, bottom)
    )
}

data class CornerValues(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0,
) {
    constructor(all: Int) : this(
        all, all, all, all
    )
    constructor(horizontal: Int, vertical: Int) : this(
        horizontal, vertical, horizontal, vertical
    )
}

data class ShadowModifierData(
    val elevation: Int = 0,
    val shape: AvailableShapes = AvailableShapes.Rectangle,
    val corner: Int = 0
)

data class OffsetDesignModifierData(
    val x: Int = 0,
    val y: Int = 0
)

data class ClickableModifierData(
    val enabled: Boolean = true
)

data class ClipModifierData(
    val shape: AvailableShapes = AvailableShapes.Rectangle,
    val corner: Int = 0
)

data class RotateModifierData(
    val degrees: Float = 0f
)

data class ScaleModifierData(
    val scale: Float = 1f
)

data class AspectRatioModifierData(
    val ratio: Float = 1f
)

data class WeightModifierData(
    val weight: Float = 1f
)

data class AlignBoxModifierData(
    val alignment: AvailableContentAlignments = AvailableContentAlignments.TopStart
)

data class AlignColumnModifierData(
    val alignment: AvailableHorizontalAlignments = AvailableHorizontalAlignments.Start
)

data class AlignRowModifierData(
    val alignment: AvailableVerticalAlignments = AvailableVerticalAlignments.Top
)

data class WrapContentHeightModifierData(
    val unbounded: Boolean = false
)

data class WrapContentWidthModifierData(
    val unbounded: Boolean = false
)

data class WrapContentSizeModifierData(
    val unbounded: Boolean = false
)

fun getModifier(data: Any): Modifier = (
    when (data) {
        is AlphaModifierData -> {
            val (alpha) = data
            Modifier.alpha(alpha)
        }
        is HeightModifierData -> {
            val (height) = data
            Modifier.height(height.dp)
        }
        is WidthModifierData -> {
            val (width) = data
            Modifier.width(width.dp)
        }
        is SizeModifierData -> {
            val (width, height) = data
            Modifier.size(width = width.dp, height = height.dp)
        }
        is PaddingModifierData -> {
            val (type, corners) = data
            when (type) {
                AvailablePadding.Sides -> {
                    Modifier.padding(vertical = (corners.top).dp, horizontal = (corners.start).dp)
                }
                AvailablePadding.Individual -> {
                    Modifier.padding(
                        top = (corners.top).dp,
                        bottom = (corners.bottom).dp,
                        start = (corners.start).dp,
                        end = (corners.end).dp,
                    )
                }
                else -> {
                    Modifier.padding((corners.top).dp)
                }
            }
        }
        is BackgroundModifierData -> {
            val (color, shape, corner) = data
            Modifier.background(color = color, shape = getShape(shape, corner))
        }
        is BorderModifierData -> {
            val (width, color, shape, corner) = data
            Modifier.border(width = width.dp, color = color, shape = getShape(shape, corner))
        }
        is ShadowModifierData -> {
            val (elevation, shape, corner) = data
            Modifier.shadow(elevation = elevation.dp, shape = getShape(shape, corner))
        }
        is OffsetDesignModifierData -> {
            val (x, y) = data
            Modifier.offset(x = (x).dp, y = (y).dp)
        }
        is ClickableModifierData -> {
            val (enabled) = data
            Modifier.clickable(enabled, onClick = { })
        }
        is ClipModifierData -> {
            val (shape, corner) = data
            Modifier.clip(getShape(shape, corner))
        }
        is RotateModifierData -> {
            val (degrees) = data
            Modifier.rotate(degrees)
        }
        is ScaleModifierData -> {
            val (scale) = data
            Modifier.scale(scale)
        }
        is AspectRatioModifierData -> {
            val (ratio) = data
            Modifier.aspectRatio(ratio)
        }
        is FillMaxWidthModifierData -> {
            val (fraction) = data
            Modifier.fillMaxWidth(fraction)
        }
        is FillMaxHeightModifierData -> {
            val (fraction) = data
            Modifier.fillMaxHeight(fraction)
        }
        is FillMaxSizeModifierData -> {
            val (fraction) = data
            Modifier.fillMaxSize(fraction)
        }
        is WrapContentHeightModifierData -> {
            val (unbounded) = data
            Modifier.wrapContentHeight(unbounded = unbounded)
        }
        is WrapContentWidthModifierData -> {
            val (unbounded) = data
            Modifier.wrapContentWidth(unbounded = unbounded)
        }
        is WrapContentSizeModifierData -> {
            val (unbounded) = data
            Modifier.wrapContentSize(unbounded = unbounded)
        }
        else -> {
            Modifier
        }
    }
)

fun getNewModifierData(modifierType: ModifierEntry): Any = (
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
        ModifierEntry.AspectRatio -> {
            AspectRatioModifierData()
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

fun getNewScopeModifierData(modifierType: Any): Any = (
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