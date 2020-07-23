package com.chat.sdk.events.open_proactive

import android.webkit.JavascriptInterface

/**
 * OpenProactiveEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeOpenProactiveEventHandler : OpenProactiveEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param agent the agent alias.
     * @param msg the proactive prompt message.
     */
    @JavascriptInterface
    fun onOpenProactive(agent: String?, msg: String?)

}