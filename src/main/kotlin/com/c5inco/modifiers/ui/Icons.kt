package com.c5inco.modifiers.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.svgResource

object AppIcons {
    val Add: Painter
        @Composable
        get() {
            return loadVectorDrawable("add_circle")
        }

    val Code: Painter
        @Composable
        get() {
            return loadVectorDrawable("code")
        }

    val CodeOff: Painter
        @Composable
        get() {
            return loadVectorDrawable("code_off")
        }

    val ContentCopy: Painter
        @Composable
        get() {
            return loadVectorDrawable("content_copy")
        }

    val CropSquare: Painter
        @Composable
        get() {
            return loadVectorDrawable("crop_square")
        }

    val ExpandLess: Painter
        @Composable
        get() {
            return loadVectorDrawable("expand_less")
        }

    val ExpandMore: Painter
        @Composable
        get() {
            return loadVectorDrawable("expand_more")
        }

    val Fullscreen: Painter
        @Composable
        get() {
            return loadVectorDrawable("fullscreen")
        }

    val GridGoldenratio: Painter
        @Composable
        get() {
            return loadVectorDrawable("grid_goldenratio")
        }

    val LineWeight: Painter
        @Composable
        get() {
            return loadVectorDrawable("line_weight")
        }

    val Remove: Painter
        @Composable
        get() {
            return loadVectorDrawable("remove")
        }

    val RestartAlt: Painter
        @Composable
        get() {
            return loadVectorDrawable("restart_alt")
        }

    val RotateLeft: Painter
        @Composable
        get() {
            return loadVectorDrawable("rotate_left")
        }

    val RoundedCorner: Painter
        @Composable
        get() {
            return loadVectorDrawable("rounded_corner")
        }

    val SquareFoot: Painter
        @Composable
        get() {
            return loadVectorDrawable("square_foot")
        }

    val UnfoldMore: Painter
        @Composable
        get() {
            return loadVectorDrawable("unfold_more")
        }

    val Visibility: Painter
        @Composable
        get() {
            return loadVectorDrawable("visibility")
        }

    val VisibilityOff: Painter
        @Composable
        get() {
            return loadVectorDrawable("visibility_off")
        }
}

@Composable
private fun loadVectorDrawable(imageName: String): Painter {
    return svgResource("icons/material/${imageName}_black_24dp.svg")
}