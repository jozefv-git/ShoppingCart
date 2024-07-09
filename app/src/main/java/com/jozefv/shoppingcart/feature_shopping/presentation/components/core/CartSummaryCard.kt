package com.jozefv.shoppingcart.feature_shopping.presentation.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryAction
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ContainerLighter
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ShoppingCartTheme
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerHorM
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerVerM

@Composable
fun CartSummaryCard(
    modifier: Modifier = Modifier,
    areButtonsEnabled: Boolean = true,
    text: String = "To be paid:",
    totalEur: String,
    secondaryButtonText: String = "Continue shopping",
    primaryButtonText: String = "Checkout",
    secondaryButtonAction: () -> Unit,
    primaryButtonAction: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ContainerLighter, MaterialTheme.shapes.small)
            .border(1.dp, PrimaryAction, MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "$totalEur Eur",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        SpacerVerM()
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.small,
                onClick = {
                    secondaryButtonAction()
                },
                enabled = areButtonsEnabled
            ) {
                Text(text = secondaryButtonText)
            }
            SpacerHorM()
            Button(
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.small,
                onClick = {
                    primaryButtonAction()
                },
                enabled = areButtonsEnabled
            ) {
                Text(text = primaryButtonText)
            }
        }
    }
}

@Preview
@Composable
private fun CartSummaryCardPreview() {
    ShoppingCartTheme {
        CartSummaryCard(
            areButtonsEnabled = true,
            secondaryButtonAction = {},
            totalEur = "2548.0",
            primaryButtonAction = {})
    }
}