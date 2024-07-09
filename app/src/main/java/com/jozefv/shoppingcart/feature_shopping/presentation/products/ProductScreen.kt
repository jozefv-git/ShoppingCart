@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.shoppingcart.feature_shopping.presentation.products

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.shoppingcart.feature_shopping.presentation.components.products.CustomBasketIcon
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CustomToolBar
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.StyleSize
import com.jozefv.shoppingcart.feature_shopping.presentation.components.products.ProductItem

@Composable
fun ProductScreenRoot(
    viewModel: SharedProductCartViewModel,
    onCartClick: () -> Unit,
) {
    ProductScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                SharedProductCartAction.GoToCart -> onCartClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun ProductScreen(
    styleSize: StyleSize = StyleSize(),
    state: SharedProductCartState,
    onAction: (SharedProductCartAction) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomToolBar(
                scrollBehavior = scrollBehavior,
                title = "Products",
                trailingContent = {
                    CustomBasketIcon(
                        numberOfProducts = state.cart.numberOfAllProductsInCart,
                        onClick = {
                            onAction(SharedProductCartAction.GoToCart)
                        })
                }
            )
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = styleSize.medium)
        ) {
            items(state.products) { product ->
                ProductItem(product = product, onAction = onAction)
            }
        }
    }
}

@Preview
@Composable
private fun ProductScreenPreview() {
    MaterialTheme {
        ProductScreen(
            state = SharedProductCartState(
                products = emptyList(),
            ),
            onAction = {}
        )
    }
}
