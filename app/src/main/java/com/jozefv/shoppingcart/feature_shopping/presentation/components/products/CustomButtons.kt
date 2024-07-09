package com.jozefv.shoppingcart.feature_shopping.presentation.components.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerHorS
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.Action


@Composable
fun CustomRowCounterButton(
    modifier: Modifier = Modifier,
    resultValue: Int,
    buttonsVisible: Boolean = true,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = if (buttonsVisible) {
            modifier
                .fillMaxWidth()
                .alpha(1f)
        } else {
            modifier.alpha(0f)
        },
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomDecreaseButton(
            onClick = {
                onDecrease()
            })
        SpacerHorS()
        Text(
            text = "$resultValue",
            style = MaterialTheme.typography.bodyMedium
        )
        SpacerHorS()
        CustomIncreaseButton(
            onClick = {
                onIncrease()
            })
    }
}


@Composable
private fun CustomIncreaseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FilledTonalIconButton(
        modifier = modifier.size(32.dp),
        shape = MaterialTheme.shapes.small,
        colors = IconButtonDefaults.iconButtonColors().copy(containerColor = Action),
        onClick = {
            onClick()
        },
        content = {
            Text(text = "+")
        }
    )
}

@Composable
private fun CustomDecreaseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FilledTonalIconButton(
        modifier = modifier.size(32.dp),
        shape = MaterialTheme.shapes.small,
        colors = IconButtonDefaults.iconButtonColors().copy(containerColor = Action),
        onClick = {
            onClick()
        },
        content = {
            Text(text = "-")
        }
    )
}