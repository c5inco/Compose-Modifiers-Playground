package data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment

enum class AvailableElements {
    Box,
    Column,
    Row,
}

data class BoxElementData(
    val contentAlignment: Alignment = Alignment.TopStart,
)

data class ColumnElementData(
    val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    val verticalSpacing: Int = 0,
    val horizontalAlignment: Alignment.Horizontal = Alignment.Start
)

data class RowElementData(
    val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    val horizontalSpacing: Int = 0,
    val verticalAlignment: Alignment.Vertical = Alignment.Top
)