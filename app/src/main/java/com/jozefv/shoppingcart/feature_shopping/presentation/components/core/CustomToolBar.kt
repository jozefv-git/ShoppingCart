@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.shoppingcart.feature_shopping.presentation.components.core

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ShoppingCartTheme
import com.jozefv.shoppingcart.feature_shopping.presentation.components.products.CustomBasketIcon
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.Surface

@Composable
fun CustomToolBar(
    modifier: Modifier = Modifier,
    title: String,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    trailingContent: @Composable () -> Unit = {},
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Surface),
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = title)
        },
        actions = {
            trailingContent()
        }
    )
}

@Preview
@Composable
private fun CustomToolBarPreview() {
    ShoppingCartTheme {
        CustomToolBar(
            title = "Products",
            trailingContent = { CustomBasketIcon(numberOfProducts = 20, onClick = {}) })
    }
}