package com.jozefv.shoppingcart.feature_shopping.presentation.products

import com.google.common.truth.Truth.assertThat
import com.jozefv.shoppingcart.feature_shopping.data.mappers.toProduct
import com.jozefv.shoppingcart.feature_shopping.data.products.FakeProductsApi
import com.jozefv.shoppingcart.feature_shopping.data.products.ProductsRepositoryImp
import com.jozefv.shoppingcart.feature_shopping.domain.model.Product
import org.junit.Before
import org.junit.Test

class SharedProductCartViewModelTest {
    private lateinit var viewModel: SharedProductCartViewModel
    private lateinit var expectedProducts: List<Product>

    private lateinit var productsRepositoryImp: ProductsRepositoryImp
    private lateinit var fakeProductsApi: FakeProductsApi

    @Before
    fun setUp() {
        fakeProductsApi = FakeProductsApi()
        productsRepositoryImp = ProductsRepositoryImp(fakeProductsApi)
        viewModel = SharedProductCartViewModel(productsRepository = productsRepositoryImp)
        expectedProducts = fakeProductsApi.getProducts().map { it.toProduct() }
    }


    @Test
    fun `After viewModel is initialized, product state should be updated with products, true`() {

        val expectedState = SharedProductCartState(
            products = expectedProducts
        )
        val viewModelState = viewModel.state

        assertThat(viewModelState).isEqualTo(expectedState)
    }

    @Test
    fun `When increase quantity is clicked, selected product was increased by one, true`() {
        val selectedProduct = expectedProducts[0]

        val expectedProductAfterUpdate = expectedProducts[0].let {
            it.copy(
                productQuantity = it.productQuantity + 1,
                totalProductPrice = (it.productQuantity + 1) * it.price
            )
        }

        viewModel.onAction(SharedProductCartAction.IncreaseQuantity(selectedProduct))

        val actualProducts = viewModel.state.products[0]

        assertThat(actualProducts).isEqualTo(expectedProductAfterUpdate)
    }

    @Test
    fun `When decrease quantity is clicked, selected product was decreased by one, true`() {
        val selectedProduct = expectedProducts[0]

        val expectedProductAfterUpdate = expectedProducts[0].let {
            it.copy(
                productQuantity = it.productQuantity - 1,
                totalProductPrice = (it.productQuantity - 1) * it.price
            )
        }

        viewModel.onAction(SharedProductCartAction.DecreaseQuantity(selectedProduct))

        val actualProducts = viewModel.state.products[0]

        assertThat(actualProducts).isEqualTo(expectedProductAfterUpdate)
    }

    @Test
    fun `Cart should contain only products with quantity greater than 0, true`() {
        val cart = productsRepositoryImp.getCart(products = viewModel.state.products)

        val lessThanZero = cart.products.none { it.productQuantity < 1 }

        assertThat(lessThanZero).isEqualTo(true)
    }

    @Test
    fun `Cart shouldn't include product which was removed, true`() {
        val productA = expectedProducts[0]
        val productC = expectedProducts[2]

        // Increase quantity of selected products (add to the cart)
        viewModel.onAction(SharedProductCartAction.IncreaseQuantity(productA))
        viewModel.onAction(SharedProductCartAction.IncreaseQuantity(productC))

        val expectedProductsInTheCart =
            listOf(productA, productC).map { it.copy(
                productQuantity = it.productQuantity + 1,
                totalProductPrice = (it.productQuantity + 1) * it.price
            ) }

        val actualProductsInTheCart =
            productsRepositoryImp.getCart(products = viewModel.state.products).products

        assertThat(actualProductsInTheCart).isEqualTo(expectedProductsInTheCart)

        // Remove productC from the cart
        val productToBeRemoved = actualProductsInTheCart[1]

        val expectedCartAfterProductRemove = actualProductsInTheCart.filter { it != productToBeRemoved }

        viewModel.onAction(SharedProductCartAction.RemoveProductFromTheCart(productToBeRemoved))

        val actualCart = viewModel.state.cart.products

        assertThat(actualCart).isEqualTo(expectedCartAfterProductRemove)
    }

    @Test
    fun `After removal of all products from the cart, default state should be provided, true`() {
        val expectedState = SharedProductCartState(
            products = expectedProducts,
            cart = productsRepositoryImp.getCart(expectedProducts)
        )

        viewModel.onAction(SharedProductCartAction.RemoveAllProductsFromTheCart)

        val actualState = viewModel.state

        assertThat(actualState).isEqualTo(expectedState)
    }
}