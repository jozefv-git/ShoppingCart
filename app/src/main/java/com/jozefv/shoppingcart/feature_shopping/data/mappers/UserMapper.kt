package com.jozefv.shoppingcart.feature_shopping.data.mappers

import com.jozefv.shoppingcart.feature_shopping.data.model.UserSerializable
import com.jozefv.shoppingcart.feature_shopping.domain.model.User

/**
 * Map User models -> so we can keep our domain layer dependency free
 */
fun User.toUserSerializable(): UserSerializable {
    return UserSerializable(
        name = name,
        email = email,
        address = address,
        card = card
    )
}

fun UserSerializable.toUser(): User {
    return User(
        name = name,
        email = email,
        address = address,
        card = card
    )
}