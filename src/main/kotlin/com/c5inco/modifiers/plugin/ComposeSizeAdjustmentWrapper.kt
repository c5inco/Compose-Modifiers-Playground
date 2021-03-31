package com.c5inco.modifiers.plugin

import androidx.compose.desktop.ComposePanel
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.intellij.openapi.ui.FrameWrapper
import java.awt.Dimension

@Composable
fun ComposeSizeAdjustmentWrapper(
    window: FrameWrapper,
    panel: ComposePanel,
    preferredSize: IntSize,
    content: @Composable () -> Unit
) {
    var packed = false
    Box {
        content()
        Layout(
            content = {},
            modifier = Modifier.onGloballyPositioned { childCoordinates ->
                // adjust size of the dialog
                if (!packed) {
                    panel.preferredSize = Dimension(
                        preferredSize.width,
                        preferredSize.height
                    )

                    packed = true
                }
            },
            measurePolicy = { _, _ ->
                layout(0, 0) {}
            }
        )
    }
}
