@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.shoppingcart.feature_shopping.presentation.checkout

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.SpacerVerM
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CartSummaryCard
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CustomAlertDialog
import com.jozefv.shoppingcart.feature_shopping.presentation.components.checkout.CustomTextField
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CustomProgressDialog
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.CustomToolBar
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.PrimaryAction
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.ui.theme.ShoppingCartTheme
import com.jozefv.shoppingcart.feature_shopping.presentation.components.core.util.ObserveAsEvents

@Composable
fun CheckoutScreenRoot(
    viewModel: CheckoutScreenViewModel,
    goBack: () -> Unit,
    startOver: () -> Unit,
) {
    ObserveAsEvents(flow = viewModel.eventChannel) { event ->
        if (event is CheckoutEvent.NavigateToMain) {
            startOver()
        }
    }
    CheckoutScreen(
        state = viewModel.checkoutState,
        onAction = { action ->
            when (action) {
                CheckoutAction.GoBack -> {
                    viewModel.onAction(action)
                    goBack()
                }

                CheckoutAction.StartOver -> {
                    viewModel.onAction(action)
                    startOver()
                }

                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun CheckoutScreen(
    state: CheckoutState,
    onAction: (CheckoutAction) -> Unit
) {
    val dialogVisibility = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            CustomToolBar(
                title = "Checkout",
                trailingContent = {
                    IconButton(
                        onClick = {
                            dialogVisibility.value = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = Icons.Default.Delete.name,
                            tint = PrimaryAction
                        )
                    }
                }
            )
        }) { paddingValues ->
        // Automatic scroll when keyboard is visible
        val scrollState = rememberScrollState()
        val isKeyboardVisible = remember {
            mutableStateOf(false)
        }
        val keyBoardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
        LaunchedEffect(
            key1 = keyBoardHeight,
            block = {
                isKeyboardVisible.value = keyBoardHeight > 0
                scrollState.animateScrollTo(
                    value = keyBoardHeight,
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = Ease
                    )
                )
            })
        Column {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .imePadding()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .verticalScroll(scrollState)
            ) {
                CustomTextField(
                    leadingIcon = Icons.Default.AccountCircle,
                    trailingIcon = Icons.Default.Clear,
                    trailingIconAction = {
                        onAction(CheckoutAction.NameErased)
                    },
                    label = "Your name",
                    supportiveText = "John Doe",
                    isValid = state.isValidName,
                    textFieldValue = state.name,
                    onValueChange = {
                        onAction(CheckoutAction.NameInput(it))
                    }
                )
                SpacerVerM()
                CustomTextField(
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    leadingIcon = Icons.Default.Email,
                    trailingIcon = Icons.Default.Clear,
                    trailingIconAction = {
                        onAction(CheckoutAction.EmailErased)
                    },
                    label = "Your email",
                    supportiveText = "john.doe@mail.com",
                    isValid = state.isValidEmail,
                    textFieldValue = state.email,
                    onValueChange = {
                        onAction(CheckoutAction.EmailInput(it))
                    }
                )
                SpacerVerM()
                CustomTextField(
                    leadingIcon = Icons.Default.Place,
                    trailingIcon = Icons.Default.Clear,
                    trailingIconAction = {
                        onAction(CheckoutAction.AddressErased)
                    },
                    label = "Your address",
                    supportiveText = "Must contain Finland ...",
                    isValid = state.isValidAddress,
                    textFieldValue = state.address,
                    onValueChange = {
                        onAction(CheckoutAction.AddressInput(it))
                    }
                )
                SpacerVerM()
                CustomTextField(
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    leadingIcon = Icons.Default.Create,
                    trailingIcon = Icons.Default.Clear,
                    trailingIconAction = {
                        onAction(CheckoutAction.CardErased)
                    },
                    label = "Your card",
                    supportiveText = "Visa card must start with 4 and have 13 or 16 letters",
                    isValid = state.isValidCard,
                    textFieldValue = state.card,
                    onValueChange = {
                        onAction(CheckoutAction.CardInput(it))
                    }
                )
            }
            AnimatedVisibility(
                visible = !isKeyboardVisible.value && state.canPay == true,
                enter = fadeIn(tween(500))
            ) {
                CartSummaryCard(
                    text = "Summary",
                    primaryButtonText = "Pay",
                    totalEur = state.toBePaid,
                    secondaryButtonAction = {
                        onAction(CheckoutAction.GoBack)
                    },
                    primaryButtonAction = {
                        onAction(CheckoutAction.MakePayment)
                    })
            }
            if (dialogVisibility.value) {
                CustomAlertDialog(
                    title = "Do you want to start again?",
                    text = "All data will be erased and you will be redirected to main screen.",
                    onPositiveAction = {
                        onAction(CheckoutAction.StartOver)
                    },
                    onNegativeAction = {
                        dialogVisibility.value = false
                    },
                    onDismissRequest = {
                        dialogVisibility.value = false
                    }
                )
            }
            if (state.isLoading) {
                CustomProgressDialog(
                    text = state.dialogText
                )
            }
        }
    }
    BackHandler {
        onAction(CheckoutAction.GoBack)
    }
}

@Preview
@Composable
private fun CheckoutScreenPreview() {
    ShoppingCartTheme {
        CheckoutScreen(
            state = CheckoutState(),
            onAction = {}
        )
    }
}