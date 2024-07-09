package com.jozefv.shoppingcart.feature_shopping.data.products

import android.icu.text.DecimalFormat
import com.jozefv.shoppingcart.feature_shopping.data.model.ProductDetail
import java.math.RoundingMode
import java.util.UUID
import kotlin.random.Random

/**
 * Products generator
 *
 * In reality these data would come from some remote server.
 */
object Products : ProductsApi {
    private val deviceMemorySize = listOf(128, 256, 512, 1024)
    private fun randomMemory(): Int {
        return deviceMemorySize.random()
    }

    private fun randomId(): String {
        return UUID.randomUUID().toString()
    }

    private fun randomPrice(): Double {
        return Random.nextInt(800, 1600).toDouble()
    }

    val allProducts = listOf(
        ProductDetail(
            randomId(),
            "Samsung galaxy S24 Ultra",
            "${randomMemory()} GB, Pearl blue",
            "Lorem ipsum",
            randomPrice()
        ),
        ProductDetail(
            randomId(),
            "Samsung galaxy S23",
            "${randomMemory()} GB, Diamond red",
            "Lorem ipsum",
            randomPrice()
        ),
        ProductDetail(
            randomId(),
            "Iphone 14 Max",
            "${randomMemory()} GB, Sky black",
            "Lorem ipsum",
            randomPrice()
        ),
        ProductDetail(
            randomId(),
            "Huawei P30",
            "${randomMemory()} GB, Silver",
            "Lorem ipsum",
            randomPrice()
        ),
        ProductDetail(
            randomId(),
            "Pixel 8 Pro",
            "${randomMemory()} GB, Pinky pink",
            "Lorem ipsum",
            randomPrice()
        ),
        ProductDetail(
            randomId(),
            "Pixel 7",
            "${randomMemory()} GB, White",
            "Lorem ipsum",
            randomPrice()
        )
    )
}