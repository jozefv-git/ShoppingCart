package com.jozefv.shoppingcart.feature_shopping.data.gateway

import com.jozefv.shoppingcart.feature_shopping.domain.Gateway
import com.jozefv.shoppingcart.feature_shopping.domain.ResourceResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// Handle GatewayApi response

class GatewayImp(private val api: GatewayApi) : Gateway {

    override fun paymentResult(): Flow<ResourceResult> = flow {
        emit(ResourceResult.Loading(data = "Contacting gateway..."))
        when (api.serverResponse()) {
            200 -> {
                emit(ResourceResult.Success(data = "Result code is: 200 - Success."))
            }

            400 -> {
                emit(ResourceResult.Error(data = "Result code is: 400 - Error."))
            }

            else -> {
                emit(ResourceResult.Error(data = "Result code is: 500 - Error."))
            }
        }
        delay(2000)
        emit(ResourceResult.Loading(data = "Redirecting to main screen..."))
        delay(1500)
        emit(ResourceResult.Loading(isLoading = false))
    }
}