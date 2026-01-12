package com.driverhub.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.theme.*

/**
 * OTP Input Field Component
 * 6-digit OTP input with individual boxes
 */
@Composable
fun OTPInputField(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    digitCount: Int = 6,
    enabled: Boolean = true
) {
    BasicTextField(
        value = otp,
        onValueChange = { newValue ->
            if (newValue.length <= digitCount && newValue.all { it.isDigit() }) {
                onOtpChange(newValue)
            }
        },
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.Small, Alignment.CenterHorizontally)
            ) {
                repeat(digitCount) { index ->
                    val char = when {
                        index >= otp.length -> ""
                        else -> otp[index].toString()
                    }
                    
                    val isFocused = index == otp.length && enabled
                    
                    Surface(
                        modifier = Modifier
                            .width(48.dp)
                            .height(56.dp)
                            .then(
                                if (isFocused) {
                                    Modifier.border(
                                        width = AppBorder.Medium,
                                        color = PrimaryBlue,
                                        shape = AppShapes.ButtonMedium
                                    )
                                } else {
                                    Modifier.border(
                                        width = AppBorder.Thin,
                                        color = if (char.isEmpty()) BorderLight else if (enabled) PrimaryBlue else BorderMedium,
                                        shape = AppShapes.ButtonMedium
                                    )
                                }
                            ),
                        shape = AppShapes.ButtonMedium,
                        color = if (enabled) SurfaceWhite else AppBackground
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = char,
                                fontSize = AppFontSize.HeadingLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (enabled) TextPrimary else TextTertiary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    )
}
