package com.chat.sdk

import android.util.Log
import com.chat.sdk.events.button.BridgeButtonEventHandler
import com.chat.sdk.events.button.ButtonEventHandler
import com.chat.sdk.events.button.ButtonEventHandlerImpl
import com.chat.sdk.events.chat_message_received.BridgeChatMessageReceivedEventHandler
import com.chat.sdk.events.chat_message_received.ChatMessageReceivedEventHandler
import com.chat.sdk.events.chat_message_received.ChatMessageReceivedEventHandlerImpl
import com.chat.sdk.events.chat_message_sent.BridgeChatMessageSentEventHandler
import com.chat.sdk.events.chat_message_sent.ChatMessageSentEventHandler
import com.chat.sdk.events.chat_message_sent.ChatMessageSentEventHandlerImpl
import com.chat.sdk.events.close.BridgeCloseEventHandler
import com.chat.sdk.events.close.CloseEventHandler
import com.chat.sdk.events.close.CloseEventHandlerImpl
import com.chat.sdk.events.message_submit.BridgeMessageSubmitEventHandler
import com.chat.sdk.events.message_submit.MessageSubmitEventHandler
import com.chat.sdk.events.message_submit.MessageSubmitEventHandlerImpl
import com.chat.sdk.events.minimize.BridgeMinimizeEventHandler
import com.chat.sdk.events.minimize.MinimizeEventHandler
import com.chat.sdk.events.minimize.MinimizeEventHandlerImpl
import com.chat.sdk.events.open.BridgeOpenEventHandler
import com.chat.sdk.events.open.OpenEventHandler
import com.chat.sdk.events.open.OpenEventHandlerImpl
import com.chat.sdk.events.open_proactive.BridgeOpenProactiveEventHandler
import com.chat.sdk.events.open_proactive.OpenProactiveEventHandler
import com.chat.sdk.events.open_proactive.OpenProactiveEventHandlerImpl
import com.chat.sdk.events.ready.BridgeReadyEventHandler
import com.chat.sdk.events.ready.ReadyEventHandler
import com.chat.sdk.events.ready.ReadyEventHandlerImpl
import com.chat.sdk.events.start_callme.BridgeStartCallmeEventHandler
import com.chat.sdk.events.start_callme.StartCallmeEventHandler
import com.chat.sdk.events.start_callme.StartCallmeEventHandlerImpl
import com.chat.sdk.events.start_chat.BridgeStartChatEventHandler
import com.chat.sdk.events.start_chat.StartChatEventHandler
import com.chat.sdk.events.start_chat.StartChatEventHandlerImpl
import com.chat.sdk.events.switch_widget.BridgeSwitchWidgetEventHandler
import com.chat.sdk.events.switch_widget.SwitchWidgetEventHandler
import com.chat.sdk.events.switch_widget.SwitchWidgetEventHandlerImpl

/**
 * Implementation of a centralized event handler holder.
 *
 * @property closeEventHandler bridge object to listen for close events.
 * @property chatMessageReceivedEventHandler bridge object to listen for chat message received events.
 * @property buttonEventHandler bridge object to listen button events.
 * @property chatMessageSentEventHandler bridge object to listen chat message sent events.
 * @property messageSubmitEventHandler bridge object to listen message submit events.
 * @property minimizeEventHandler bridge object to listen minimize events.
 * @property openEventHandler bridge object to listen open events.
 * @property openProactiveEventHandler bridge object to listen open proactive events.
 * @property startCallmeEventHandler bridge object to listen start call me events.
 * @property startChatEventHandler bridge object to listen start chat events.
 * @property switchWidgetEventHandler bridge object to listen switch widget events.
 * @property readyEventHandler bridge object to listen ready events.
 */
internal class BridgeChatEventHandlerImpl(
    private val closeEventHandler: BridgeCloseEventHandler = CloseEventHandlerImpl(),
    private val chatMessageReceivedEventHandler: BridgeChatMessageReceivedEventHandler = ChatMessageReceivedEventHandlerImpl(),
    private val buttonEventHandler: BridgeButtonEventHandler = ButtonEventHandlerImpl(),
    private val chatMessageSentEventHandler: BridgeChatMessageSentEventHandler = ChatMessageSentEventHandlerImpl(),
    private val messageSubmitEventHandler: BridgeMessageSubmitEventHandler = MessageSubmitEventHandlerImpl(),
    private val minimizeEventHandler: BridgeMinimizeEventHandler = MinimizeEventHandlerImpl(),
    private val openEventHandler: BridgeOpenEventHandler = OpenEventHandlerImpl(),
    private val openProactiveEventHandler: BridgeOpenProactiveEventHandler = OpenProactiveEventHandlerImpl(),
    private val startCallmeEventHandler: BridgeStartCallmeEventHandler = StartCallmeEventHandlerImpl(),
    private val startChatEventHandler: BridgeStartChatEventHandler = StartChatEventHandlerImpl(),
    private val switchWidgetEventHandler: BridgeSwitchWidgetEventHandler = SwitchWidgetEventHandlerImpl(),
    private val readyEventHandler : BridgeReadyEventHandler = ReadyEventHandlerImpl()
) : BridgeChatEventHandler,
    CloseEventHandler by closeEventHandler,
    ChatMessageReceivedEventHandler by chatMessageReceivedEventHandler,
    ButtonEventHandler by buttonEventHandler,
    ChatMessageSentEventHandler by chatMessageSentEventHandler,
    MessageSubmitEventHandler by messageSubmitEventHandler,
    MinimizeEventHandler by minimizeEventHandler,
    OpenEventHandler by openEventHandler,
    OpenProactiveEventHandler by openProactiveEventHandler,
    StartCallmeEventHandler by startCallmeEventHandler,
    StartChatEventHandler by startChatEventHandler,
    SwitchWidgetEventHandler by switchWidgetEventHandler,
    ReadyEventHandler by readyEventHandler {

    /**
     * list of all handlers
     */
    private val handlers = listOf(
        closeEventHandler, chatMessageReceivedEventHandler, buttonEventHandler,
        chatMessageSentEventHandler, messageSubmitEventHandler, minimizeEventHandler,
        openEventHandler, openProactiveEventHandler, startCallmeEventHandler,
        startChatEventHandler, switchWidgetEventHandler, readyEventHandler
    )

    /**
     * This method registers all the internal bridge objects
     *
     * @param registerHandler closure is a callback for the ChatWebView to register the javascript bridges.
     */
    override fun registerJsInterfaces(registerHandler: (Any, String) -> Unit) {
        handlers.forEach {
            val bridgeName = it.javaClass.simpleName
            registerHandler(it, bridgeName)
            Log.e("RegisterBridge", bridgeName)
        }
    }

    /**
     * This method unregisters all the internal bridge objects
     *
     * @param unregisterHandler closure is a callback for the ChatWebView to unregister the javascript bridges.
     */
    override fun unregisterJsInterfaces(unregisterHandler: (String) -> Unit) {
        handlers.forEach {
            val bridgeName = it.javaClass.simpleName
            unregisterHandler(bridgeName)
            Log.e("UnregisterBridge", bridgeName)
        }
    }

}