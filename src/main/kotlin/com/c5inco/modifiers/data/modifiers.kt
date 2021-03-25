package com.c5inco.modifiers.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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

val AvailableHorizontalAlignments: Map<Alignment.Horizontal, String> = mapOf(
    Alignment.Start to "Start",
    Alignment.End to "End",
    Alignment.CenterHorizontally to "CenterHorizontally",
)

val AvailableVerticalAlignments: Map<Alignment.Vertical, String> = mapOf(
    Alignment.Top to "Top",
    Alignment.Bottom to "Bottom",
    Alignment.CenterVertically to "CenterVertically",
)

val AvailableContentAlignments: Map<Alignment, String> = mapOf(
    Alignment.TopStart to "TopStart",
    Alignment.TopEnd to "TopEnd",
    Alignment.TopCenter to "TopCenter",
    Alignment.CenterStart to "CenterStart",
    Alignment.CenterEnd to "CenterEnd",
    Alignment.Center to "Center",
    Alignment.BottomStart to "BottomStart",
    Alignment.BottomEnd to "BottomEnd",
    Alignment.BottomCenter to "BottomCenter"
)

data class HorizontalArrangementData(
    val arrangement: AvailableHorizontalArrangements = AvailableHorizontalArrangements.Start,
    val spacing: Int = 0
)

data class VerticalArrangementData(
    val arrangement: AvailableVerticalArrangements = AvailableVerticalArrangements.Top,
    val spacing: Int = 0
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