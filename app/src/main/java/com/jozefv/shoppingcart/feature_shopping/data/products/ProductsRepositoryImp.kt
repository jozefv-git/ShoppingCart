package com.jozefv.shoppingcart.feature_shopping.data.products

import com.jozefv.shoppingcart.feature_shopping.data.mappers.toProduct
import com.jozefv.shoppingcart.feature_shopping.domain.model.Cart
import com.jozefv.shoppingcart.feature_shopping.domain.model.Product
import com.jozefv.shoppingcart.feature_shopping.domain.ProductsRepository
import com.jozefv.shoppingcart.feature_shopping.domain.ResourceResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductsRepositoryImp(
    private val productsApi: ProductsApi
) : ProductsRepository {

    // These can be data from our API - for now only static data
    override fun getProducts(): List<Product> {
        return productsApi.getProducts().map { it.toProduct() }
    }

    override fun updateProductQuantity(
        products: List<Product>,
        selectedProduct: Product,
        productQuantity: Int
    ): List<Product> {
        return products.map {
            if (it == selectedProduct) {
                it.copy(
                    productQuantity = productQuantity,
                    totalProductPrice = productQuantity * it.price
                )
            } else it
        }
    }

    override fun getCart(products: List<Product>): Cart {
        return Cart(products = products.filter { it.productQuantity > 0 })
    }

    override fun removeProductFromTheCart(
        products: List<Product>,
        product: Product
    ): List<Product> {
        return products.map {
            if (it == product) {
                it.copy(productQuantity = 0)
            } else it
        }
    }


    // Querying backend inventory system - for now just static data - so we can display some flow in the UI
    override fun productsAvailable(products: List<Product>): Flow<ResourceResult> = flow {
        if (products.isNotEmpty()) {
            emit(ResourceResult.Loading())
            emit(ResourceResult.Success("Checking products availability..."))
            delay(2000)
            emit(ResourceResult.Success("Almost there..."))
            delay(1000)
            emit(ResourceResult.Success("All products are available!"))
            delay(1500)
            emit(ResourceResult.Loading(false))
        } else {
            emit(ResourceResult.Loading())
            emit(ResourceResult.Error("Your cart is an empty."))
            delay(1000)
            emit(ResourceResult.Error("Try to add items again."))
            delay(1000)
            emit(ResourceResult.Loading(false))
        }
    }
}