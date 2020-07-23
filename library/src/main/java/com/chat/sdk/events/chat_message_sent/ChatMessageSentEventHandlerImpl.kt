package com.chat.sdk.events.chat_message_sent

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the chat message sent event handler.
 */
internal class ChatMessageSentEventHandlerImpl : EventHandler<ChatMessageSentEventListener>(),
    BridgeChatMessageSentEventHandler {

    /**
     * Adds the given ChatMessageSentEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addChatMessageSentListener(listener: ChatMessageSentEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given ChatMessageSentEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeChatMessageSentListener(listener: ChatMessageSentEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param msg The message that was sent by the visitor.
     */
    @JavascriptInterface
    override fun onChatMessageSent(msg: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onChatMessageSent(msg)
            }
        }
    }

}