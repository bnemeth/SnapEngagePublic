package com.chat.sdk.events.open

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the open event handler.
 */
internal class OpenEventHandlerImpl : EventHandler<OpenEventListener>(), BridgeOpenEventHandler {

    /**
     * Adds the given OpenEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addOpenListener(listener: OpenEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given OpenEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeOpenListener(listener: OpenEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param status The widget status at the time the event was fired.
     */
    @JavascriptInterface
    override fun onOpen(status: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onOpen(status)
            }
        }
    }

}