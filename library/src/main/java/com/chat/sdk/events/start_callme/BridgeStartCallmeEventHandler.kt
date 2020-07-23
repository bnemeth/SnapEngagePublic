package com.chat.sdk.events.start_callme

import android.webkit.JavascriptInterface

/**
 * StartCallmeEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeStartCallmeEventHandler : StartCallmeEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param phone The visitor's phone number.
     */
    @JavascriptInterface
    fun onStartCallMe(phone: String?)

}