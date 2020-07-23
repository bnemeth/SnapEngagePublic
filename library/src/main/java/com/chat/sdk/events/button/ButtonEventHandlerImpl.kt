package com.chat.sdk.events.button

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the button event handler.
 */
internal class ButtonEventHandlerImpl : EventHandler<ButtonEventListener>(), BridgeButtonEventHandler {

    /**
     * Adds the given ButtonEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addButtonEventListener(listener: ButtonEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given ButtonEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeButtonEventListener(listener: ButtonEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param botName Bot name (alias) that sent button message
     * @param buttonLabel Label of the button
     * @param buttonValue Value of the button
     */
    @JavascriptInterface
    override fun onButtonEvent(botName: String?, buttonLabel: String?, buttonValue: String?) {
        uiHandler.post {
            val options = ButtonEventListener.Options(botName, buttonLabel, buttonValue)
            listeners.forEach {
                it.onButtonEvent(options)
            }
        }
    }

}