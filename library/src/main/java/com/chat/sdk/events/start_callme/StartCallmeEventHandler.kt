package com.chat.sdk.events.start_callme

/**
 * Abstraction of the start call me event handler
 */
interface StartCallmeEventHandler {

    /**
     * Adds the given StartCallmeEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addStartCallMeListener(listener: StartCallmeEventListener)

    /**
     * Removes the given StartCallmeEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeStartCallMeListener(listener: StartCallmeEventListener)

}