package com.chat.sdk.events.minimize

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Abstraction of the open event handler.
 */
internal class MinimizeEventHandlerImpl : EventHandler<MinimizeEventListener>(), BridgeMinimizeEventHandler {

    /**
     * Adds the given MinimizeEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addMinimizeListener(listener: MinimizeEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given MinimizeEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeMinimizeListener(listener: MinimizeEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param isMinimized The state of the chat box AFTER the visitor has clicked.
     * @param chatType The type of chat the visitor is using.
     * @param boxType The type of the chat box the visitor sees.
     */
    @JavascriptInterface
    override fun onMinimize(isMinimized: String?, chatType: String?, boxType: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onMinimize(isMinimized.equals("true"), chatType, boxType)
            }
        }
    }

}