package com.c5inco.modifiers.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
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
            DpInput(elevationValue, onValueChange = {
                onChange(ShadowModifierData(it, shapeValue, cornerValue))
            })
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
            DpInput(widthValue, onValueChange = {
                onChange(SizeModifierData(it, heightValue))
            })
            DpInput(heightValue, onValueChange = {
                onChange(SizeModifierData(widthValue, it))
            })
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
            DpInput(widthValue, onValueChange = {
                onChange(BorderModifierData(it, colorValue, shapeValue, cornerValue))
            })
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
            DpInput(allValue, onValueChange = {
                onChange(PaddingModifierData(it))
            })
        }
    }
}

@Composable
fun OffsetDesignModifier(xValue: Int, yValue: Int, onChange: (OffsetDesignModifierData) -> Unit) {
    Column {
        ModifierLabel("offset")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DpInput(xValue, onValueChange = {
                onChange(OffsetDesignModifierData(it, yValue))
            })
            DpInput(yValue, onValueChange = {
                onChange(OffsetDesignModifierData(xValue, it))
            })
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
            FloatInput(degreesValue, onValueChange = {
                onChange(RotateModifierData(it))
            })
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
private fun ModifierLabel(text: String) {
    Text(
        text,
        modifier = Modifier.padding(bottom = 4.dp),
        fontSize = 12.sp,
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
    )
}