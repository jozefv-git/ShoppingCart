package com.jozefv.shoppingcart.feature_shopping

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Test rule
 * https://junit.org/junit4/javadoc/4.12/org/junit/rules/TestRule.html
 *
 * In local unit tests the Main dispatcher that wraps the Android UI is unavailable
 * as unit tests are executed on a local JVM and not on Android Device
 *
 * For that reason, if we need to access main thread, we need to create rule to switch the dispatcher
 * otherwise exception for tested coroutine will be thrown.
 *
 * !!! This only applies to local unit tests.
 * You should not replace the Main dispatcher in instrumented tests where the real UI thread is available.
 * !!!
 *
 * https://developer.android.com/kotlin/coroutines/test#setting-main-dispatcher
 */


@ExperimentalCoroutinesApi
class MainCoroutineRule(private val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}