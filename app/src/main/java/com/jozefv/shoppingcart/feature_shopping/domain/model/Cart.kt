package com.jozefv.shoppingcart.feature_shopping.domain.model

data class Cart(
    val products: List<Product>,
    val totalPrice: String = products.sumOf { it.totalProductPrice }.toString(),
    val numberOfAllProductsInCart: Int = products.sumOf { it.productQuantity }
)
