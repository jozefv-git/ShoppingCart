package com.jozefv.shoppingcart.feature_shopping.data.form

import com.jozefv.shoppingcart.feature_shopping.domain.model.User
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class FormValidatorImpTest {
    private lateinit var formValidator: FormValidatorImp
    private lateinit var fakeFormStorage: FakeFormStorage

    private val expectedUser = User(
        name = "Jozef",
        email = "jozef@mail.com",
        address = "Finland",
        card = "4000000000000"
    )

    // Run setUp() before every single test case
    @Before
    fun setUp() {
        fakeFormStorage = FakeFormStorage()
        formValidator = FormValidatorImp(fakeFormStorage)
        // Save user
        fakeFormStorage.saveUser(expectedUser)
    }

    @Test
    fun `Is user saved, true`() {
        assertThat(formValidator.getUser()).isEqualTo(expectedUser)
    }

    @Test
    fun `After saved user is removed, user is null`() {
        assertThat(formValidator.getUser()).isEqualTo(expectedUser)

        formValidator.removeData()

        assertThat(formValidator.getUser()).isEqualTo(null)
    }

    @Test
    fun `Is name at least three char long and doesn't contain digits, true`() {
        val name = formValidator.getUser()!!.name
        assertThat(formValidator.isNameValid(name)).isEqualTo(true)
    }

    @Test
    fun `Is provided email address in correct format, true`() {
        val email = formValidator.getUser()!!.email
        assertThat(formValidator.isEmailValid(email)).isEqualTo(true)
    }

    @Test
    fun `Does provided address contain Finland, true`() {
        val address = formValidator.getUser()!!.address
        assertThat(formValidator.isAddressValid(address)).isEqualTo(true)
    }

    // Visa cards have either 13 or 16 numbers
    // Check if first letter is 4 - Visa cards starts with 4
    @Test
    fun `Is provided credit card a valid VISA card, true`() {
        val card = formValidator.getUser()!!.card
        assertThat(formValidator.isCreditCardValid(card)).isTrue()
    }
}