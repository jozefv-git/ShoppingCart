package com.jozefv.shoppingcart.feature_shopping.presentation.components.core

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ShoppingCartTheme

@Composable
fun CustomAlertDialog(
    title: String = "Warning",
    text: String = "Do you want to empty your cart?",
    positiveButtonText: String = "Yes",
    negativeButtonText: String = "No",
    onPositiveAction: () -> Unit,
    onNegativeAction: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPositiveAction()
                }) {
                Text(text = positiveButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onNegativeAction()
                }) {
                Text(text = negativeButtonText)
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        })
}

@Preview
@Composable
fun CustomAlertDialogPreview() {
    ShoppingCartTheme {
        CustomAlertDialog(
            onPositiveAction = {},
            onNegativeAction = {},
            onDismissRequest = {})
    }
}