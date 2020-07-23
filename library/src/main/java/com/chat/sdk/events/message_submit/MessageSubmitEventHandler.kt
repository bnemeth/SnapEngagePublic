package com.chat.sdk.events.message_submit

/**
 * Abstraction of the message submit event handler.
 */
interface MessageSubmitEventHandler {

    /**
     * Adds the given MessageSubmitEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addMessageSubmitListener(listener: MessageSubmitEventListener)

    /**
     * Removes the given MessageSubmitEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeMessageSubmitListener(listener: MessageSubmitEventListener)

}