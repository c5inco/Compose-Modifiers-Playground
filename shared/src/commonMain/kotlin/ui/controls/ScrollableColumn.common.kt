package ui.controls

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal expect fun ScrollableColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
)