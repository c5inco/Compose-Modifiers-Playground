package com.c5inco.modifiers.plugin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.unit.IntSize
import com.c5inco.modifiers.Application
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.FrameWrapper
import com.intellij.openapi.util.IconLoader
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
                    ComposeSizeAdjustmentWrapper(
                        window = dialog,
                        panel = this,
                        preferredSize = IntSize(1100, 800)
                    ) {
                        PluginTheme {
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
}

object PluginIcons {
    @JvmField
    val PlaygroundAction = IconLoader.getIcon("/icons/menu-icon.svg", javaClass)
}
