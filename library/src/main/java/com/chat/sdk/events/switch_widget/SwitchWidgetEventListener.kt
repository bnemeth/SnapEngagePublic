package com.chat.sdk.events.switch_widget

/**
 * Listener for SwitchWidgetEvents.
 */
interface SwitchWidgetEventListener {

    /**
     * The switchWidget event is triggered when the visitor has selected a value from the available dropdown list options. It will return the value of the newWidgetId.
     *
     * @param newWidgetId The widgetId of the selected option.
     */
    fun onSwitchWidget(newWidgetId: String?)

}
