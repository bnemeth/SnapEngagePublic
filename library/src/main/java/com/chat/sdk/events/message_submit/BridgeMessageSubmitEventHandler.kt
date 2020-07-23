package com.chat.sdk.events.message_submit

import android.webkit.JavascriptInterface

/**
 * MessageSubmitEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeMessageSubmitEventHandler : MessageSubmitEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param email The visitor's email address.
     * @param msg The message.
     */
    @JavascriptInterface
    fun onMessageSubmit(email: String?, msg: String?)

}