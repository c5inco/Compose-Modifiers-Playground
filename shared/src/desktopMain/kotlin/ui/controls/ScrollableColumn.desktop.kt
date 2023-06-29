package ui.controls

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal actual fun ScrollableColumn(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    val scrollThumbColor = Color.LightGray
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }
    val editorHovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier.hoverable(interactionSource)
    ) {
        Column(modifier.verticalScroll(scrollState)) {
            content()
        }
        if (editorHovered) {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                style = ScrollbarStyle(
                    minimalHeight = 16.dp,
                    thickness = 8.dp,
                    shape = MaterialTheme.shapes.small,
                    hoverDurationMillis = 300,
                    unhoverColor = scrollThumbColor.copy(alpha = 0.4f),
                    hoverColor = scrollThumbColor.copy(alpha = 0.6f)
                ),
                adapter = rememberScrollbarAdapter(scrollState),
            )
        }
    }
}