package com.chat.sdk.events.message_submit

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the message submit event handler.
 */
internal class MessageSubmitEventHandlerImpl : EventHandler<MessageSubmitEventListener>(),
    BridgeMessageSubmitEventHandler {

    /**
     * Adds the given MessageSubmitEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addMessageSubmitListener(listener: MessageSubmitEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given MessageSubmitEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeMessageSubmitListener(listener: MessageSubmitEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param email The visitor's email address.
     * @param msg The message.
     */
    @JavascriptInterface
    override fun onMessageSubmit(email: String?, msg: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onMessageSubmit(email, msg)
            }
        }
    }

}