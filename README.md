# ShoppingCart

ShoppingCart is an example of shopping application for an e-commerce. All data in the application are example data - no remote endpoints were used .

| MainScreen Unselected | MainScreen Selected | CartScreen |
| --------------------- |--------------------- |--------------------- |
|![MainScreenUnselected](https://github.com/jozefv-git/ShoppingCart/assets/78084830/9f27e057-badc-402f-b818-2690d6a06b28)|![MainScreenSelected](https://github.com/jozefv-git/ShoppingCart/assets/78084830/e3ea799d-c611-43b7-bd21-2429001acae5)|![CartScreen](https://github.com/jozefv-git/ShoppingCart/assets/78084830/3abd5877-3611-4f37-9c66-17d3e15d1bd8)|
| CheckoutScreen Empty | CheckoutScreen Invalid | CheckoutScreen Valid |
![CheckoutScreenEmpty](https://github.com/jozefv-git/ShoppingCart/assets/78084830/c8603821-429b-499d-be38-cf8058fc40aa)|![CheckoutScreenInvalid](https://github.com/jozefv-git/ShoppingCart/assets/78084830/6e090096-b25c-4f97-b60a-3cb73163558e)|![CheckoutScreenValid](https://github.com/jozefv-git/ShoppingCart/assets/78084830/b6ee6c19-e554-41db-b580-d7e46037b490)|


### Functionality
- Select product from the list
- Automatically add selected product into the cart
- Shopping cart
- Edit product quantity
- Form validator
- Based on the user action, form data are stored locally in the device

- ## Used
- Kotlin
- Jetpack Compose
- M3 practices
- Kotlin coroutines, Flow
- Shared preferences
- Koin
- Clean architecture, MVI, ViewModels, Navigation

## Dependencies - defined in version catalog, implement where needed
Koin

```bash
koin-core = "io.insert-koin:koin-core:3.5.4"
koin-compose = "io.insert-koin:koin-androidx-compose:3.5.4"
```

Navigation

```bash
navigation = "androidx.navigation:navigation-compose:2.7.7"
```

Json

```bash
kotlin-serialization-json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3"
```

Testing

```bash
testing-truth = "com.google.truth:truth:1.4.3"
testing-mockk = "io.mockk:mockk:1.13.11"
testing-coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3"
testing-turbine = "app.cash.turbine:turbine:1.1.0"
```

## Support links:
- https://kotlinlang.org/docs/serialization.html#formats
- https://truth.dev/
- https://mockk.io/ANDROID.html
- https://developer.android.com/kotlin/flow/test
- https://insert-koin.io/docs/quickstart/android-compose/
