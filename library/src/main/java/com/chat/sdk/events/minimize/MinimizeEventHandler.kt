package com.chat.sdk.events.minimize

/**
 * Abstraction of the open event handler.
 */
interface MinimizeEventHandler {

    /**
     * Adds the given MinimizeEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addMinimizeListener(listener: MinimizeEventListener)

    /**
     * Removes the given MinimizeEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeMinimizeListener(listener: MinimizeEventListener)

}