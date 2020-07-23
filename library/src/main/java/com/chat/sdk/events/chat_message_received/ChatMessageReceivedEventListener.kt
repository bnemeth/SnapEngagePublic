package com.chat.sdk.events.chat_message_received

/**
 * Listener for ChatMessageReceivedEvents.
 */
interface ChatMessageReceivedEventListener {

    /**
     * The ChatMessageReceived event is fired when a message from the agent is received by the visitor.
     * This callback can return two pieces of info: name of the agent that sent the message, and the msg that was sent.
     *
     * @param agent The agent alias.
     * @param msg The message that was received by the visitor.
     */
    fun onChatMessageReceived(agent : String?, msg : String?)

}