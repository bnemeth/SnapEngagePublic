package com.chat.sdk.events.message_submit

/**
 * Listener for MessageSubmitEvents.
 */
interface MessageSubmitEventListener {

    /**
     * The MessageSubmit event is fired when the visitor submits an offline message (not a chat message).
     * This callback can return two pieces of info: the visitor's email, and the msg that was sent.
     *
     * @param email The visitor's email address.
     * @param msg The message.
     */
    fun onMessageSubmit(email: String?, msg: String?)

}