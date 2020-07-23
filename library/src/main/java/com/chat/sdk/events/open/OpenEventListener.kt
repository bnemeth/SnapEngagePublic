package com.chat.sdk.events.open

/**
 * Listener for OpenEvents.
 */
interface OpenEventListener {

    /**
     * The Open event is fired when the chat form is opened. The form may be opened by the user directly (button click), or as the result of an API call.
     * This callback can return one piece of info: the widget status when the event was fired.
     *
     * @param status The widget status at the time the event was fired.
     */
    fun onOpen(status : String?)

}