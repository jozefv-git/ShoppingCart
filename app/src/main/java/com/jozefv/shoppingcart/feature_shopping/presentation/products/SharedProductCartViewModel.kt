package com.jozefv.shoppingcart.feature_shopping.presentation.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.shoppingcart.feature_shopping.domain.ProductsRepository
import com.jozefv.shoppingcart.feature_shopping.domain.ResourceResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class SharedProductCartViewModel(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    var state by mutableStateOf(SharedProductCartState())
        private set

    private val _eventChannel = Channel<SharedProductCartEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        state = state.copy(
            products = productsRepository.getProducts()
        )
    }

    fun onAction(action: SharedProductCartAction) {
        when (action) {
            SharedProductCartAction.GoBack -> {
                // navigation is handled in mainActivity
            }

            SharedProductCartAction.GoToCart -> {
                // navigation is handled in mainActivity
            }

            is SharedProductCartAction.DecreaseQuantity -> {
                state = state.copy(
                    products = productsRepository.updateProductQuantity(
                        state.products,
                        action.product,
                        action.product.productQuantity.dec()
                    )
                )
            }

            is SharedProductCartAction.IncreaseQuantity -> {
                state = state.copy(
                    products = productsRepository.updateProductQuantity(
                        state.products,
                        action.product,
                        action.product.productQuantity.inc()
                    )
                )
            }

            is SharedProductCartAction.RemoveProductFromTheCart -> {
                state = state.copy(
                    products = productsRepository.removeProductFromTheCart(
                        state.products,
                        action.product
                    )
                )

            }

            SharedProductCartAction.GoToCheckout -> {
                productsRepository.productsAvailable(state.cart.products).onEach {
                    state = when (it) {
                        is ResourceResult.Success -> state.copy(dialogText = it.data)
                        is ResourceResult.Error -> state.copy(dialogText = it.data)
                        is ResourceResult.Loading -> {
                            // When loading is over -> send notification based on which we navigate in main activity with selected result
                            if (!it.isLoading) {
                                _eventChannel.send(SharedProductCartEvent.NavigateToCheckout(state.cart.totalPrice))
                            }
                            state.copy(isLoading = it.isLoading)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            // Create new blank state with products - which were created when viewModel was initialized
            is SharedProductCartAction.RemoveAllProductsFromTheCart -> {
                state = SharedProductCartState(
                    products = productsRepository.getProducts()
                )
            }
        }
        state = state.copy(
            cart = productsRepository.getCart(state.products)
        )
    }
}