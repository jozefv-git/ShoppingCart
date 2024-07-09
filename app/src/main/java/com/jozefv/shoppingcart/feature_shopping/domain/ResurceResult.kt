package com.jozefv.shoppingcart.feature_shopping.domain

sealed interface ResourceResult {
    data class Success(
        val data: String,
    ) : ResourceResult

    data class Error(
        val data: String,
    ) : ResourceResult

    data class Loading(val isLoading: Boolean = true, val data: String = "") : ResourceResult
}