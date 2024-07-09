package com.jozefv.shoppingcart.feature_shopping.domain

import com.jozefv.shoppingcart.feature_shopping.domain.model.Cart
import com.jozefv.shoppingcart.feature_shopping.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProducts(): List<Product>

    fun updateProductQuantity(
        products: List<Product>,
        selectedProduct: Product,
        productQuantity: Int
    ): List<Product>

    fun getCart(products: List<Product> = emptyList()): Cart
    fun removeProductFromTheCart(products: List<Product>, product: Product): List<Product>
    fun productsAvailable(products: List<Product>): Flow<ResourceResult>
}