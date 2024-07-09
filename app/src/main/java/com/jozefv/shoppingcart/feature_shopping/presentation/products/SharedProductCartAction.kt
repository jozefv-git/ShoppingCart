package com.jozefv.shoppingcart.feature_shopping.presentation.products

import com.jozefv.shoppingcart.feature_shopping.domain.model.Product

sealed interface SharedProductCartAction {
    data class RemoveProductFromTheCart(val product: Product) : SharedProductCartAction
    data class IncreaseQuantity(val product: Product) : SharedProductCartAction
    data class DecreaseQuantity(val product: Product) : SharedProductCartAction
    data object RemoveAllProductsFromTheCart: SharedProductCartAction
    data object GoBack: SharedProductCartAction
    data object GoToCart : SharedProductCartAction
    data object GoToCheckout: SharedProductCartAction
}