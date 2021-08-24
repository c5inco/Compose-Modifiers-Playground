package com.c5inco.modifiers

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import com.c5inco.modifiers.data.Templates
import com.c5inco.modifiers.ui.Playground

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Application() {
    val defaultTemplate = Templates.Sun

    var activeTemplate by remember { mutableStateOf(defaultTemplate) }

    Playground(activeTemplate, onTemplateChange = {
        activeTemplate = it.copy()
    })
}