package com.github.dach83.customcomposables.components.otpinputfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun OtpTextFieldScreen() {
    var otpValue by remember {
        mutableStateOf("")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.fillMaxHeight(.3f))
        OtpTextField(
            otpValue = otpValue,
            onOtpValueChange = { value, filled ->
                otpValue = value
            }
        )
    }
}
