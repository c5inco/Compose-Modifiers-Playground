package com.c5inco.modifiers.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c5inco.modifiers.data.*
import com.c5inco.modifiers.ui.theme.EditorColors
import com.c5inco.modifiers.ui.theme.Fonts
import com.c5inco.modifiers.utils.formatCode
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

@Composable
fun CodeView(
    modifier: Modifier = Modifier,
    elementModel: ElementModel,
    elementModifiers: List<Pair<Any, Boolean>>,
    childElements: List<Any>,
    childModifiersList: MutableList<MutableList<Pair<Any, Boolean>>>,
    childScopeModifiersList: MutableList<MutableList<Pair<Any, Boolean>>>
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
            var code = ""
            var verticalScrollState = rememberScrollState(0)

            Column(
                Modifier
                    .fillMaxSize()
                    .background(EditorColors.backgroundDark)
                    .verticalScroll(verticalScrollState)
            ) {
                when (elementModel.type) {
                    AvailableElements.Box -> {
                        val data = elementModel.data as BoxElementData
                        code += "Box(\n"
                        code += "\tcontentAlignment = Alignment.${data.contentAlignment},\n"
                    }
                    AvailableElements.Column -> {
                        val data = elementModel.data as ColumnElementData
                        code += "Column(\n"
                        code += "\tverticalArrangement = ${generateArrangementString(data.verticalArrangement, data.verticalSpacing)},\n"
                        code += "\thorizontalAlignment = Alignment.${data.horizontalAlignment},\n"
                    }

                    AvailableElements.Row -> {
                        val data = elementModel.data as RowElementData
                        code += "Row(\n"
                        code += "\thorizontalArrangement = ${generateArrangementString(data.horizontalArrangement, data.horizontalSpacing)},\n"
                        code += "\tverticalAlignment = Alignment.${data.verticalAlignment},\n"
                    }
                }

                code += generateModifiersString(elementModifiers, 1)
                code += ") {\n"

                childElements.forEachIndexed { idx, child ->
                    code += generateChildElementString(child)

                    val allChildModifiers = childScopeModifiersList[idx].toMutableList()
                    allChildModifiers.addAll(childModifiersList[idx].toList())

                    code += generateModifiersString(allChildModifiers, indent = 2)
                    code += "\t)\n"
                }

                code += "}"

                Text(
                    formatCode(code),
                    fontSize = 14.sp,
                    fontFamily = Fonts.jetbrainsMono(),
                    modifier = Modifier.padding(16.dp)
                )
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
                        unhoverColor = Color.LightGray.copy(alpha = 0.4f),
                        hoverColor = Color.LightGray.copy(alpha = 0.6f)
                    ),
                    adapter = rememberScrollbarAdapter(verticalScrollState),
                )
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .size(32.dp),
                onClick = { copyCode(code) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ContentCopy,
                    contentDescription = "Copy code",
                    tint = Color.White
                )
            }
        }
    }
}


private fun copyCode(code: String) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(StringSelection(code), null)
}

private fun generateArrangementString(arrangement: Any, spacing: Int): String {
    var str = "Arrangement."

    if (arrangement == AvailableHorizontalArrangements.SpacedBy || arrangement == AvailableVerticalArrangements.SpacedBy) {
        return str + "spacedBy($spacing.dp)"
    }
    return str + arrangement.toString()
}

private fun generateModifiersString(modifiers: List<Pair<Any, Boolean>>, indent: Int): String {
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
        is AlphaModifierData -> {
            val (alpha) = modifier
            "alpha(${alpha}f)"
        }
        is HeightModifierData -> {
            val (height) = modifier
            "height($height.dp)"
        }
        is WidthModifierData -> {
            val (width) = modifier
            "width($width.dp)"
        }
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
            val (type, corners) = modifier
            when (type) {
                AvailablePadding.Sides -> {
                    "padding(horizontal = ${corners.start}.dp, vertical = ${corners.top}.dp)"
                }
                AvailablePadding.Individual -> {
                    "padding(start = ${corners.start}.dp, top = ${corners.top}.dp, end = ${corners.end}.dp, bottom = ${corners.bottom}.dp)"
                }
                else -> {
                    "padding(${corners.top}.dp)"
                }
            }
        }
        is ShadowModifierData -> {
            val (elevation, shape, corner) = modifier
            "shadow(elevation = $elevation.dp, shape = ${generateShapeString(shape, corner)})"
        }
        is OffsetDesignModifierData -> {
            val (x, y) = modifier
            "offset(x = ($x).dp, y = ($y).dp)"
        }
        is ClickableModifierData -> {
            val (enabled) = modifier
            "clickable($enabled, onClick = { })"
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
        is AspectRatioModifierData -> {
            val (ratio) = modifier
            "aspectRatio(${ratio}f)"
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
        is WrapContentWidthModifierData -> {
            val (unbounded) = modifier
            "wrapContentWidth(unbounded = $unbounded)"
        }
        is WrapContentHeightModifierData -> {
            val (unbounded) = modifier
            "wrapContentHeight(unbounded = $unbounded)"
        }
        is WrapContentSizeModifierData -> {
            val (unbounded) = modifier
            "wrapContentSize(unbounded = $unbounded)"
        }
        is WeightModifierData -> {
            val (weight) = modifier
            "weight(${weight}f)"
        }
        is AlignBoxModifierData -> {
            val (alignment) = modifier
            "align(Alignment.$alignment)"
        }
        is AlignColumnModifierData -> {
            val (alignment) = modifier
            "align(Alignment.$alignment)"
        }
        is AlignRowModifierData -> {
            val (alignment) = modifier
            "align(Alignment.$alignment)"
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

private fun generateTextStyleString(style: TextStyle): String {
    var str = "MaterialTheme.typography."

    when (style) {
        Typography().h6 -> {
            str += "h6"
        }
        Typography().subtitle1 -> {
            str += "subtitle1"
        }
        Typography().subtitle2 -> {
            str += "subtitle2"
        }
        Typography().body2 -> {
            str += "body2"
        }
        else -> {
            str += "body1"
        }
    }

    return str
}

private fun generateChildElementString(element: Any): String {
    var code = ""

    when (element) {
        is TextChildData -> {
            val (text, style, alpha) = element
            code += "\tText(\n"
            code += "\t\t\"$text\",\n"
            code += "\t\tstyle = ${generateTextStyleString(style)}),\n"
            code += "\t\tcolor = LocalContentColor.current.copy(alpha = ContentAlpha.${alpha.toString().toLowerCase()}),\n"
        }
        is ImageChildData -> {
            val (imagePath) = element
            code += "\tImage(\n"
            code += "\t\timageVector = \"images/$imagePath\",\n"
            code += "\t\tcontentDescription = \"$imagePath image\",\n"
        }
        else -> {
            val (emoji) = element as EmojiChildData
            code += "\tText(\n"
            code += "\t\t\"$emoji\",\n"
            code += "\t\tfontSize = 48.sp,\n"
        }
    }

    return code
}