package com.jozefv.shoppingcart.feature_shopping.presentation.checkout

sealed interface CheckoutAction {
    data class NameInput(val name: String): CheckoutAction
    data object NameErased: CheckoutAction
    data class EmailInput(val email: String): CheckoutAction
    data object EmailErased : CheckoutAction
    data class AddressInput(val address: String): CheckoutAction
    data object AddressErased: CheckoutAction
    data class CardInput(val card: String): CheckoutAction
    data object CardErased: CheckoutAction
    data object StartOver: CheckoutAction
    data object GoBack: CheckoutAction
    data object MakePayment: CheckoutAction
}