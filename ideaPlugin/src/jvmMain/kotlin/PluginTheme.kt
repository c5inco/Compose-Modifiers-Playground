import androidx.compose.runtime.Composable
import com.c5inco.modifiers.intellij.SwingColors
import com.c5inco.modifiers.ui.PlaygroundTheme
import com.c5inco.modifiers.ui.theme.appDarkColors
import com.c5inco.modifiers.ui.theme.appLightColors
import com.intellij.util.ui.UIUtil

@Composable
fun PluginTheme(
    content: @Composable () -> Unit
) {
    val swingColors = SwingColors()
    val appColors = if (UIUtil.isUnderDarcula()) appDarkColors else appLightColors

    PlaygroundTheme(
        colors = appColors.copy(
            background = swingColors.background,
            onBackground = swingColors.onBackground,
            surface = swingColors.surface,
            onSurface = swingColors.onSurface,
        ),
        content = content
    )
}