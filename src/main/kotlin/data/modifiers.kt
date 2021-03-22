package data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

data class SizeModifierData(
    val width: Int = 0,
    val height: Int = 0
)

enum class AvailableShapes {
    Circle,
    RoundedCorner,
    CutCorner,
    Rectangle
}

val AvailableHorizontalArrangements: Map<Arrangement.Horizontal, String> = mapOf(
    Arrangement.Start to "Top",
    Arrangement.End to "Bottom",
    Arrangement.Center to "Center",
)

val AvailableVerticalArrangements: Map<Arrangement.Vertical, String> = mapOf(
    Arrangement.Top to "Top",
    Arrangement.Bottom to "Bottom",
    Arrangement.Center to "Center",
)

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