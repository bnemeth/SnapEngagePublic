package com.chat.sdk.events.button

import android.webkit.JavascriptInterface

/**
 * ButtonEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeButtonEventHandler : ButtonEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param botName Bot name (alias) that sent button message
     * @param buttonLabel Label of the button
     * @param buttonValue Value of the button
     */
    @JavascriptInterface
    fun onButtonEvent(botName: String?, buttonLabel: String?, buttonValue: String?)

}