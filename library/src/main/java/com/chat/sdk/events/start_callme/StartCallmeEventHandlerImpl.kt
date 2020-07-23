package com.chat.sdk.events.start_callme

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the start call me event handler.
 */
internal class StartCallmeEventHandlerImpl : EventHandler<StartCallmeEventListener>(),
    BridgeStartCallmeEventHandler {

    /**
     * Adds the given StartCallmeEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addStartCallMeListener(listener: StartCallmeEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given StartCallmeEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeStartCallMeListener(listener: StartCallmeEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param phone The visitor's phone number.
     */
    @JavascriptInterface
    override fun onStartCallMe(phone: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onStartCallme(phone)
            }
        }
    }

}