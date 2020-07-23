package com.chat.sdk.events.switch_widget

import android.webkit.JavascriptInterface
import com.chat.sdk.events.EventHandler

/**
 * Implementation of the SwitchWidgetEventHandler.
 */
internal class SwitchWidgetEventHandlerImpl : EventHandler<SwitchWidgetEventListener>(),
    BridgeSwitchWidgetEventHandler {

    /**
     * Adds the given SwitchWidgetEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addSwitchWidgetListener(listener: SwitchWidgetEventListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given SwitchWidgetEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeSwitchWidgetListener(listener: SwitchWidgetEventListener) {
        listeners.remove(listener)
    }

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param newWidgetId The widgetId of the selected option.
     */
    @JavascriptInterface
    override fun onSwitchWidget(newWidgetId: String?) {
        uiHandler.post {
            listeners.forEach {
                it.onSwitchWidget(newWidgetId)
            }
        }
    }

}
