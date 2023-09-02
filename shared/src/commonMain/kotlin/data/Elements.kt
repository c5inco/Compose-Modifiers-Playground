package data

import ui.ImageEmoji

enum class AvailableElements {
    Box,
    Column,
    Row,
}

data class BoxElementData(
    val contentAlignment: AvailableContentAlignments = AvailableContentAlignments.TopStart,
)

data class ColumnElementData(
    val verticalArrangement: AvailableVerticalArrangements = AvailableVerticalArrangements.Top,
    val verticalSpacing: Int = 0,
    val horizontalAlignment: AvailableHorizontalAlignments = AvailableHorizontalAlignments.Start
)

data class RowElementData(
    val horizontalArrangement: AvailableHorizontalArrangements = AvailableHorizontalArrangements.Start,
    val horizontalSpacing: Int = 0,
    val verticalAlignment: AvailableVerticalAlignments = AvailableVerticalAlignments.Top
)

data class ElementModel(
    val type: AvailableElements,
    val data: Any,
    val themeAware: Boolean = true
)

data class EmojiChildData(
    val emoji: String
)

data class ImageEmojiChildData(
    val emoji: ImageEmoji
)

data class TextChildData(
    val text: String,
    val style: TextChildStyle,
    val alpha: AvailableContentAlphas = AvailableContentAlphas.High
)

enum class TextChildStyle {
    BODY1,
    BODY2,
    H6,
    SUBTITLE1,
    SUBTITLE2
}

data class ImageChildData(
    val imagePath: String
)

enum class AvailableContentAlphas {
    High,
    Medium,
    Disabled
}