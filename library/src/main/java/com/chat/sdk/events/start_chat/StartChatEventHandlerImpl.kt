package com.chat.sdk.events.start_chat

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the start chat event handler.
 */
internal class StartChatEventHandlerImpl : EventHandler<StartChatEventListener>(), BridgeStartChatEventHandler {

    /**
     * Adds the given StartChatListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addStartChatListener(listener: StartChatEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given StartChatListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeStartChatListener(listener: StartChatEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param email The visitor's email address.
     * @param msg The visitor's first message.
     * @param type The type of chat.
     */
    @JavascriptInterface
    override fun onStartChat(email: String?, msg: String?, type: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onStartChat(email, msg, type)
            }
        }
    }

}