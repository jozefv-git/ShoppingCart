package com.jozefv.shoppingcart.feature_shopping.data.products

import com.jozefv.shoppingcart.feature_shopping.data.model.ProductDetail

class FakeProductsApi : ProductsApi {
    override fun getProducts(): List<ProductDetail> {
        val products = mutableListOf<ProductDetail>()
        for (char: Char in 'a'..'j') {
            products.add(
                ProductDetail(
                    id = char + "_id",
                    name = char + "_name",
                    type = char + "_type",
                    description = char + "_description",
                    price = char.code.toDouble()
                )
            )
        }
        return products
    }
}