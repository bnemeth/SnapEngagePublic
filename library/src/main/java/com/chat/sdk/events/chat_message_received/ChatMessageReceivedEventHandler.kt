package com.chat.sdk.events.chat_message_received

/**
 * Abstraction of the chat message received event handler.
 */
interface ChatMessageReceivedEventHandler {

    /**
     * Adds the given ChatMessageReceivedEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addChatMessageReceivedListener(listener: ChatMessageReceivedEventListener)

    /**
     * Removes the given ChatMessageReceivedEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeChatMessageReceivedListener(listener: ChatMessageReceivedEventListener)
}