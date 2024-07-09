package com.jozefv.shoppingcart.feature_shopping.presentation.components.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.shoppingcart.feature_shopping.domain.model.Product
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerHorS
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerVerL
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerVerS
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryAction
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryActionDisabled
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.Container
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ContainerLighter
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ShoppingCartTheme
import com.jozefv.shoppingcart.feature_shopping.presentation.products.SharedProductCartAction

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    productScreen: Boolean = true,
    onAction: (SharedProductCartAction) -> Unit
) {
    val productSelected = product.productQuantity > 0
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        onClick = {
            when (productSelected) {
                true -> {
                    onAction(
                        SharedProductCartAction.RemoveProductFromTheCart(
                            product
                        )
                    )
                }

                false -> onAction(SharedProductCartAction.IncreaseQuantity(product))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            Container,
                            ContainerLighter
                        )
                    )
                )
                .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = product.name, style = MaterialTheme.typography.headlineMedium)
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    tint = if (productSelected) PrimaryAction else PrimaryActionDisabled,
                    contentDescription = "Add to shopping cart"
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.type,
                style = MaterialTheme.typography.headlineSmall
            )
            SpacerVerL()
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = if (productSelected) Modifier else Modifier.fillMaxWidth(),
                    horizontalArrangement = if (productSelected) Arrangement.Start else Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (productScreen) "Price per product:" else "Total price: ",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    SpacerHorS()
                    Text(
                        text = "${if (productScreen) product.price else product.totalProductPrice} Eur",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                SpacerHorS()
                CustomRowCounterButton(
                    buttonsVisible = productSelected,
                    resultValue = product.productQuantity,
                    onIncrease = {
                        onAction(SharedProductCartAction.IncreaseQuantity(product))
                    },
                    onDecrease = {
                        onAction(SharedProductCartAction.DecreaseQuantity(product))
                    })
            }

        }
    }
    SpacerVerS()
}

@Preview
@Composable
private fun ProductItemPreview() {
    ShoppingCartTheme {
        ProductItem(
            product = Product(
                id = "154",
                name = "Samsung Galaxy S24",
                type = "256 GB. Blue pearl",
                price = 1836.0,
                productQuantity = 5,
                totalProductPrice = 9180.0
            ),
            onAction = {}
        )
    }
}