package com.jozefv.shoppingcart.feature_shopping.data.form

import android.content.SharedPreferences
import com.jozefv.shoppingcart.feature_shopping.data.mappers.toUser
import com.jozefv.shoppingcart.feature_shopping.data.mappers.toUserSerializable
import com.jozefv.shoppingcart.feature_shopping.data.model.UserSerializable
import com.jozefv.shoppingcart.feature_shopping.domain.FormStorage
import com.jozefv.shoppingcart.feature_shopping.domain.model.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FormStorageImp(private val sharedPreferences: SharedPreferences) : FormStorage {
    override fun saveUser(user: User) {
        val json = Json.encodeToString(user.toUserSerializable())
        sharedPreferences.edit().putString(KEY_USER, json).apply()
    }

    override fun getUser(): User? {
        val json = sharedPreferences.getString(KEY_USER, null)
        return json?.let {
            return Json.decodeFromString<UserSerializable>(it).toUser()
        }
    }

    override fun removeData() {
        // Remove all values from shared prefs
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_USER = "user"
    }
}