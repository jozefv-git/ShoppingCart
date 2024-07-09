package com.jozefv.shoppingcart.feature_shopping.domain

import kotlinx.coroutines.flow.Flow

interface Gateway {
    fun paymentResult(): Flow<ResourceResult>
}