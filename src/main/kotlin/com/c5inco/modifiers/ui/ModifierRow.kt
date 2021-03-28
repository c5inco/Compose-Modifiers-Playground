package com.c5inco.modifiers.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RotateLeft
import androidx.compose.material.icons.outlined.SquareFoot
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c5inco.modifiers.data.*

@Composable
fun AlphaModifier(alphaValue: Float, onChange: (AlphaModifierData) -> Unit) {
    Column {
        ModifierLabel("alpha")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            FloatInput(
                alphaValue,
                onValueChange = {
                    onChange(AlphaModifierData(it))
                }
            )
        }
    }
}

@Composable
fun ShadowModifier(elevationValue: Int, shapeValue: AvailableShapes, cornerValue: Int, onChange: (ShadowModifierData) -> Unit) {
    Column {
        ModifierLabel("shadow")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
}

@Composable
fun HeightModifier(heightValue: Int, onChange: (HeightModifierData) -> Unit) {
    Column {
        ModifierLabel("height")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DpInput(
                heightValue,
                onValueChange = {
                    onChange(HeightModifierData(it))
                }
            )
        }
    }
}

@Composable
fun WidthModifier(widthValue: Int, onChange: (WidthModifierData) -> Unit) {
    Column {
        ModifierLabel("width")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DpInput(
                widthValue,
                onValueChange = {
                    onChange(WidthModifierData(it))
                }
            )
        }
    }
}

@Composable
fun SizeModifier(widthValue: Int, heightValue: Int, onChange: (SizeModifierData) -> Unit) {
    Column {
        ModifierLabel("size")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
}

@Composable
fun BackgroundModifier(colorValue: Color, shapeValue: AvailableShapes, cornerValue: Int, onChange: (BackgroundModifierData) -> Unit) {
    Column {
        ModifierLabel("background")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            ColorInput(colorValue, onValueChange = { color ->
                onChange(BackgroundModifierData(color, shapeValue, cornerValue))
            })
            ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
                onChange(BackgroundModifierData(colorValue, shape, corner))
            })
        }
    }
}

@Composable
fun BorderModifier(widthValue: Int, colorValue: Color, shapeValue: AvailableShapes, cornerValue: Int, onChange: (BorderModifierData) -> Unit) {
    Column {
        ModifierLabel("border")
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
}

@Composable
fun PaddingModifier(allValue: Int, onChange: (PaddingModifierData) -> Unit) {
    Column {
        ModifierLabel("padding")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            DpInput(
                allValue,
                label = {
                    Text(
                        "all",
                        style = MaterialTheme.typography.body2,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                },
                onValueChange = {
                    onChange(PaddingModifierData(it))
                }
            )
        }
    }
}

@Composable
fun OffsetDesignModifier(xValue: Int, yValue: Int, onChange: (OffsetDesignModifierData) -> Unit) {
    Column {
        ModifierLabel("offset")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DpInput(
                xValue,
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
}

@Composable
fun ClickableModifier(enabledValue: Boolean, onChange: (ClickableModifierData) -> Unit) {
    Column {
        ModifierLabel("clickable")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Checkbox(
                checked = enabledValue,
                onCheckedChange = {
                    onChange(ClickableModifierData(it))
                }
            )
            Text("Enabled", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun ClipModifier(shapeValue: AvailableShapes, cornerValue: Int, onChange: (ClipModifierData) -> Unit) {
    Column {
        ModifierLabel("clip")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
                onChange(ClipModifierData(shape, corner))
            })
        }
    }
}

@Composable
fun RotateModifier(degreesValue: Float, onChange: (RotateModifierData) -> Unit) {
    Column {
        ModifierLabel("rotate")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
                onValueChange = {
                    onChange(RotateModifierData(it))
                }
            )
        }
    }
}

@Composable
fun ScaleModifier(scaleValue: Float, onChange: (ScaleModifierData) -> Unit) {
    Column {
        ModifierLabel("scale")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
}

@Composable
fun FillMaxWidthModifier(fractionValue: Float, onChange: (FillMaxWidthModifierData) -> Unit) {
    Column {
        ModifierLabel("fillMaxWidth")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            FloatInput(fractionValue, onValueChange = {
                onChange(FillMaxWidthModifierData(it))
            })
        }
    }
}

@Composable
fun FillMaxHeightModifier(fractionValue: Float, onChange: (FillMaxHeightModifierData) -> Unit) {
    Column {
        ModifierLabel("fillMaxHeight")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            FloatInput(fractionValue, onValueChange = {
                onChange(FillMaxHeightModifierData(it))
            })
        }
    }
}

@Composable
fun FillMaxSizeModifier(fractionValue: Float, onChange: (FillMaxSizeModifierData) -> Unit) {
    Column {
        ModifierLabel("fillMaxSize")
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            FloatInput(fractionValue, onValueChange = {
                onChange(FillMaxSizeModifierData(it))
            })
        }
    }
}

@Composable
fun WrapContentHeightModifier(unboundedValue: Boolean, onChange: (WrapContentHeightModifierData) -> Unit) {
    Column {
        ModifierLabel("wrapContentHeight")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Checkbox(
                checked = unboundedValue,
                onCheckedChange = {
                    onChange(WrapContentHeightModifierData(it))
                }
            )
            Text("Unbounded", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun WrapContentWidthModifier(unboundedValue: Boolean, onChange: (WrapContentWidthModifierData) -> Unit) {
    Column {
        ModifierLabel("wrapContentWidth")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Checkbox(
                checked = unboundedValue,
                onCheckedChange = {
                    onChange(WrapContentWidthModifierData(it))
                }
            )
            Text("Unbounded", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun WrapContentSizeModifier(unboundedValue: Boolean, onChange: (WrapContentSizeModifierData) -> Unit) {
    Column {
        ModifierLabel("wrapContentSize")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Checkbox(
                checked = unboundedValue,
                onCheckedChange = {
                    onChange(WrapContentSizeModifierData(it))
                }
            )
            Text("Unbounded", style = MaterialTheme.typography.body2)
        }
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