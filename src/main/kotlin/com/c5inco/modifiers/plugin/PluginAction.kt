package com.c5inco.modifiers.plugin

import androidx.compose.desktop.ComposePanel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import com.c5inco.modifiers.Application
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import java.awt.Dimension
import javax.swing.JComponent

class PluginAction : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        DemoDialog(e.project).show()
    }

    class DemoDialog(project: Project?) : DialogWrapper(project) {
        init {
            title = "Modifiers Playground"
            init()
        }

        override fun createCenterPanel(): JComponent {
            val dialog = this
            return ComposePanel().apply {
                preferredSize = Dimension(1100, 800)
                setContent {
                    ComposeSizeAdjustmentWrapper(
                        window = dialog,
                        panel = this,
                        preferredSize = IntSize(1100, 800)
                    ) {
                        Thread.currentThread().contextClassLoader = PluginAction::class.java.classLoader
                        Surface(modifier = Modifier.fillMaxSize()) {
                            Application()
                        }
                    }
                }
            }
        }
    }
}
