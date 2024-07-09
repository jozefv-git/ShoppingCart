package com.jozefv.shoppingcart.feature_shopping.data.form


import androidx.core.util.PatternsCompat
import com.jozefv.shoppingcart.feature_shopping.domain.FormStorage
import com.jozefv.shoppingcart.feature_shopping.domain.FormValidator
import com.jozefv.shoppingcart.feature_shopping.domain.model.User

class FormValidatorImp(
    private val formStorage: FormStorage
) : FormValidator {
    private var user = User()
    override fun isNameValid(name: String): Boolean? {
        user = user.copy(name = name)
        // Basic check if name doesn't contains digit and it is at least 3 char long
        return if (name.isNotBlank()) {
            val noDigit = name.all { !it.isDigit() }
            val length = name.length >= 3
            noDigit && length
        } else null
    }

    override fun isEmailValid(email: String): Boolean? {
        user = user.copy(email = email)
        return if (email.isNotBlank()) {
            PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
        } else null
    }

    override fun isAddressValid(address: String): Boolean? {
        user = user.copy(address = address)
        // If input contains Finland - it is valid
        return if (address.isNotBlank()) {
            address.contains("Finland", ignoreCase = true)
        } else null
    }

    override fun isCreditCardValid(creditCardNumber: String): Boolean? {
        user = user.copy(card = creditCardNumber)
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
        formStorage.saveUser(user = user)
    }

    override fun getUser(): User? {
        return formStorage.getUser()
    }

    override fun removeData() {
        // Reset user
        user = User()
        return formStorage.removeData()
    }
}