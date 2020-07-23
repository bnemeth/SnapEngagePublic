package com.chat.sdk.events.ready

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the ready event handler.
 */
internal class ReadyEventHandlerImpl : EventHandler<ReadyEventListener>(), BridgeReadyEventHandler {

    /**
     * Adds the given ReadyEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addReadyEventListener(listener: ReadyEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given ReadyEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeReadyEventListener(listener: ReadyEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     */
    @JavascriptInterface
    override fun onReady() {
        uiHandler.post {
            listeners.forEach {
                it.onReady()
            }
        }
    }

}