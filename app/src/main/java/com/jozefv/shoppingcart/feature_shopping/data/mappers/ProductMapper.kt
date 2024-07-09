package com.jozefv.shoppingcart.feature_shopping.data.mappers
import com.jozefv.shoppingcart.feature_shopping.data.model.ProductDetail
import com.jozefv.shoppingcart.feature_shopping.domain.model.Product

fun ProductDetail.toProduct(): Product {
    return Product(
        id,
        name,
        type,
        price,
        0,
        0.0
    )
}