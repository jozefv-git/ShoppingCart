package com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val size = StyleSize()

@Composable
fun SpacerHorS() {
    Spacer(modifier = Modifier.width(size.small))
}

@Composable
fun SpacerHorM() {
    Spacer(modifier = Modifier.width(size.medium))
}

@Composable
fun SpacerHorL() {
    Spacer(modifier = Modifier.width(size.large))
}

@Composable
fun SpacerHorXL() {
    Spacer(modifier = Modifier.width(size.extraLarge))
}

@Composable
fun SpacerVerS() {
    Spacer(modifier = Modifier.height(size.small))
}

@Composable
fun SpacerVerM() {
    Spacer(modifier = Modifier.height(size.medium))
}

@Composable
fun SpacerVerL() {
    Spacer(modifier = Modifier.height(size.large))
}

@Composable
fun SpacerVerXL() {
    Spacer(modifier = Modifier.height(size.extraLarge))
}