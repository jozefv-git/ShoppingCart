package com.jozefv.shoppingcart

import android.app.Application
import com.jozefv.shoppingcart.feature_shopping.di.featureShoppingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Set up koin for DI
 */
class ShoppingCartApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Set up koin
        startKoin {
            // Log android specific codes
            androidLogger()
            // Provide app context, so it can be used to create dependencies
            androidContext(this@ShoppingCartApp)
            // List of modules what we need
            modules(featureShoppingModule)
        }
    }
}