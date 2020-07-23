package com.chat.sdk.events.open

import android.webkit.JavascriptInterface

/**
 * OpenEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeOpenEventHandler : OpenEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param status The widget status at the time the event was fired.
     */
    @JavascriptInterface
    fun onOpen(status: String?)

}