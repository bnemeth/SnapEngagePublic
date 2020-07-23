package com.snapengage.demo

import com.chat.sdk.Chat
import com.chat.sdk.error_handling.ErrorListener
import com.chat.sdk.error_handling.InternalException
import com.chat.sdk.error_handling.NetworkException
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

fun Chat.addTestListeners(log: (String) -> Unit) {
    addButtonEventListener(object : ButtonEventListener {
        override fun onButtonEvent(options: ButtonEventListener.Options?) {
            log("onButtonEvent(options = $options)")
        }
    })

    addChatMessageReceivedListener(object : ChatMessageReceivedEventListener {
        override fun onChatMessageReceived(agent: String?, msg: String?) {
            log("onChatMessageReceived(agent = $agent, msg = $msg)")
        }
    })

    addChatMessageSentListener(object : ChatMessageSentEventListener {
        override fun onChatMessageSent(msg: String?) {
            log("onChatMessageSent(msg = $msg)")
        }
    })

    addCloseListener(object : CloseEventListener {
        override fun onClose(type: String?, status: String?) {
            log("onClose(type = $type, status = $status)")
        }
    })

    addMessageSubmitListener(object : MessageSubmitEventListener {
        override fun onMessageSubmit(email: String?, msg: String?) {
            log("onMessageSubmit(email = $email, msg = $msg)")
        }
    })

    addMinimizeListener(object : MinimizeEventListener {
        override fun onMinimize(isMinimized: Boolean?, chatType: String?, boxType: String?) {
            log("onMinimize(isMinimized = $isMinimized, chatType = $chatType , boxType = $boxType)")
        }
    })

    addOpenListener(object : OpenEventListener {
        override fun onOpen(status: String?) {
            log("onOpen(status = $status)")
        }
    })

    addOpenProactiveListener(object : OpenProactiveEventListener {
        override fun onOpenProactive(agent: String?, msg: String?) {
            log("onOpenProactive(agent = $agent, msg = $msg)")
        }
    })

    addStartCallMeListener(object : StartCallmeEventListener {
        override fun onStartCallme(phone: String?) {
            log("onStartCallme(phone = $phone)")
        }
    })

    addStartChatListener(object : StartChatEventListener {
        override fun onStartChat(email: String?, msg: String?, type: String?) {
            log("onStartChat(email = $email, msg = $msg, type = $type)")
        }
    })

    addSwitchWidgetListener(object : SwitchWidgetEventListener {
        override fun onSwitchWidget(newWidgetId: String?) {
            log("onStartCallme(newWidgetId = $newWidgetId)")
        }
    })

    addErrorListener(object : ErrorListener {
        override fun onNetworkError(networkException: NetworkException) {
            log("onError(errorCode = ${networkException.errorCode}, failingUrl = ${networkException.failingUrl} message = ${networkException.message}")
        }
        override fun onInternalError(internalException: InternalException) {
            log("onInternalError(message = ${internalException.message})")
        }
    })

    addReadyEventListener(object : ReadyEventListener {
        override fun onReady() {
            log("onReady()")
        }
    })
}