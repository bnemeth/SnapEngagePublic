package com.chat.sdk.events.ready

import android.webkit.JavascriptInterface

/**
 * ReadyEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeReadyEventHandler : ReadyEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     */
    @JavascriptInterface
    fun onReady()

}