package com.jozefv.shoppingcart.feature_shopping.di

import android.content.Context
import android.content.SharedPreferences
import com.jozefv.shoppingcart.feature_shopping.data.form.FormValidatorImp
import com.jozefv.shoppingcart.feature_shopping.data.form.FormStorageImp
import com.jozefv.shoppingcart.feature_shopping.data.gateway.GatewayApi
import com.jozefv.shoppingcart.feature_shopping.data.gateway.GatewayImp
import com.jozefv.shoppingcart.feature_shopping.data.gateway.GatewayServer
import com.jozefv.shoppingcart.feature_shopping.data.products.Products
import com.jozefv.shoppingcart.feature_shopping.data.products.ProductsRepositoryImp
import com.jozefv.shoppingcart.feature_shopping.domain.FormStorage
import com.jozefv.shoppingcart.feature_shopping.domain.FormValidator
import com.jozefv.shoppingcart.feature_shopping.domain.Gateway
import com.jozefv.shoppingcart.feature_shopping.domain.ProductsRepository
import com.jozefv.shoppingcart.feature_shopping.presentation.checkout.CheckoutScreenViewModel
import com.jozefv.shoppingcart.feature_shopping.presentation.products.SharedProductCartViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureShoppingModule = module {
    viewModelOf(::SharedProductCartViewModel)
    // Explicitly bind our abstraction to our actual implementation
    single<ProductsRepository> {
        ProductsRepositoryImp(Products)
    }
    viewModelOf(::CheckoutScreenViewModel)
    singleOf(::FormValidatorImp).bind<FormValidator>()
    singleOf(::GatewayImp).bind<Gateway>()
    single<SharedPreferences> {
        androidApplication().getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
    singleOf(::FormStorageImp).bind<FormStorage>()

    single<GatewayApi> {
        GatewayServer()
    }
}