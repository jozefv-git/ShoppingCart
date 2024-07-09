package com.jozefv.shoppingcart.feature_shopping.presentation.products

import com.jozefv.shoppingcart.feature_shopping.domain.model.Cart
import com.jozefv.shoppingcart.feature_shopping.domain.model.Product

data class SharedProductCartState(
    val products: List<Product> = emptyList(),
    val cart: Cart = Cart(products = emptyList()),
    val isLoading: Boolean = false,
    val dialogText: String = ""
)
