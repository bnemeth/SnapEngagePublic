package com.chat.sdk.events.switch_widget

import android.webkit.JavascriptInterface

/**
 * SwitchWidgetEventHandler interface with javascript bridge abstraction.
 */
internal interface BridgeSwitchWidgetEventHandler : SwitchWidgetEventHandler {

    /**
     * Bridge method where the data comes from Javascript. It posted to all listeners on the main thread.
     *
     * @param newWidgetId The widgetId of the selected option.
     */
    @JavascriptInterface
    fun onSwitchWidget(newWidgetId: String?)

}