package com.chat.sdk.events.chat_message_received

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the chat message received event handler.
 */
internal class ChatMessageReceivedEventHandlerImpl : EventHandler<ChatMessageReceivedEventListener>(),
    BridgeChatMessageReceivedEventHandler {

    /**
     * Adds the given ChatMessageReceivedEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addChatMessageReceivedListener(listener: ChatMessageReceivedEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given ChatMessageReceivedEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeChatMessageReceivedListener(listener: ChatMessageReceivedEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param agent The agent alias.
     * @param msg The message that was received by the visitor.
     */
    @JavascriptInterface
    override fun onChatMessageReceived(agent: String?, msg: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onChatMessageReceived(agent, msg)
            }
        }
    }

}