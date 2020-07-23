package com.chat.sdk.events.start_chat

/**
 * Abstraction of the start chat event handler
 */
interface StartChatEventHandler {

    /**
     * Adds the given StartChatListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addStartChatListener(listener: StartChatEventListener)

    /**
     * Removes the given StartChatListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeStartChatListener(listener: StartChatEventListener)

}