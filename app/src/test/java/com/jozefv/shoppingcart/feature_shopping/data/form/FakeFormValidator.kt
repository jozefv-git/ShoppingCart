package com.jozefv.shoppingcart.feature_shopping.data.form

import androidx.core.util.PatternsCompat
import com.jozefv.shoppingcart.feature_shopping.domain.FormValidator
import com.jozefv.shoppingcart.feature_shopping.domain.model.User

class FakeFormValidator(private val fakeFormStorage: FakeFormStorage) : FormValidator {

    override fun isNameValid(name: String): Boolean? {
        // Basic check if name doesn't contains digit and it is at least 3 char long
        return if (name.isNotBlank()) {
            val noDigit = name.all { !it.isDigit() }
            val length = name.length >= 3
            noDigit && length
        } else null
    }

    override fun isEmailValid(email: String): Boolean? {
        return if (email.isNotBlank()) {
            PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
        } else null
    }

    override fun isAddressValid(address: String): Boolean? {
        // If input contains Finland - it is valid
        return if (address.isNotBlank()) {
            address.contains("Finland", ignoreCase = true)
        } else null
    }

    override fun isCreditCardValid(creditCardNumber: String): Boolean? {
        return if (creditCardNumber.isNotBlank()) {
            // For simplicity validate only VISA cards
            // Visa cards have either 13 or 16 numbers
            // Check if first letter is 4 - Visa cards starts with 4
            val isNumber = creditCardNumber.all { it.isDigit() }
            val isLengthValid = creditCardNumber.run { length == 13 || length == 16 }
            val isStartingNumberValid = creditCardNumber[0].toString() == "4"
            isNumber && isLengthValid && isStartingNumberValid
        } else null
    }

    override fun saveUser() {
        fakeFormStorage.saveUser(
            user =
            User(
                name = "Jozef",
                email = "jozef@mail.com",
                address = "Finland",
                card = "4000000000000"
            )
        )
    }

    override fun getUser(): User? {
        return fakeFormStorage.getUser()
    }

    override fun removeData() {
        return fakeFormStorage.removeData()
    }
}