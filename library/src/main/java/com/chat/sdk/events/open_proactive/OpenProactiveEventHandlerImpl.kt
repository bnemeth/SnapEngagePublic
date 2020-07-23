package com.chat.sdk.events.open_proactive

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the open proactive event handler
 */
internal class OpenProactiveEventHandlerImpl : EventHandler<OpenProactiveEventListener>(),
    BridgeOpenProactiveEventHandler {

    /**
     * Adds the given OpenProactiveEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addOpenProactiveListener(listener: OpenProactiveEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given OpenProactiveEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeOpenProactiveListener(listener: OpenProactiveEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param agent the agent alias.
     * @param msg the proactive prompt message.
     */
    @JavascriptInterface
    override fun onOpenProactive(agent: String?, msg: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onOpenProactive(agent, msg)
            }
        }
    }

}
