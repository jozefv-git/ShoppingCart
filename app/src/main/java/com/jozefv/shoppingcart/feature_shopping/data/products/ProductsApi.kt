package com.jozefv.shoppingcart.feature_shopping.data.products

import com.jozefv.shoppingcart.feature_shopping.data.model.ProductDetail

interface ProductsApi {
    // This would be in reality some path to API where data are stored
    fun getProducts(): List<ProductDetail> {
        return Products.allProducts
    }
}