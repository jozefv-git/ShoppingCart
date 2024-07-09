package com.jozefv.shoppingcart.feature_shopping.presentation.components.checkout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryAction
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryActionDisabled
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ShoppingCartTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    label: String = "",
    supportiveText: String = "",
    singleLine: Boolean = true,
    isValid: Boolean? = false,
    textFieldValue: String,
    trailingIconAction: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    val isFocused = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused.value = it.isFocused
            },
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = leadingIcon.name,
                    tint = if (isFocused.value) PrimaryAction else PrimaryActionDisabled
                )
            }
        },
        trailingIcon = {
            if (trailingIcon != null && textFieldValue.isNotEmpty()) {
                IconButton(
                    onClick = { trailingIconAction() }) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = trailingIcon.name,
                        tint = if (isFocused.value) PrimaryAction else PrimaryActionDisabled
                    )
                }
            }
        },
        label = {
            Text(text = label)
        },
        supportingText = {
            Text(text = supportiveText)
        },
        singleLine = singleLine,
        isError = if (isValid != null) !isValid else false,
        value = textFieldValue,
        onValueChange = {
            onValueChange(it)
        })
}

@Preview
@Composable
private fun CustomTextFieldPreview() {
    ShoppingCartTheme {
        CustomTextField(
            textFieldValue = "This is my name",
            leadingIcon = Icons.Default.AccountCircle,
            trailingIcon = Icons.Default.Clear,
            trailingIconAction = {},
            supportiveText = "John Doe",
            label = "Your name",
            onValueChange = {})
    }
}