package com.chat.sdk.events.chat_message_sent

import android.webkit.JavascriptInterface

/**
 * ChatMessageSentEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeChatMessageSentEventHandler : ChatMessageSentEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param msg The message that was sent by the visitor.
     */
    @JavascriptInterface
    fun onChatMessageSent(msg: String?)

}