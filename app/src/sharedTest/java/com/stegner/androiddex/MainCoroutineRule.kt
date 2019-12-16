package com.stegner.androiddex

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor

/**
 * Sets the main corountin dispather to [TestCoroutineScope] for unit testing.
 * A [TestCoroutineScope] provides control over the execution of coroutines
 *
 * Declare it as a JUnit Rule:
 *
 * ```
 * @get:Rule
 * var mainCoroutineRule = MainCoroutineRule()
 * ```
 *
 * Use it directory as a [TestCoroutineScope]
 *
 * ```
 * mainCoroutineRule.pauseDispatcher()
 * ...
 * mainCoroutineRule.resumeDispatcher()
 * ...
 * mainCoroutineRule.runBlockingTest { }
 * ...
 *
 * ```
 * TestWatcher is a base class for Rules that take note of the testing
 * action, without modifying it. For example, this class will keep a log of each
 * passing and failing test:
 *
 * TestCoroutineScope is A scope which provides detailed control over the execution of coroutines for tests.
 * If the provided context does not provide a ContinuationInterceptor (Dispatcher) or CoroutineExceptionHandler,
 * the scope adds TestCoroutineDispatcher and TestCoroutineExceptionHandler automatically
 *
 */

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

    /**
     * Invoked when a test is about to start
     *
     * We do this to set the dispatcher to the to the current context
     */
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}