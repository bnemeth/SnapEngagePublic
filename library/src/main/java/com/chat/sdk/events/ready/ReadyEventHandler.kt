package com.chat.sdk.events.ready

/**
 * Abstraction of the ready event handler.
 */
interface ReadyEventHandler {

    /**
     * Adds the given ReadyEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addReadyEventListener(listener: ReadyEventListener)

    /**
     * Removes the given ReadyEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeReadyEventListener(listener: ReadyEventListener)

}