package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.*
import ui.controls.ScrollableColumn
import ui.icons.AppIcons
import ui.icons.ContentCopy
import ui.theme.EditorColors
import ui.theme.EditorTheme
import utils.copyString
import utils.formatCode

@Composable
fun CodeView(
    modifier: Modifier = Modifier,
    elementModel: ElementModel,
    elementModifiers: List<Pair<Any, Boolean>>,
    childElements: List<Any>,
    childModifiersList: List<List<Pair<Any, Boolean>>>,
    childScopeModifiersList: List<List<Pair<Any, Boolean>>>
) {
    var code = ""
    when (elementModel.type) {
        AvailableElements.Box -> {
            val data = elementModel.data as BoxElementData
            code += "Box(\n"
            code += "\tcontentAlignment = Alignment.${data.contentAlignment},\n"
        }

        AvailableElements.Column -> {
            val data = elementModel.data as ColumnElementData
            code += "Column(\n"
            code += "\tverticalArrangement = ${
                generateArrangementString(
                    data.verticalArrangement,
                    data.verticalSpacing
                )
            },\n"
            code += "\thorizontalAlignment = Alignment.${data.horizontalAlignment},\n"
        }

        AvailableElements.Row -> {
            val data = elementModel.data as RowElementData
            code += "Row(\n"
            code += "\thorizontalArrangement = ${
                generateArrangementString(
                    data.horizontalArrangement,
                    data.horizontalSpacing
                )
            },\n"
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

    Box(
        modifier
            .fillMaxSize()
            .background(EditorColors.backgroundDark)
    ) {
        ScrollableColumn(
            Modifier.fillMaxSize()
        ) {
            // TODO: SelectionContainer not supported web
            // SelectionContainer {
                EditorTheme {
                    Text(
                        formatCode(code),
                        fontSize = 14.sp,
                        //fontFamily = Fonts.jetbrainsMono(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            // }
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .size(32.dp),
            onClick = { copyString(code) }
        ) {
            Icon(
                imageVector = AppIcons.ContentCopy,
                contentDescription = "Copy code",
                tint = Color.White
            )
        }
    }
}

private fun generateArrangementString(arrangement: Any, spacing: Int): String {
    val str = "Arrangement."

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

private fun generateTextStyleString(style: TextChildStyle): String {
    var str = "MaterialTheme.typography."

    when (style) {
        TextChildStyle.H6 -> {
            str += "h6"
        }
        TextChildStyle.SUBTITLE1 -> {
            str += "subtitle1"
        }
        TextChildStyle.SUBTITLE2 -> {
            str += "subtitle2"
        }
        TextChildStyle.BODY2 -> {
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
            code += "\t\tstyle = ${generateTextStyleString(style)},\n"
            code += "\t\tcolor = LocalContentColor.current.copy(alpha = ContentAlpha.${alpha.toString().lowercase()}),\n"
        }
        is ImageChildData -> {
            val (imagePath) = element
            code += "\tImage(\n"
            code += "\t\timageVector = \"images/$imagePath\",\n"
            code += "\t\tcontentDescription = \"$imagePath image\",\n"
        }
        else -> {
            val (emoji) = element as ImageEmojiChildData
            code += "\tText(\n"
            code += "\t\t\"${convertImageEmojiToEmoji(emoji)}\",\n"
            code += "\t\tfontSize = 48.sp,\n"
        }
    }

    return code
}