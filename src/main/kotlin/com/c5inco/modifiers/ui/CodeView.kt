package com.c5inco.modifiers.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c5inco.modifiers.data.*

@Composable
fun CodeView(
    modifier: Modifier = Modifier,
    elementModel: ElementModel,
    elementModifiers: List<Pair<Any, Boolean>>,
    childModifiersList: List<MutableList<Pair<Any, Boolean>>>
) {
    var editorHovered by remember { mutableStateOf( false) }

    SelectionContainer(modifier
        .pointerMoveFilter(
            onEnter = {
                editorHovered = true
                false
            },
            onExit = {
                editorHovered = false
                false
            }
        )
    ) {
        Box {
            var verticalScrollState = rememberScrollState(0)
            Column(
                Modifier
                    .fillMaxSize()
                    .background(EditorTheme.colors.backgroundDark)
                    .padding(16.dp)
                    .verticalScroll(verticalScrollState)
            ) {
                var code = ""

                when (elementModel.type) {
                    AvailableElements.Box -> {
                        val data = elementModel.data as BoxElementData
                        code += "Box(\n"
                        code += "\tcontentAlignment = Alignment.${AvailableContentAlignments[data.contentAlignment]},\n"
                    }
                    AvailableElements.Column -> {
                        val data = elementModel.data as ColumnElementData
                        code += "Column(\n"
                        code += "\tverticalArrangement = Arrangement.${getArrangementString(data.verticalArrangement, data.verticalSpacing)},\n"
                        code += "\thorizontalAlignment = Alignment.${AvailableHorizontalAlignments[data.horizontalAlignment]},\n"
                    }

                    AvailableElements.Row -> {
                        val data = elementModel.data as RowElementData
                        code += "Row(\n"
                        code += "\thorizontalArrangement = Arrangement.${getArrangementString(data.horizontalArrangement, data.horizontalSpacing)},\n"
                        code += "\tverticalAlignment = Alignment.${AvailableVerticalAlignments[data.verticalAlignment]},\n"
                    }
                }

                code += generateModifiers(elementModifiers, 1)
                code += ") {\n"

                val emojis = listOf("🥑", "☕", "🤖")
                emojis.forEachIndexed { idx, emoji ->
                    code += "\tText(\n"
                    code += "\t\t\"$emoji\",\n"
                    code += "\t\tfontSize = 48.sp,\n"
                    code += generateModifiers(childModifiersList[idx], indent = 2)
                    code += "\t)\n"
                }

                code += "}"

                Text(
                    formatCode(code),
                    fontSize = 14.sp,
                    fontFamily = Fonts.jetbrainsMono()
                )
            }

            if (editorHovered) {
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(verticalScrollState),
                )
            }
        }
    }
}

private fun getArrangementString(arrangement: Any, spacing: Int): String {
    if (arrangement == AvailableHorizontalArrangements.SpacedBy || arrangement == AvailableVerticalArrangements.SpacedBy) {
        return "spacedBy($spacing.dp)"
    }
    return arrangement.toString()
}

private fun generateModifiers(modifiers: List<Pair<Any, Boolean>>, indent: Int): String {
    val toPrint = modifiers.filter { (_, visible) -> visible }
    var str = ""

    val prependIndent: (Int) -> Unit = {
        for (i in 0 until it) { str += "\t" }
    }

    if (toPrint.isNotEmpty()) {
        prependIndent(indent)
        str += "modifier = Modifier\n"
        toPrint.forEach {
            val (data, visible) = it

            if (visible) {
                prependIndent(indent + 1)
                str += ".${lookupModifier(data)}\n"
            }
        }
    }

    return str
}

private fun lookupModifier(modifier: Any): String = (
    when (modifier) {
        is SizeModifierData -> {
            val (width, height) = modifier
            "size(width = $width.dp, height = $height.dp)"
        }
        is BackgroundModifierData -> {
            val (color, shape, corner) = modifier
            "background(color = ${generateColorString(color)}, shape = ${generateShapeString(shape, corner)})"
        }
        is BorderModifierData -> {
            val (width, color, shape, corner) = modifier
            "border(width = $width.dp, color = ${generateColorString(color)}, shape = ${generateShapeString(shape, corner)})"
        }
        is PaddingModifierData -> {
            val (all) = modifier
            "padding($all.dp)"
        }
        is ShadowModifierData -> {
            val (elevation, shape, corner) = modifier
            "shadow(elevation = $elevation.dp, shape = ${generateShapeString(shape, corner)})"
        }
        is OffsetDesignModifierData -> {
            val (x, y) = modifier
            "offset(x = ($x).dp, y = ($y).dp)"
        }
        is ClipModifierData -> {
            val (shape, corner) = modifier
            "clip(${generateShapeString(shape, corner)})"
        }
        is RotateModifierData -> {
            val (degrees) = modifier
            "rotate(${degrees}f)"
        }
        is ScaleModifierData -> {
            val (scale) = modifier
            "scale(${scale}f)"
        }
        is FillMaxWidthModifierData -> {
            val (fraction) = modifier
            "fillMaxWidth(${fraction}f)"
        }
        is FillMaxHeightModifierData -> {
            val (fraction) = modifier
            "fillMaxHeight(${fraction}f)"
        }
        is FillMaxSizeModifierData -> {
            val (fraction) = modifier
            "fillMaxSize(${fraction}f)"
        }
        else -> {
            ""
        }
    }
)

private fun generateColorString(color: Color): String = (
    when (color) {
        Color.Black -> "Color.Black"
        Color.Gray -> "Color.Gray"
        Color.White -> "Color.White"
        Color.Red -> "Color.Red"
        Color.Green -> "Color.Green"
        Color.Blue -> "Color.Blue"
        Color.Yellow -> "Color.Yellow"
        Color.Cyan -> "Color.Cyan"
        Color.Magenta -> "Color.Magenta"
        Color.Transparent -> "Color.Transparent"
        else -> "Color.Black"
    }
)

private fun generateShapeString(shape: AvailableShapes, corner: Int): String = (
    when (shape) {
        AvailableShapes.Circle -> "CircleShape"
        AvailableShapes.RoundedCorner -> "RoundedCornerShape(size = $corner.dp)"
        AvailableShapes.CutCorner -> "CutCornerShape(size = $corner.dp)"
        else -> "RectangleShape"
    }
)

private fun formatCode(str: String) = buildAnnotatedString {
    withStyle(EditorTheme.code.simple) {
        var strFormatted = str.replace("\t", "    ")
        append(strFormatted)
        addStyle(EditorTheme.code.punctuation, strFormatted, ":")
        addStyle(EditorTheme.code.punctuation, strFormatted, "=")
        addStyle(EditorTheme.code.punctuation, strFormatted, "\"")
        addStyle(EditorTheme.code.punctuation, strFormatted, "[")
        addStyle(EditorTheme.code.punctuation, strFormatted, "]")
        addStyle(EditorTheme.code.punctuation, strFormatted, "{")
        addStyle(EditorTheme.code.punctuation, strFormatted, "}")
        addStyle(EditorTheme.code.punctuation, strFormatted, "(")
        addStyle(EditorTheme.code.punctuation, strFormatted, ")")
        addStyle(EditorTheme.code.punctuation, strFormatted, ",")
        addStyle(EditorTheme.code.punctuation, strFormatted, ".")
        addStyle(EditorTheme.code.keyword, strFormatted, "fun ")
        addStyle(EditorTheme.code.keyword, strFormatted, "val ")
        addStyle(EditorTheme.code.keyword, strFormatted, "var ")
        addStyle(EditorTheme.code.value, strFormatted, "true")
        addStyle(EditorTheme.code.value, strFormatted, "false")
        addStyle(EditorTheme.code.value, strFormatted, Regex("(\\d+.dp)"))
        addStyle(EditorTheme.code.annotation, strFormatted, Regex("(.dp|.sp)"))
        addStyle(EditorTheme.code.annotation, strFormatted, Regex("^@[a-zA-Z_]*"))
        addStyle(EditorTheme.code.comment, strFormatted, Regex("^\\s*//.*"))
    }
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: String) {
    addStyle(style, text, Regex.fromLiteral(regexp))
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: Regex) {
    for (result in regexp.findAll(text)) {
        addStyle(style, result.range.first, result.range.last + 1)
    }
}