package com.c5inco.modifiers.plugin.intellij

import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.c5inco.modifiers.ui.theme.appDarkColors
import com.c5inco.modifiers.ui.theme.appLightColors
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.util.ui.UIUtil
import javax.swing.UIManager
import java.awt.Color as AWTColor

interface SwingColor {
    val primary: Color
    val secondary: Color
    val background: Color
    val onBackground: Color
    val surface: Color
    val onSurface: Color
}

@Composable
fun SwingColor(): SwingColor {
    val swingColor = remember { SwingColorImpl() }

    val messageBus = remember {
        ApplicationManager.getApplication().messageBus.connect()
    }

    remember(messageBus) {
        messageBus.subscribe(
            LafManagerListener.TOPIC,
            ThemeChangeListener(swingColor::updateCurrentColors)
        )
    }

    DisposableEffect(messageBus) {
        onDispose {
            messageBus.disconnect()
        }
    }

    return swingColor
}

private class SwingColorImpl : SwingColor {
    private var _primaryState: MutableState<Color> = mutableStateOf(lightColors().primary)
    private var _secondaryState: MutableState<Color> = mutableStateOf(lightColors().secondary)
    private val _backgroundState: MutableState<Color> = mutableStateOf(getBackgroundColor)
    private val _onBackgroundState: MutableState<Color> = mutableStateOf(getOnBackgroundColor)
    private val _surfaceState: MutableState<Color> = mutableStateOf(getSurfaceColor)
    private val _onSurfaceState: MutableState<Color> = mutableStateOf(getOnSurfaceColor)

    override val primary: Color get() = _primaryState.value
    override val secondary: Color get() = _secondaryState.value
    override val background: Color get() = _backgroundState.value
    override val onBackground: Color get() = _onBackgroundState.value
    override val surface: Color get() = _surfaceState.value
    override val onSurface: Color get() = _onSurfaceState.value

    private val getBackgroundColor get() = getEditorBackground()
    private val getOnBackgroundColor get() = getColor(ON_SURFACE_KEY)
    private val getSurfaceColor get() = getColor(SURFACE_KEY)
    private val getOnSurfaceColor get() = getColor(ON_SURFACE_KEY)

    init {
        updateCurrentColors()
    }

    fun updateCurrentColors() {
        val appColors = if (UIUtil.isUnderDarcula()) appDarkColors else appLightColors

        if (UIUtil.isUnderDarcula()) {
            _backgroundState.value = getBackgroundColor
            _surfaceState.value = getSurfaceColor
        } else {
            _backgroundState.value = getSurfaceColor
            _surfaceState.value = getBackgroundColor
        }
        _onBackgroundState.value = getOnBackgroundColor
        _onSurfaceState.value = getOnSurfaceColor
        _primaryState.value = appColors.primary
        _secondaryState.value = appColors.secondary
    }

    private val AWTColor.asComposeColor: Color get() = Color(red, green, blue, alpha)
    private fun getColor(key: String): Color = UIManager.getColor(key).asComposeColor
    private fun getEditorBackground(): Color {
        val currentScheme = EditorColorsManager.getInstance().schemeForCurrentUITheme
        return currentScheme.defaultBackground.asComposeColor
    }

    companion object {
        private const val SURFACE_KEY = "Panel.background"
        private const val ON_SURFACE_KEY = "Panel.foreground"
    }
}