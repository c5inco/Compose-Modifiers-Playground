package com.c5inco.modifiers.plugin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.dp
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.FrameWrapper
import data.Templates
import ui.Playground
import utils.withTouchSlop
import java.awt.Dimension
import javax.swing.JComponent

class PluginAction : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { DemoDialog(it).show() }
    }

    class DemoDialog(
        private val project: Project
    ) : FrameWrapper(project) {
        init {
            title = "Modifiers Playground"
            component = createCenterPanel()
        }

        private fun createCenterPanel(): JComponent {
            return ComposePanel().apply {
                preferredSize = Dimension(1100, 800)
                setContent {
                    // Decrease the touch slop. The default value of too high for desktop
                    val vc = LocalViewConfiguration.current.withTouchSlop(
                        with(LocalDensity.current) { 0.125.dp.toPx() },
                    )

                    CompositionLocalProvider(LocalViewConfiguration provides vc) {
                        PluginTheme {
                            Thread.currentThread().contextClassLoader = PluginAction::class.java.classLoader
                            Surface(modifier = Modifier.fillMaxSize()) {
                                val defaultTemplate = Templates.Sun

                                var activeTemplate by remember { mutableStateOf(defaultTemplate) }

                                Playground(activeTemplate = activeTemplate, onTemplateChange = {
                                    activeTemplate = it.copy()
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
