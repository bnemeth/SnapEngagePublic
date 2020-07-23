package com.chat.sdk.events.minimize

import android.webkit.JavascriptInterface

/**
 * MinimizeEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeMinimizeEventHandler : MinimizeEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param isMinimized The state of the chat box AFTER the visitor has clicked.
     * @param chatType The type of chat the visitor is using.
     * @param boxType The type of the chat box the visitor sees.
     */
    @JavascriptInterface
    fun onMinimize(isMinimized: String?, chatType: String?, boxType: String?)

}