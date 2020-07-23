package com.chat.sdk.events.chat_message_sent

/**
 * Listener for ChatMessageSentEvents.
 */
interface ChatMessageSentEventListener {

    /**
     * The ChatMessageSent event is fired when the visitor submits a chat message while in the chat session.
     * This callback can return one piece of info: the msg that was sent by the visitor.
     *
     * @param msg The message that was sent by the visitor.
     */
    fun onChatMessageSent(msg : String?)

}