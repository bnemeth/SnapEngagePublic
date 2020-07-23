package com.chat.sdk.events.start_chat

import android.webkit.JavascriptInterface

/**
 * StartChatEventHandler interface with javascript bridge abstraction.
 */
interface BridgeStartChatEventHandler : StartChatEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param email The visitor's email address.
     * @param msg The visitor's first message.
     * @param type The type of chat.
     */
    @JavascriptInterface
    fun onStartChat(email: String?, msg: String?, type: String?)

}