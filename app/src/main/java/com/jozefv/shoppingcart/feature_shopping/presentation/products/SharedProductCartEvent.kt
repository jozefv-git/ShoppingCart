package com.jozefv.shoppingcart.feature_shopping.presentation.products

sealed interface SharedProductCartEvent {
    data class NavigateToCheckout(val totalCartPrice: String) : SharedProductCartEvent
}