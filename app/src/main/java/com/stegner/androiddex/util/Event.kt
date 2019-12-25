package com.stegner.androiddex.util

import androidx.lifecycle.Observer


/**
 * Used as a wraooer fir data that is exposed via LiveData that represents an event
 *
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class Event<out T>(private val content: T){

    // suppresses compiler warning about private setter
    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if(hasBeenHandled){
            null
        }else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even it's already been handled
     */
    fun peekContent(): T = content
}


/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has already been handled.
 *
 * [onEventUnHandledContent] is *only* called if the [Event] has not been handled.
 */
class EventObserver<T>(private val onEventUnHandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnHandledContent(it)
        }
    }
}