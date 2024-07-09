package com.jozefv.shoppingcart.feature_shopping.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSerializable(
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val card: String = ""
)
