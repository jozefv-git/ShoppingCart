package com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    background = Surface,
    primary = PrimaryAction,
)

@Composable
fun ShoppingCartTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}