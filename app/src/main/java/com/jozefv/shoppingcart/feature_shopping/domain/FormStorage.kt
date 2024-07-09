package com.jozefv.shoppingcart.feature_shopping.domain

import com.jozefv.shoppingcart.feature_shopping.domain.model.User

interface FormStorage {
    fun saveUser(user: User)
    fun getUser(): User?
    fun removeData()
}