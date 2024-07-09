package com.jozefv.shoppingcart.feature_shopping.data.form

import com.jozefv.shoppingcart.feature_shopping.domain.FormStorage
import com.jozefv.shoppingcart.feature_shopping.domain.model.User

class FakeFormStorage : FormStorage{
    private var user: User? = null
    override fun saveUser(user: User) {
        this.user = user
    }

    override fun getUser(): User? {
        return user
    }

    override fun removeData() {
        user = null
    }
}