package com.jozefv.shoppingcart.feature_shopping.presentation.navigation

sealed class Screen(val route: String) {
    data object ProductsSubGraph: Screen("products")
    data object ProductsScreen: Screen("productScreen")
    data object CartScreen: Screen("cartScreen")
    data object CheckoutScreen: Screen("checkoutScreen")
}