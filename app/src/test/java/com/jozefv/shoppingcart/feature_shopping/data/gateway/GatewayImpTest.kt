package com.jozefv.shoppingcart.feature_shopping.data.gateway

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jozefv.shoppingcart.feature_shopping.domain.ResourceResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GatewayImpTest {
    private lateinit var gatewayImp: GatewayImp
    private val apiMock = mockk<GatewayApi>()

    @Before
    fun setUp(){
        gatewayImp = GatewayImp(apiMock)
    }

    @Test
    fun `When api respond with code 200, we should emit ResourceResult Success with code 200`() = runTest{
        // Simulate code 200 response from the API - so we don't need to do actual api call as we want to save resources
        // additionally unit tests should be fast
        coEvery { apiMock.serverResponse() } returns 200

        val expectedFirstEmission = ResourceResult.Loading(data = "Contacting gateway...")
        val expectedSecondEmission = ResourceResult.Success(data = "Result code is: 200 - Success.")
        val expectedThirdEmission = ResourceResult.Loading(data = "Redirecting to main screen...")
        val expectedForthEmission = ResourceResult.Loading(isLoading = false)

        gatewayImp.paymentResult().test {
            // Each awaitItem represents one emission
            assertThat(awaitItem()).isEqualTo(expectedFirstEmission)
            assertThat(awaitItem()).isEqualTo(expectedSecondEmission)
            assertThat(awaitItem()).isEqualTo(expectedThirdEmission)
            assertThat(awaitItem()).isEqualTo(expectedForthEmission)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When api respond with code 400, we should emit ResourceResult Error with code 400` () = runTest {
        coEvery { apiMock.serverResponse() } returns 400

        val expectedFirstEmission = ResourceResult.Loading(data = "Contacting gateway...")
        val expectedSecondEmission = ResourceResult.Error(data = "Result code is: 400 - Error.")
        val expectedThirdEmission = ResourceResult.Loading(data = "Redirecting to main screen...")
        val expectedForthEmission = ResourceResult.Loading(isLoading = false)

        gatewayImp.paymentResult().test {
            assertThat(awaitItem()).isEqualTo(expectedFirstEmission)
            assertThat(awaitItem()).isEqualTo(expectedSecondEmission)
            assertThat(awaitItem()).isEqualTo(expectedThirdEmission)
            assertThat(awaitItem()).isEqualTo(expectedForthEmission)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When api respond with other code, we should emit ResourceResult Error with code 500` () = runTest {
        coEvery { apiMock.serverResponse() } returns 505

        val expectedFirstEmission = ResourceResult.Loading(data = "Contacting gateway...")
        val expectedSecondEmission = ResourceResult.Error(data = "Result code is: 500 - Error.")
        val expectedThirdEmission = ResourceResult.Loading(data = "Redirecting to main screen...")
        val expectedForthEmission = ResourceResult.Loading(isLoading = false)

        gatewayImp.paymentResult().test {
            assertThat(awaitItem()).isEqualTo(expectedFirstEmission)
            assertThat(awaitItem()).isEqualTo(expectedSecondEmission)
            assertThat(awaitItem()).isEqualTo(expectedThirdEmission)
            assertThat(awaitItem()).isEqualTo(expectedForthEmission)
            cancelAndIgnoreRemainingEvents()
        }
    }

}