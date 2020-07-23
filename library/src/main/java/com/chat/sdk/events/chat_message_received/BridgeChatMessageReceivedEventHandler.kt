package com.chat.sdk.events.chat_message_received

import android.webkit.JavascriptInterface

/**
 * ChatMessageReceivedEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeChatMessageReceivedEventHandler : ChatMessageReceivedEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param agent The agent alias.
     * @param msg The message that was received by the visitor.
     */
    @JavascriptInterface
    fun onChatMessageReceived(agent: String?, msg: String?)

}