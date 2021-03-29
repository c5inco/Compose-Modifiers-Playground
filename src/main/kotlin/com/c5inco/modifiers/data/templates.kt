package com.c5inco.modifiers.data

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color

object Templates {
    val PinkSquare = Template(
        name = "Pink square",
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
        ),
        childScopeModifiers = listOf(
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        )
    )

    val Rainbow = Template(
        name = "Rainbow",
        parentElement = ElementModel(
            AvailableElements.Row,
            RowElementData(
                horizontalArrangement = AvailableHorizontalArrangements.Center,
                verticalAlignment = AvailableVerticalAlignments.CenterVertically)
        ),
        parentModifiers = listOf(
            Pair(OffsetDesignModifierData(y = -90), true),
            Pair(SizeModifierData(width = 360, height = 360), true),
            Pair(ClipModifierData(shape = AvailableShapes.Rectangle), true),
            Pair(OffsetDesignModifierData(y = 180), true),
            Pair(BackgroundModifierData(color = Color.Red, shape = AvailableShapes.Circle), true),
            Pair(PaddingModifierData(20), true),
            Pair(BackgroundModifierData(color = Color.Yellow, shape = AvailableShapes.Circle), true),
            Pair(PaddingModifierData(20), true),
            Pair(BackgroundModifierData(color = Color.Green, shape = AvailableShapes.Circle), true),
            Pair(PaddingModifierData(20), true),
            Pair(BackgroundModifierData(color = Color.Cyan, shape = AvailableShapes.Circle), true),
            Pair(PaddingModifierData(20), true),
            Pair(BackgroundModifierData(color = Color.Blue, shape = AvailableShapes.Circle), true),
            Pair(PaddingModifierData(20), true),
            Pair(BackgroundModifierData(color = Color.Magenta, shape = AvailableShapes.Circle), true),
        ),
        childModifiers = listOf(
            mutableListOf(
                Pair(OffsetDesignModifierData(y = -24), true),
            ),
            mutableListOf(
                Pair(OffsetDesignModifierData(y = -24), true),
            ),
            mutableListOf(
                Pair(OffsetDesignModifierData(y = -24), true),
            )
        ),
    )

    val Sun = Template(
        name = "Sun",
        parentElement = ElementModel(
            AvailableElements.Column,
            ColumnElementData(
                verticalArrangement = AvailableVerticalArrangements.SpacedEvenly,
                horizontalAlignment = AvailableHorizontalAlignments.CenterHorizontally)
        ),
        parentModifiers = listOf(
            Pair(SizeModifierData(width = 360, height = 360), true),
            Pair(BorderModifierData(width = 16, color = Color.Green, shape = AvailableShapes.RoundedCorner, corner = 32), true),
            Pair(RotateModifierData(30f), true),
            Pair(BorderModifierData(width = 16, color = Color.Blue, shape = AvailableShapes.RoundedCorner, corner = 32), true),
            Pair(RotateModifierData(30f), true),
            Pair(BorderModifierData(width = 16, color = Color.Red, shape = AvailableShapes.RoundedCorner, corner = 32), true),
            Pair(RotateModifierData(-60f), true),
            Pair(PaddingModifierData(42), true),
            Pair(BackgroundModifierData(color = Color.Magenta, shape = AvailableShapes.Circle), true),
        ),
    )

    val SimpleCard = Template(
        name = "Simple card",
        parentElement = ElementModel(
            AvailableElements.Column,
            ColumnElementData()
        ),
        parentModifiers = listOf(
            Pair(SizeModifierData(width = 320, height = 400), true),
            Pair(ShadowModifierData(elevation = 1, shape = AvailableShapes.RoundedCorner, corner = 4), true),
            Pair(BackgroundModifierData(color = Color.White, shape = AvailableShapes.RoundedCorner, corner = 4), true),
        ),
        childElements = listOf(
            ImageChildData("ic_placeholder.xml"),
            TextChildData("Card title", style = Typography().h6),
            TextChildData("Secondary text", style = Typography().body2, alpha = AvailableContentAlphas.Medium),
            TextChildData(
                "Greyhound divisively hello coldly wonderfully marginally far upon excluding.",
                style = Typography().body2,
                alpha = AvailableContentAlphas.Medium
            ),
        ),
        childModifiers = listOf(
            mutableListOf(
                Pair(BackgroundModifierData(color = Color.Gray), true),
                Pair(HeightModifierData(180), true),
                Pair(FillMaxWidthModifierData(), true),
            ),
            mutableListOf(
                Pair(PaddingModifierData(16, 16, 16, 0), true),
            ),
            mutableListOf(
                Pair(PaddingModifierData(16, 0), true),
            ),
            mutableListOf(
                Pair(PaddingModifierData(16, 16), true),
            ),
        ),
        childScopeModifiers = listOf(
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
        ),
    )
}

data class Template(
    val name: String,
    var parentElement: ElementModel,
    val parentModifiers: List<Pair<Any, Boolean>>,
    val childElements: List<Any> = listOf(
        EmojiChildData("🥑"),
        EmojiChildData("☕"),
        EmojiChildData("🤖"),
    ),
    val childModifiers: List<MutableList<Pair<Any, Boolean>>> = listOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    ),
    val childScopeModifiers: List<MutableList<Pair<Any, Boolean>>> = listOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    ),
) {
    override fun toString(): String {
        return name
    }
}