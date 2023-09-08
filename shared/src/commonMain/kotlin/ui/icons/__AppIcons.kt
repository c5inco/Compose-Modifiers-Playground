package ui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.collections.List as ____KtList

object AppIcons

private var __AllAssets: ____KtList<ImageVector>? = null

val AppIcons.AllAssets: ____KtList<ImageVector>
    get() {
        if (__AllAssets != null) {
            return __AllAssets!!
        }
        __AllAssets = listOf(
            Add,
            Circle,
            Code,
            CodeOff,
            ContentCopy,
            CutCorner,
            Fullscreen,
            GridGoldenratio,
            KeyboardArrowDown,
            LineWeight,
            Placeholder,
            Rectangle,
            Remove,
            RestartAlt,
            RotateLeft,
            RoundedCorner,
            SquareFoot,
            Visibility,
            VisibilityOff
        )
        return __AllAssets!!
    }
