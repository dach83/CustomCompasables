package com.github.dach83.customcomposables.components.otpinputfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpValue: String,
    otpCount: Int = 6,
    otpFloat: Int = 1,
    onOtpValueChange: (String, Boolean) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = otpValue,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpValueChange.invoke(it, it.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    val isFractional = index >= otpCount - otpFloat
                    OutlinedChar(
                        modifier = Modifier.width(40.dp),
                        index = index,
                        text = otpValue,
                        primary = if (isFractional) Color.Red else Color.LightGray,
                        secondary = if (isFractional) Color.Red else Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun OutlinedChar(
    modifier: Modifier = Modifier,
    index: Int,
    text: String,
    style: TextStyle = MaterialTheme.typography.displayMedium,
    borderWidth: Dp = 2.dp,
    borderShape: Shape = MaterialTheme.shapes.small,
    primary: Color = Color.LightGray,
    secondary: Color = Color.DarkGray
) {
    val isFocused = text.length == index
    val borderColor = if (isFocused) secondary else primary
    val textColor = if (isFocused) primary else secondary
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = modifier
            .border(
                width = borderWidth,
                color = borderColor,
                shape = borderShape
            )
            .padding(2.dp),
        text = char,
        style = style,
        color = textColor,
        textAlign = TextAlign.Center
    )
}
