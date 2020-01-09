package com.stegner.androiddex

import androidx.lifecycle.LiveData
import com.stegner.androiddex.util.Event
import org.junit.Assert.assertEquals

/**
 * Assertion for testing live event equal to what triggered the event
 */
fun assertLiveDataEventTriggered(liveData: LiveData<Event<Int>>, pokedexId: Int){
    val value = LiveDataTestUtil.getValue(liveData)
    assertEquals(value.getContentIfNotHandled(), pokedexId)
}

/**
 * Assertion for testing that a snackbar message was displayed
 */
fun assertSnackbarMessage(snackbarLiveData: LiveData<Event<Int>>, messageId: Int) {
    val value: Event<Int> = LiveDataTestUtil.getValue(snackbarLiveData)
    assertEquals(value.getContentIfNotHandled(), messageId)
}
