package com.jozefv.shoppingcart.feature_shopping.domain.model

data class Product(
    val id: String,
    val name: String,
    val type: String,
    val price: Double,
    val productQuantity: Int,
    val totalProductPrice: Double
)
