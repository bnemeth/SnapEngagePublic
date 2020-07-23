package com.chat.sdk.events.open_proactive

/**
 * Abstraction of the open proactive event handler
 */
interface OpenProactiveEventHandler {

    /**
     * Adds the given OpenProactiveEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addOpenProactiveListener(listener: OpenProactiveEventListener)

    /**
     * Removes the given OpenProactiveEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeOpenProactiveListener(listener: OpenProactiveEventListener)

}
