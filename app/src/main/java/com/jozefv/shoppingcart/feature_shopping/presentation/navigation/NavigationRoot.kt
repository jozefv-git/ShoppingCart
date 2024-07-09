package com.jozefv.shoppingcart.feature_shopping.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.jozefv.shoppingcart.feature_shopping.presentation.checkout.CheckoutScreenRoot
import com.jozefv.shoppingcart.feature_shopping.presentation.products.CartScreenRoot
import com.jozefv.shoppingcart.feature_shopping.presentation.products.ProductScreenRoot
import com.jozefv.shoppingcart.feature_shopping.presentation.products.SharedProductCartViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.ProductsSubGraph.route
    ) {
        cartGraph(navController)
        composable(
            route = "${Screen.CheckoutScreen.route}/{toBePaid}",
            listOf(
                navArgument(
                    name = "toBePaid",
                    builder = {
                        type = NavType.StringType
                    }
                )
            )
        ) {
            CheckoutScreenRoot(
                viewModel = koinViewModel(),
                goBack = {
                    navController.popBackStack(
                        route = Screen.CartScreen.route,
                        inclusive = false,
                    )
                },
                startOver = {
                    navController.navigate(Screen.ProductsSubGraph.route) {
                        navController.popBackStack(
                            route = Screen.ProductsSubGraph.route,
                            inclusive = true,
                        )
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.cartGraph(navController: NavController) {
    navigation(Screen.ProductsScreen.route, Screen.ProductsSubGraph.route) {
        composable(Screen.ProductsScreen.route) {
            ProductScreenRoot(
                viewModel = it.sharedViewModel(
                    navController = navController
                ),
                onCartClick = {
                    navController.navigate(Screen.CartScreen.route)
                }
            )
        }
        composable(Screen.CartScreen.route) {
            CartScreenRoot(
                viewModel = it.sharedViewModel(
                    navController = navController
                ),
                goBack = {
                    // We use shared VM with shared state -> just go back
                    navController.popBackStack()
                },
                navigateToCheckout = { totalCartPrice ->
                    navController.navigate(
                        route = "${Screen.CheckoutScreen.route}/${totalCartPrice}"
                    )
                }
            )
        }
    }
}

@Composable
private fun NavBackStackEntry.sharedViewModel(navController: NavController): SharedProductCartViewModel {
    val graphRoute = destination.parent?.route ?: return koinViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(graphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}