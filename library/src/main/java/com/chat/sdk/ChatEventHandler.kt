package com.chat.sdk

import com.chat.sdk.events.button.ButtonEventHandler
import com.chat.sdk.events.chat_message_received.ChatMessageReceivedEventHandler
import com.chat.sdk.events.chat_message_sent.ChatMessageSentEventHandler
import com.chat.sdk.events.close.CloseEventHandler
import com.chat.sdk.events.message_submit.MessageSubmitEventHandler
import com.chat.sdk.events.minimize.MinimizeEventHandler
import com.chat.sdk.events.open.OpenEventHandler
import com.chat.sdk.events.open_proactive.OpenProactiveEventHandler
import com.chat.sdk.events.ready.ReadyEventHandler
import com.chat.sdk.events.start_callme.StartCallmeEventHandler
import com.chat.sdk.events.start_chat.StartChatEventHandler
import com.chat.sdk.events.switch_widget.SwitchWidgetEventHandler

/**
 * Abstraction of a centralized chat event handler which extends all the event handlers.
 */
interface ChatEventHandler :
    CloseEventHandler,
    ChatMessageReceivedEventHandler,
    ButtonEventHandler,
    ChatMessageSentEventHandler,
    MessageSubmitEventHandler,
    MinimizeEventHandler,
    OpenEventHandler,
    OpenProactiveEventHandler,
    StartCallmeEventHandler,
    StartChatEventHandler,
    SwitchWidgetEventHandler,
    ReadyEventHandler