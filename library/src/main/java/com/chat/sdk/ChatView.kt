package com.chat.sdk

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.ValueCallback
import android.widget.FrameLayout
import androidx.annotation.WorkerThread
import com.chat.sdk.error_handling.ErrorListener
import com.chat.sdk.events.button.ButtonEventListener
import com.chat.sdk.events.chat_message_received.ChatMessageReceivedEventListener
import com.chat.sdk.events.chat_message_sent.ChatMessageSentEventListener
import com.chat.sdk.events.close.CloseEventListener
import com.chat.sdk.events.message_submit.MessageSubmitEventListener
import com.chat.sdk.events.minimize.MinimizeEventListener
import com.chat.sdk.events.open.OpenEventListener
import com.chat.sdk.events.open_proactive.OpenProactiveEventListener
import com.chat.sdk.events.ready.ReadyEventListener
import com.chat.sdk.events.start_callme.StartCallmeEventListener
import com.chat.sdk.events.start_chat.StartChatEventListener
import com.chat.sdk.events.switch_widget.SwitchWidgetEventListener

/**
 * Wrapper view that hides the real implementation from the user and prevent to do any change on it.
 */
class ChatView private constructor(context: Context, attributeSet: AttributeSet? = null, private val chat: ChatWebView) :
    FrameLayout(context, attributeSet), Chat by chat {

    constructor(context: Context, attributeSet: AttributeSet? = null) : this(context, attributeSet, ChatWebView(context))

    /**
     * Initialization
     */
    init {
        addView(chat, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        visibility = View.GONE
    }
}