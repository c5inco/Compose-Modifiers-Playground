package com.c5inco.modifiers.plugin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.unit.IntSize
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.FrameWrapper
import com.intellij.openapi.util.IconLoader
import data.Templates
import ui.Playground
import java.awt.Dimension
import javax.swing.JComponent

class PluginAction : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        DemoDialog(e.project).show()
    }

    class DemoDialog(project: Project?) : FrameWrapper(project) {
        init {
            title = "Modifiers Playground"
            component = createCenterPanel()
        }

        fun createCenterPanel(): JComponent {
            val dialog = this
            return ComposePanel().apply {
                preferredSize = Dimension(1100, 800)
                setContent {
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
