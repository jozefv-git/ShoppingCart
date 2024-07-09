package com.jozefv.shoppingcart.feature_shopping.presentation.components.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ShoppingCartTheme
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerVerS

@Composable
fun CustomProgressDialog(
    modifier: Modifier = Modifier,
    text: String = "Checking products availability..."
) {
    Dialog(
        onDismissRequest = {
            // Don't dismiss dialog
        }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(86.dp),
                    strokeWidth = 4.dp
                )
                SpacerVerS()
                Text(text = text)
            }
        }
    }
}

@Preview
@Composable
private fun CustomProgressDialogPreview(){
    ShoppingCartTheme {
        CustomProgressDialog()
    }
}