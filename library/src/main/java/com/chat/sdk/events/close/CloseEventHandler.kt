package com.chat.sdk.events.close

/**
 * Abstraction of the close event handler.
 */
interface CloseEventHandler {

    /**
     * Adds the given CloseEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addCloseListener(listener: CloseEventListener)

    /**
     * Removes the given CloseEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeCloseListener(listener: CloseEventListener)

}