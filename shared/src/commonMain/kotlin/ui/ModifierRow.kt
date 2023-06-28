package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material.icons.outlined.RotateLeft
import androidx.compose.material.icons.outlined.SquareFoot
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.*
import ui.controls.*

@Composable
fun AlphaModifier(
    alphaValue: Float,
    onChange: (AlphaModifierData) -> Unit
) {
    ModifierRow(label = "alpha") {
        FloatInput(
            alphaValue,
            onValueChange = {
                onChange(AlphaModifierData(it))
            }
        )
    }
}

@Composable
fun ShadowModifier(
    elevationValue: Int,
    shapeValue: AvailableShapes,
    cornerValue: Int,
    onChange: (ShadowModifierData) -> Unit
) {
    ModifierRow(label = "shadow", arrangement = Arrangement.spacedBy(8.dp)) {
        DpInput(
            elevationValue,
            label = {
                Text(
                    "E",
                    style = MaterialTheme.typography.body2,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                )
            },
            onValueChange = {
                onChange(ShadowModifierData(it, shapeValue, cornerValue))
            }
        )
        ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
            onChange(ShadowModifierData(elevationValue, shape, corner))
        })
    }
}

@Composable
fun HeightModifier(
    heightValue: Int,
    onChange: (HeightModifierData) -> Unit
) {
    ModifierRow(label = "height") {
        DpInput(
            heightValue,
            onValueChange = {
                onChange(HeightModifierData(it))
            }
        )
    }
}

@Composable
fun WidthModifier(
    widthValue: Int,
    onChange: (WidthModifierData) -> Unit
) {
    ModifierRow(label = "width") {
        DpInput(
            widthValue,
            onValueChange = {
                onChange(WidthModifierData(it))
            }
        )
    }
}

@Composable
fun SizeModifier(
    widthValue: Int,
    heightValue: Int,
    onChange: (SizeModifierData) -> Unit
) {
    ModifierRow(label = "size") {
        DpInput(
            widthValue,
            label = {
                Text(
                    "W",
                    style = MaterialTheme.typography.body2,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                )
            },
            onValueChange = {
                onChange(SizeModifierData(it, heightValue))
            }
        )
        DpInput(
            heightValue,
            label = {
                Text(
                    "H",
                    style = MaterialTheme.typography.body2,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                )
            },
            onValueChange = {
                onChange(SizeModifierData(widthValue, it))
            }
        )
    }
}

@Composable
fun BackgroundModifier(
    colorValue: Color,
    shapeValue: AvailableShapes,
    cornerValue: Int,
    onChange: (BackgroundModifierData) -> Unit
) {
    ModifierRow(label = "background", arrangement = Arrangement.spacedBy(8.dp)) {
        ColorInput(colorValue, onValueChange = { color ->
            onChange(BackgroundModifierData(color, shapeValue, cornerValue))
        })
        ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
            onChange(BackgroundModifierData(colorValue, shape, corner))
        })
    }
}

@Composable
fun BorderModifier(
    widthValue: Int,
    colorValue: Color,
    shapeValue: AvailableShapes,
    cornerValue: Int,
    onChange: (BorderModifierData) -> Unit,
) {
    ModifierRow(label = "border", arrangement = Arrangement.spacedBy(8.dp)) {
        DpInput(
            widthValue,
            label = {
                Text(
                    "W",
                    style = MaterialTheme.typography.body2,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                )
            },
            onValueChange = {
                onChange(BorderModifierData(it, colorValue, shapeValue, cornerValue))
            }
        )
        ColorInput(colorValue, onValueChange = { color ->
            onChange(BorderModifierData(widthValue, color, shapeValue, cornerValue))
        })
        ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
            onChange(BorderModifierData(widthValue, colorValue, shape, corner))
        })
    }
}

@Composable
fun PaddingModifier(
    typeValue: AvailablePadding,
    cornerValues: CornerValues,
    onChange: (PaddingModifierData) -> Unit,
) {
    ModifierRow(label = "padding") {
        PaddingInput(
            typeValue = typeValue,
            cornerValues = cornerValues,
            onValueChange = { type, corners ->
                onChange(PaddingModifierData(type = type, corners = corners.copy()))
            }
        )
    }
}

@Composable
fun OffsetDesignModifier(
    xValue: Int,
    yValue: Int,
    onChange: (OffsetDesignModifierData) -> Unit,
) {
    ModifierRow(label = "offset") {
        DpInput(
            xValue,
            canBeNegative = true,
            label = {
                Text(
                    "X",
                    style = MaterialTheme.typography.body2,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                )
            },
            onValueChange = {
                onChange(OffsetDesignModifierData(it, yValue))
            }
        )
        DpInput(
            yValue,
            canBeNegative = true,
            label = {
                Text(
                    "Y",
                    style = MaterialTheme.typography.body2,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                )
            },
            onValueChange = {
                onChange(OffsetDesignModifierData(xValue, it))
            }
        )
    }
}

@Composable
fun ClickableModifier(
    enabledValue: Boolean,
    onChange: (ClickableModifierData) -> Unit,
) {
    ModifierRow(label = "clickable") {
        CheckboxInput(
            checked = enabledValue,
            onCheckedChange = {
                onChange(ClickableModifierData(it))
            }
        )
    }
}

@Composable
fun ClipModifier(
    shapeValue: AvailableShapes,
    cornerValue: Int,
    onChange: (ClipModifierData) -> Unit,
) {
    ModifierRow(label = "clip") {
        ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
            onChange(ClipModifierData(shape, corner))
        })
    }
}

@Composable
fun RotateModifier(
    degreesValue: Float,
    onChange: (RotateModifierData) -> Unit,
) {
    ModifierRow(label = "rotate") {
        FloatInput(
            degreesValue,
            label = {
                Icon(
                    imageVector = Icons.Outlined.RotateLeft,
                    contentDescription = "Rotate icon",
                    tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                    modifier = Modifier.size(18.dp)
                )
            },
            modifier = Modifier.width(64.dp),
            onValueChange = {
                onChange(RotateModifierData(it))
            }
        )
    }
}

@Composable
fun ScaleModifier(
    scaleValue: Float,
    onChange: (ScaleModifierData) -> Unit,
) {
    ModifierRow(label = "scale") {
        FloatInput(
            scaleValue,
            label = {
                Icon(
                    imageVector = Icons.Outlined.SquareFoot,
                    contentDescription = "Scale icon",
                    tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                    modifier = Modifier.size(18.dp)
                )
            },
            onValueChange = {
                onChange(ScaleModifierData(it))
            }
        )
    }
}

@Composable
fun AspectRatioModifier(
    ratioValue: Float,
    onChange: (AspectRatioModifierData) -> Unit,
) {
    ModifierRow(label = "aspectRatio") {
        FloatInput(
            ratioValue,
            onValueChange = {
                onChange(AspectRatioModifierData(it))
            }
        )
    }
}

@Composable
fun FillMaxWidthModifier(
    fractionValue: Float,
    onChange: (FillMaxWidthModifierData) -> Unit,
) {
    ModifierRow(label = "fillMaxWidth") {
        FloatInput(fractionValue, onValueChange = {
            onChange(FillMaxWidthModifierData(it))
        })
    }
}

@Composable
fun FillMaxHeightModifier(
    fractionValue: Float,
    onChange: (FillMaxHeightModifierData) -> Unit,
) {
    ModifierRow(label = "fillMaxHeight") {
        FloatInput(fractionValue, onValueChange = {
            onChange(FillMaxHeightModifierData(it))
        })
    }
}

@Composable
fun FillMaxSizeModifier(
    fractionValue: Float,
    onChange: (FillMaxSizeModifierData) -> Unit,
) {
    ModifierRow(label = "fillMaxSize") {
        FloatInput(fractionValue, onValueChange = {
            onChange(FillMaxSizeModifierData(it))
        })
    }
}

@Composable
fun WrapContentHeightModifier(
    unboundedValue: Boolean,
    onChange: (WrapContentHeightModifierData) -> Unit,
) {
    ModifierRow(label = "wrapContentHeight") {
        CheckboxInput(
            label = "Unbounded",
            checked = unboundedValue,
            onCheckedChange = {
                onChange(WrapContentHeightModifierData(it))
            }
        )
    }
}

@Composable
fun WrapContentWidthModifier(
    unboundedValue: Boolean,
    onChange: (WrapContentWidthModifierData) -> Unit,
) {
    ModifierRow(label = "wrapContentWidth") {
        CheckboxInput(
            label = "Unbounded",
            checked = unboundedValue,
            onCheckedChange = {
                onChange(WrapContentWidthModifierData(it))
            }
        )
    }
}

@Composable
fun WrapContentSizeModifier(
    unboundedValue: Boolean,
    onChange: (WrapContentSizeModifierData) -> Unit,
) {
    ModifierRow(label = "wrapContentSize") {
        CheckboxInput(
            label = "Unbounded",
            checked = unboundedValue,
            onCheckedChange = {
                onChange(WrapContentSizeModifierData(it))
            }
        )
    }
}

@Composable
fun WeightModifier(
    weightValue: Float,
    onChange: (WeightModifierData) -> Unit,
) {
    ModifierRow(label = "weight") {
        FloatInput(
            weightValue,
            label = {
                Icon(
                    imageVector = Icons.Outlined.LineWeight,
                    contentDescription = "Weight icon",
                    tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                    modifier = Modifier.size(18.dp)
                )
            },
            onValueChange = {
                onChange(WeightModifierData(it))
            }
        )
    }
}

@Composable
fun AlignBoxModifier(
    alignmentValue: AvailableContentAlignments,
    onChange: (AlignBoxModifierData) -> Unit,
) {
    ModifierRow(modifier = Modifier.fillMaxWidth(0.75f), label = "align") {
        ContentAlignmentInput(alignmentValue, onValueChange = {
            onChange(AlignBoxModifierData(it))
        })
    }
}

@Composable
fun AlignColumnModifier(
    alignmentValue: AvailableHorizontalAlignments,
    onChange: (AlignColumnModifierData) -> Unit,
) {
    ModifierRow(modifier = Modifier.fillMaxWidth(0.75f),label = "align") {
        HorizontalAlignmentInput(alignmentValue, onValueChange = {
            onChange(AlignColumnModifierData(it))
        })
    }
}

@Composable
fun AlignRowModifier(
    alignmentValue: AvailableVerticalAlignments,
    onChange: (AlignRowModifierData) -> Unit,
) {
    ModifierRow(modifier = Modifier.fillMaxWidth(0.75f), label = "align") {
        VerticalAlignmentInput(alignmentValue, onValueChange = {
            onChange(AlignRowModifierData(it))
        })
    }
}

@Composable
private fun ModifierLabel(text: String) {
    Text(
        text,
        modifier = Modifier.padding(bottom = 4.dp),
        fontSize = 12.sp,
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
    )
}

@Composable
private fun ModifierRow(
    modifier: Modifier = Modifier,
    label: String,
    arrangement: Arrangement.Horizontal = Arrangement.spacedBy(4.dp),
    content: @Composable RowScope.() -> Unit
) {
    Column {
        ModifierLabel(label)
        Row(
            modifier = modifier,
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}