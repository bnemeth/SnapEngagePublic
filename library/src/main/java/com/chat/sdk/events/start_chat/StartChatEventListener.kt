package com.chat.sdk.events.start_chat

/**
 * Listener for StartChatEvents.
 */
interface StartChatEventListener {

    /**
     * The StartChat event is fired when the visitor starts a chat, or responds to a proactive invitation.
     * This callback can return three pieces of info: the visitor's email, the visitor's first msg, and the type of chat (manual or proactive).
     *
     * @param email The visitor's email address.
     * @param msg The visitor's first message.
     * @param type The type of chat.
     */
    fun onStartChat(email: String?, msg: String?, type: String?)

}