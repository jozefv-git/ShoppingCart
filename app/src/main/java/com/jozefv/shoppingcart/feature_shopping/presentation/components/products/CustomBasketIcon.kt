package com.jozefv.shoppingcart.feature_shopping.presentation.components.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryAction
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryActionDisabled

@Composable
fun CustomBasketIcon(
    modifier: Modifier = Modifier,
    numberOfProducts: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            tint = if(numberOfProducts > 0 ) PrimaryAction else PrimaryActionDisabled,
            contentDescription = "Shopping Cart"
        )
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(if(numberOfProducts > 0) Color.Red else Color.Gray)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$numberOfProducts",
                color = Color.White,
                style = TextStyle().copy(fontSize = 12.sp)
            )
        }
    }
}

@Preview
@Composable
private fun CustomBasketIconPreview() {
    CustomBasketIcon(numberOfProducts = 5, onClick = {})
}