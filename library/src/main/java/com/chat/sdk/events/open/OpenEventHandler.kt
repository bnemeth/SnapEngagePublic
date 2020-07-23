package com.chat.sdk.events.open

/**
 * Abstraction of the open event handler.
 */
interface OpenEventHandler {

    /**
     * Adds the given OpenEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addOpenListener(listener: OpenEventListener)

    /**
     * Removes the given OpenEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeOpenListener(listener: OpenEventListener)

}