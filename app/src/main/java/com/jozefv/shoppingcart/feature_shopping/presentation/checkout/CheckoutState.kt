package com.jozefv.shoppingcart.feature_shopping.presentation.checkout

data class CheckoutState(
    val name: String = "",
    val isValidName: Boolean? = null,
    val email: String = "",
    val isValidEmail: Boolean? = null,
    val address: String = "",
    val isValidAddress: Boolean? = null,
    val card: String = "",
    val isValidCard: Boolean? = null,
    val canPay: Boolean? = null,
    val isLoading: Boolean = false,
    val dialogText: String = "",
    val toBePaid: String = ""
) {
    val isValidForm =
        isValidName == true && isValidEmail == true && isValidAddress == true && isValidCard == true
}
