package com.chat.sdk.events.close

import android.webkit.JavascriptInterface

/**
 * CloseEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeCloseEventHandler : CloseEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param type The type of window that was closed (see table of values below).
     * @param status The widget status when the close event was fired (see table of values below).
     */
    @JavascriptInterface
    fun onClose(type: String?, status: String?)

}