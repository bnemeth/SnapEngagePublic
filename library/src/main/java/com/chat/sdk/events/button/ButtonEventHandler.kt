package com.chat.sdk.events.button

/**
 * Abstraction of the button event handler.
 */
interface ButtonEventHandler {

    /**
     * Adds the given ButtonEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addButtonEventListener(listener: ButtonEventListener)

    /**
     * Removes the given ButtonEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeButtonEventListener(listener: ButtonEventListener)

}