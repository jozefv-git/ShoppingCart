package com.jozefv.shoppingcart.feature_shopping.presentation.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.shoppingcart.feature_shopping.domain.FormValidator
import com.jozefv.shoppingcart.feature_shopping.domain.Gateway
import com.jozefv.shoppingcart.feature_shopping.domain.ResourceResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class CheckoutScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val formValidator: FormValidator,
    private val gateway: Gateway,
) : ViewModel() {
    var checkoutState by mutableStateOf(CheckoutState())
        private set

    private val _eventChannel = Channel<CheckoutEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        formValidator.getUser()?.let {
            checkoutState = checkoutState.copy(
                name = it.name,
                isValidName = formValidator.isNameValid(it.name),
                email = it.email,
                isValidEmail = formValidator.isEmailValid(it.email),
                address = it.address,
                isValidAddress = formValidator.isAddressValid(it.address),
                card = it.card,
                isValidCard = formValidator.isCreditCardValid(it.card)
            )
            checkoutState = checkoutState.copy(
                canPay = checkoutState.isValidForm
            )
        }
        checkoutState = checkoutState.copy(
            // Accessing argument which we sent from the CartScreen
            toBePaid = savedStateHandle["toBePaid"] ?: "0.0"
        )
    }


    fun onAction(action: CheckoutAction) {
        when (action) {
            is CheckoutAction.NameInput -> {
                checkoutState = checkoutState.copy(
                    name = action.name,
                    isValidName = formValidator.isNameValid(action.name)
                )
            }

            CheckoutAction.NameErased -> {
                checkoutState = checkoutState.copy(
                    name = "",
                )
                checkoutState = checkoutState.copy(
                    isValidName = formValidator.isNameValid(checkoutState.name)
                )
            }

            is CheckoutAction.EmailInput -> {
                checkoutState = checkoutState.copy(
                    email = action.email,
                    isValidEmail = formValidator.isEmailValid(action.email)
                )
            }

            CheckoutAction.EmailErased -> {
                checkoutState = checkoutState.copy(
                    email = "",
                )
                checkoutState = checkoutState.copy(
                    isValidEmail = formValidator.isEmailValid(checkoutState.email)
                )
            }

            is CheckoutAction.AddressInput -> {
                checkoutState = checkoutState.copy(
                    address = action.address,
                    isValidAddress = formValidator.isAddressValid(action.address)
                )
            }

            CheckoutAction.AddressErased -> {
                checkoutState = checkoutState.copy(
                    address = "",
                )
                checkoutState = checkoutState.copy(
                    isValidAddress = formValidator.isAddressValid(checkoutState.address)
                )
            }

            is CheckoutAction.CardInput -> {
                checkoutState = checkoutState.copy(
                    card = action.card,
                    isValidCard = formValidator.isCreditCardValid(action.card)
                )
            }

            CheckoutAction.CardErased -> {
                checkoutState = checkoutState.copy(
                    card = "",
                )
                checkoutState = checkoutState.copy(
                    isValidCard = formValidator.isCreditCardValid(checkoutState.card)
                )
            }

            CheckoutAction.StartOver -> {
                // Navigation is handled in the main activity where viewModel is popOut - which resets the state
                // and start again from the products sub-graph
                // Remove all data from shared prefs
                formValidator.removeData()
            }

            CheckoutAction.GoBack -> {
                // Navigation handled in main
                // Every time when user press back button or "Continue shopping", data will be saved
                formValidator.saveUser()
            }

            CheckoutAction.MakePayment -> {
                gateway.paymentResult().onEach { paymentResult ->
                    checkoutState = when (paymentResult) {
                        is ResourceResult.Loading -> {
                            if (!paymentResult.isLoading) {
                                formValidator.saveUser()
                                // Send event about redirection to main screen
                                _eventChannel.send(CheckoutEvent.NavigateToMain)
                            }
                            checkoutState.copy(
                                isLoading = paymentResult.isLoading,
                                dialogText = paymentResult.data
                            )
                        }

                        is ResourceResult.Success -> {
                            checkoutState.copy(dialogText = paymentResult.data)
                        }

                        is ResourceResult.Error -> {
                            checkoutState.copy(dialogText = paymentResult.data)
                        }
                    }
                    }.launchIn(viewModelScope)
            }
        }
        checkoutState = checkoutState.copy(
            canPay = checkoutState.isValidForm
        )
    }
}