package com.chat.sdk.events.switch_widget

/**
 * Abstraction of the switch widget event handler
 */
interface SwitchWidgetEventHandler {

    /**
     * Adds the given SwitchWidgetEventListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addSwitchWidgetListener(listener: SwitchWidgetEventListener)

    /**
     * Removes the given SwitchWidgetEventListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeSwitchWidgetListener(listener: SwitchWidgetEventListener)

}
