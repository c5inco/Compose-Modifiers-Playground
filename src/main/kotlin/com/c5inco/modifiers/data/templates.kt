package com.c5inco.modifiers.data

import androidx.compose.ui.graphics.Color

object Templates {
    val PinkSquare = Template(
        parentElement = ElementModel(
            AvailableElements.Row,
            RowElementData(
                horizontalArrangement = AvailableHorizontalArrangements.SpacedAround,
                verticalAlignment = AvailableVerticalAlignments.CenterVertically)
        ),
        parentModifiers = listOf(
            Pair(ShadowModifierData(elevation = 20, shape = AvailableShapes.RoundedCorner, corner = 60), true),
            Pair(SizeModifierData(width = 360, height = 360), true),
            Pair(BackgroundModifierData(color = Color.Magenta), true),
            Pair(PaddingModifierData(40), true),
            Pair(BorderModifierData(width = 20, color = Color.Cyan, shape = AvailableShapes.RoundedCorner, corner = 40), true),
            Pair(PaddingModifierData(20), true),
            Pair(BackgroundModifierData(color = Color.White), true),
        ),
        childModifiers = listOf(
            mutableListOf(
                Pair(BackgroundModifierData(color = Color.Red), true),
            ),
            mutableListOf(
                Pair(BackgroundModifierData(color = Color.Cyan), true),
            ),
            mutableListOf(
                Pair(BackgroundModifierData(color = Color.Green), true),
            )
        )
    )
}

data class Template(
    var parentElement: ElementModel,
    val parentModifiers: List<Pair<Any, Boolean>>,
    val childModifiers: List<MutableList<Pair<Any, Boolean>>>
)