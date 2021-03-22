package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.*

@Composable
fun ShadowModifier(elevationValue: Int, shapeValue: AvailableShapes, cornerValue: Int, onChange: (ShadowModifierData) -> Unit) {
    Column {
        Text("Shadow", style = MaterialTheme.typography.overline)
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
        Text("Size", style = MaterialTheme.typography.overline)
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
        Text("Background", style = MaterialTheme.typography.overline)
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
        Text("Border", style = MaterialTheme.typography.overline)
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
        Text("Padding", style = MaterialTheme.typography.overline)
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
        Text("Offset", style = MaterialTheme.typography.overline)
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