package com.c5inco.modifiers.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RotateLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c5inco.modifiers.data.*

@Composable
fun ShadowModifier(elevationValue: Int, shapeValue: AvailableShapes, cornerValue: Int, onChange: (ShadowModifierData) -> Unit) {
    Column {
        ModifierLabel("shadow")
        Row {
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
            Spacer(Modifier.width(16.dp))
            ShapeInput(shapeValue, cornerValue, onValueChange = { shape, corner ->
                onChange(ShadowModifierData(elevationValue, shape, corner))
            })
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
        Row {
            ColorInput(colorValue, onValueChange = { color ->
                onChange(BackgroundModifierData(color, shapeValue, cornerValue))
            })
            Spacer(Modifier.width(16.dp))
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
        Row {
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
            Spacer(Modifier.width(16.dp))
            ColorInput(colorValue, onValueChange = { color ->
                onChange(BorderModifierData(widthValue, color, shapeValue, cornerValue))
            })
            Spacer(Modifier.width(16.dp))
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
            FloatInput(scaleValue, onValueChange = {
                onChange(ScaleModifierData(it))
            })
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
private fun ModifierLabel(text: String) {
    Text(
        text,
        modifier = Modifier.padding(bottom = 4.dp),
        fontSize = 12.sp,
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
    )
}