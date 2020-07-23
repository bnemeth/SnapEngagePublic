package com.chat.sdk.events.close

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Abstraction of the close event handler.
 */
internal class CloseEventHandlerImpl : EventHandler<CloseEventListener>(), BridgeCloseEventHandler {

    /**
     * Adds the given CloseEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addCloseListener(listener: CloseEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given CloseEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeCloseListener(listener: CloseEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param type The type of window that was closed (see table of values below).
     * @param status The widget status when the close event was fired (see table of values below).
     */
    @JavascriptInterface
    override fun onClose(type: String?, status: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onClose(type, status)
            }
        }
    }

}