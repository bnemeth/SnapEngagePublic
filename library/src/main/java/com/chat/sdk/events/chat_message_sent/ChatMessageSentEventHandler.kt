package com.chat.sdk.events.chat_message_sent

/**
 * Abstraction of the chat message sent event handler.
 */
interface ChatMessageSentEventHandler {

    /**
     * Adds the given ChatMessageSentEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addChatMessageSentListener(listener: ChatMessageSentEventListener)

    /**
     * Removes the given ChatMessageSentEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeChatMessageSentListener(listener: ChatMessageSentEventListener)

}