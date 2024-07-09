@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.shoppingcart.feature_shopping.presentation.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.shoppingcart.feature_shopping.domain.model.Cart
import com.jozefv.shoppingcart.feature_shopping.domain.model.Product
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CartSummaryCard
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CustomAlertDialog
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CustomProgressDialog
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CustomToolBar
import com.jozefv.shoppingcart.feature_shopping.presentation.components.products.ProductItem
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryAction
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ShoppingCartTheme
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.ObserveAsEvents
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerVerM

@Composable
fun CartScreenRoot(
    viewModel: SharedProductCartViewModel,
    goBack: () -> Unit,
    navigateToCheckout: (totalCartPrice: String) -> Unit
) {
    ObserveAsEvents(flow = viewModel.eventChannel) { event ->
        if (event is SharedProductCartEvent.NavigateToCheckout) {
            navigateToCheckout(event.totalCartPrice)
        }
    }
    CartScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                SharedProductCartAction.GoBack -> goBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun CartScreen(
    state: SharedProductCartState,
    onAction: (SharedProductCartAction) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val dialogVisibility = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomToolBar(
                scrollBehavior = scrollBehavior,
                title = "Your shopping cart",
                trailingContent = {
                    if (state.cart.numberOfAllProductsInCart > 0) {
                        IconButton(
                            onClick = {
                                dialogVisibility.value = true
                            }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = PrimaryAction,
                                contentDescription = Icons.Default.Delete.name
                            )
                        }
                    }
                })
        }) { paddingValues ->
        if (state.cart.numberOfAllProductsInCart > 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(state.cart.products) {
                        ProductItem(product = it, onAction = onAction, productScreen = false)
                    }
                }
                CartSummaryCard(
                    areButtonsEnabled = !state.isLoading,
                    totalEur = state.cart.totalPrice,
                    secondaryButtonAction = {
                        onAction(SharedProductCartAction.GoBack)
                    },
                    primaryButtonAction = {
                        onAction(SharedProductCartAction.GoToCheckout)
                    })
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Yor cart is empty")
                SpacerVerM()
                Button(
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        onAction(SharedProductCartAction.GoBack)
                    }) {
                    Text(text = "Continue shopping")
                }
            }
        }
        if (state.isLoading) {
            CustomProgressDialog(text = state.dialogText)
        }
    }
    if (dialogVisibility.value) {
        CustomAlertDialog(
            onPositiveAction = {
                onAction(SharedProductCartAction.RemoveAllProductsFromTheCart)
                dialogVisibility.value = false
            },
            onNegativeAction = {
                dialogVisibility.value = false
            },
            onDismissRequest = {
                dialogVisibility.value = false
            }
        )
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    ShoppingCartTheme {
        CartScreen(
            state = SharedProductCartState(
                cart = Cart(
                    products = listOf(
                        Product(
                            "123",
                            "Samsung",
                            "Blue 256 GB",
                            1110.5,
                            2,
                            2221.0
                        )
                    )
                )
            ), onAction = {})
    }
}