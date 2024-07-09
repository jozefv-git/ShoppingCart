package com.jozefv.shoppingcart.feature_shopping.presentation.checkout

sealed interface CheckoutEvent {
    data object NavigateToMain: CheckoutEvent
}