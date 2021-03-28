package com.c5inco.modifiers.data

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.c5inco.modifiers.ui.getShape

enum class AvailableShapes {
    Circle,
    RoundedCorner,
    CutCorner,
    Rectangle
}

enum class AvailableHorizontalArrangements {
    Start,
    End,
    Center,
    SpacedEvenly,
    SpacedAround,
    SpacedBetween,
    SpacedBy
}

fun getHorizontalArrangementObject(arrangement: AvailableHorizontalArrangements, spacing: Int): Arrangement.Horizontal {
    var ha: Arrangement.Horizontal = Arrangement.Start

    when (arrangement) {
        AvailableHorizontalArrangements.End -> ha = Arrangement.End
        AvailableHorizontalArrangements.Center -> ha = Arrangement.Center
        AvailableHorizontalArrangements.SpacedEvenly -> ha = Arrangement.SpaceEvenly
        AvailableHorizontalArrangements.SpacedAround -> ha = Arrangement.SpaceAround
        AvailableHorizontalArrangements.SpacedBetween -> ha = Arrangement.SpaceBetween
        AvailableHorizontalArrangements.SpacedBy -> ha = Arrangement.spacedBy(spacing.dp)
    }

    return ha
}

enum class AvailableVerticalArrangements {
    Top,
    Bottom,
    Center,
    SpacedEvenly,
    SpacedAround,
    SpacedBetween,
    SpacedBy
}

fun getVerticalArrangementObject(arrangement: AvailableVerticalArrangements, spacing: Int): Arrangement.Vertical {
    var va: Arrangement.Vertical = Arrangement.Top

    when (arrangement) {
        AvailableVerticalArrangements.Bottom -> va = Arrangement.Bottom
        AvailableVerticalArrangements.Center -> va = Arrangement.Center
        AvailableVerticalArrangements.SpacedEvenly -> va = Arrangement.SpaceEvenly
        AvailableVerticalArrangements.SpacedAround -> va = Arrangement.SpaceAround
        AvailableVerticalArrangements.SpacedBetween -> va = Arrangement.SpaceBetween
        AvailableVerticalArrangements.SpacedBy -> va = Arrangement.spacedBy(spacing.dp)
    }

    return va
}

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
    val all: Int = 0
)

data class ShadowModifierData(
    val elevation: Int = 0,
    val shape: AvailableShapes = AvailableShapes.Rectangle,
    val corner: Int = 0
)

data class OffsetDesignModifierData(
    val x: Int = 0,
    val y: Int = 0
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

data class WeightModifierData(
    val weight: Float = 1f
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
            val (all) = data
            Modifier.padding(all.dp)
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