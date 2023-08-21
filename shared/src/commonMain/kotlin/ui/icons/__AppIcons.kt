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
        __AllAssets = listOf(Circle, Rectangle, RoundedCorner, CutCorner)
        return __AllAssets!!
    }
