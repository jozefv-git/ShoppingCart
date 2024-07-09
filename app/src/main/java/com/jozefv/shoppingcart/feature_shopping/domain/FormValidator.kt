package com.jozefv.shoppingcart.feature_shopping.domain

import com.jozefv.shoppingcart.feature_shopping.domain.model.User

interface FormValidator {
    fun isNameValid(name: String): Boolean?
    fun isEmailValid(email: String): Boolean?
    fun isAddressValid(address: String): Boolean?
    fun isCreditCardValid(creditCardNumber: String): Boolean?
    fun saveUser()
    fun getUser(): User?
    fun removeData()
}