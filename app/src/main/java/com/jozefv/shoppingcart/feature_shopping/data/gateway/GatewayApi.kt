package com.jozefv.shoppingcart.feature_shopping.data.gateway

interface GatewayApi {
    // In reality, this would lead to an API, now only static response
    suspend fun serverResponse(): Int {
        return GatewayServer().serverResult()
    }
}