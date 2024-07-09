package com.jozefv.shoppingcart.feature_shopping.data.gateway

import kotlinx.coroutines.delay

class GatewayServer : GatewayApi{
    // Simulated server responses with artificial delays
    suspend fun serverResult(): Int{
        delay(3000)
        return 200
    }
}






