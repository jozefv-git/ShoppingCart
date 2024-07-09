package com.jozefv.shoppingcart.feature_shopping.presentation.checkout

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jozefv.shoppingcart.feature_shopping.MainCoroutineRule
import com.jozefv.shoppingcart.feature_shopping.data.form.FakeFormStorage
import com.jozefv.shoppingcart.feature_shopping.data.form.FakeFormValidator
import com.jozefv.shoppingcart.feature_shopping.data.gateway.GatewayApi
import com.jozefv.shoppingcart.feature_shopping.data.gateway.GatewayImp
import com.jozefv.shoppingcart.feature_shopping.domain.model.User
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CheckoutScreenViewModelTest {
    private lateinit var checkoutScreenViewModel: CheckoutScreenViewModel
    private lateinit var fakeFormValidator: FakeFormValidator
    private lateinit var gatewayImp: GatewayImp

    private val gatewayApiMock = mockk<GatewayApi>()
    private lateinit var fakeFormStorage: FakeFormStorage
    private val savedStateHandleMock = mockk<SavedStateHandle>()

    private val user = User(
        name = "Jozef",
        email = "jozef@mail.com",
        address = "Finland",
        card = "4000000000000"
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        fakeFormStorage = FakeFormStorage()
        gatewayImp = GatewayImp(gatewayApiMock)
        fakeFormValidator = FakeFormValidator(fakeFormStorage)

        every { savedStateHandleMock.get<String>("toBePaid") } returns "0.0"

        checkoutScreenViewModel = CheckoutScreenViewModel(
            savedStateHandle = savedStateHandleMock,
            gateway = gatewayImp,
            formValidator = fakeFormValidator,
        )
    }

    @Test
    fun `When start over is clicked, all user data are deleted, true`() {
        // First we need to have user which can be deleted
        //fakeFormStorage.saveUser(user)
        fakeFormValidator.saveUser()
        val expectedUser = user

        assertThat(fakeFormValidator.getUser()).isEqualTo(expectedUser)

        checkoutScreenViewModel.onAction(CheckoutAction.StartOver)

        val actualUser = fakeFormValidator.getUser()

        assertThat(actualUser).isEqualTo(null)
    }

    @Test
    fun `When GoBack is clicked, user data are saved, true`() {
        val expectedUser = user

        checkoutScreenViewModel.onAction(CheckoutAction.GoBack)

        val actualUser = fakeFormValidator.getUser()

        assertThat(actualUser).isEqualTo(expectedUser)
    }

    @Test
    fun `When correct name is inputted, name state should contain same name and isValidName state should be true`() {
        val expectedName = user.name
        val expectedValidity = true

        checkoutScreenViewModel.onAction(CheckoutAction.NameInput(expectedName))

        val actualNameState = checkoutScreenViewModel.checkoutState.name
        val actualIsValidNameState = checkoutScreenViewModel.checkoutState.isValidName

        assertThat(actualNameState).isEqualTo(expectedName)
        assertThat(actualIsValidNameState).isEqualTo(expectedValidity)

    }

    @Test
    fun `When name is erased, name state should be empty and isValidName state should be null`() {
        val expectedNameAfterErase = ""
        val expectedValidityAfterErase = null

        checkoutScreenViewModel.onAction(CheckoutAction.NameErased)

        val actualNameState = checkoutScreenViewModel.checkoutState.name
        val actualValidityState = checkoutScreenViewModel.checkoutState.isValidName

        assertThat(actualNameState).isEqualTo(expectedNameAfterErase)
        assertThat(actualValidityState).isEqualTo(expectedValidityAfterErase)

    }

    @Test
    fun `When correct email is inputted, email state should contain same email and isValidEmail state should be true`() {
        val expectedEmail = user.email
        val expectedValidity = true

        checkoutScreenViewModel.onAction(CheckoutAction.EmailInput(expectedEmail))

        val actualEmailState = checkoutScreenViewModel.checkoutState.email
        val actualIsValidEmailState = checkoutScreenViewModel.checkoutState.isValidEmail

        assertThat(actualEmailState).isEqualTo(expectedEmail)
        assertThat(actualIsValidEmailState).isEqualTo(expectedValidity)
    }

    @Test
    fun `When email is erased, email state should be empty and isValidEmail state should be null`() {
        val expectedEmailAfterErase = ""
        val expectedValidityAfterErase = null

        checkoutScreenViewModel.onAction(CheckoutAction.EmailErased)

        val actualEmailState = checkoutScreenViewModel.checkoutState.email
        val actualValidityState = checkoutScreenViewModel.checkoutState.isValidEmail

        assertThat(actualEmailState).isEqualTo(expectedEmailAfterErase)
        assertThat(actualValidityState).isEqualTo(expectedValidityAfterErase)

    }

    @Test
    fun `When correct address is inputted, address state should contain same address and isValidAddress state should be true`() {
        val expectedAddress = user.address
        val expectedValidity = true

        checkoutScreenViewModel.onAction(CheckoutAction.AddressInput(expectedAddress))

        val actualAddressState = checkoutScreenViewModel.checkoutState.address
        val actualIsValidAddressState = checkoutScreenViewModel.checkoutState.isValidAddress

        assertThat(actualAddressState).isEqualTo(expectedAddress)
        assertThat(actualIsValidAddressState).isEqualTo(expectedValidity)
    }

    @Test
    fun `When address is erased, address state should be empty and isValidAddress state should be null`() {
        val expectedAddressAfterErase = ""
        val expectedValidityAfterErase = null

        checkoutScreenViewModel.onAction(CheckoutAction.AddressErased)

        val actualAddressState = checkoutScreenViewModel.checkoutState.address
        val actualValidityState = checkoutScreenViewModel.checkoutState.isValidAddress

        assertThat(actualAddressState).isEqualTo(expectedAddressAfterErase)
        assertThat(actualValidityState).isEqualTo(expectedValidityAfterErase)

    }

    @Test
    fun `When correct card is inputted, card state should contain same card and isValidCard state should be true`() {
        val expectedCard = user.card
        val expectedValidity = true

        checkoutScreenViewModel.onAction(CheckoutAction.CardInput(expectedCard))

        val actualCardState = checkoutScreenViewModel.checkoutState.card
        val actualIsValidCardState = checkoutScreenViewModel.checkoutState.isValidCard

        assertThat(actualCardState).isEqualTo(expectedCard)
        assertThat(actualIsValidCardState).isEqualTo(expectedValidity)
    }

    @Test
    fun `When card is erased, card state should be empty and isValidAddress state should be null`() {
        val expectedCardAfterErase = ""
        val expectedValidityAfterErase = null

        checkoutScreenViewModel.onAction(CheckoutAction.CardErased)

        val actualCardState = checkoutScreenViewModel.checkoutState.card
        val actualValidityState = checkoutScreenViewModel.checkoutState.isValidCard

        assertThat(actualCardState).isEqualTo(expectedCardAfterErase)
        assertThat(actualValidityState).isEqualTo(expectedValidityAfterErase)
    }


    @Test
    fun `When make payment is clicked, navigate to main event should be send`() =
        // runTest will automatically skip any delays in coroutines
        runTest {
            // Server response will return mocked status 200 to our paymentResult.
            // At this point, result code doesn't really matter in here
            // as we every time we redirect to main screen
            coEvery { gatewayApiMock.serverResponse() } returns 200

            checkoutScreenViewModel.onAction(CheckoutAction.MakePayment)

            checkoutScreenViewModel.eventChannel.test {
                assertThat(awaitItem()).isEqualTo(CheckoutEvent.NavigateToMain)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When make payment is clicked, user should be saved`() =
        runTest {
            val expectedUser = user

            coEvery { gatewayApiMock.serverResponse() } returns 200

            checkoutScreenViewModel.onAction(CheckoutAction.MakePayment)

            // Runs all other coroutines on the scheduler until there is nothing left in the queue
            // After all emissions (paymentResult is proceeded - user should be saved)
            advanceUntilIdle()

            val actualUser = fakeFormValidator.getUser()

            assertThat(actualUser).isEqualTo(expectedUser)
        }
}