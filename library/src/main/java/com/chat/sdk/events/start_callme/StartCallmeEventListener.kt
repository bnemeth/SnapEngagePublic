package com.chat.sdk.events.start_callme

/**
 * Listener for StartCallmeEvents.
 */
interface StartCallmeEventListener {

    /**
     * The StartCallMe event is fired when the visitor starts a call-me.
     * This callback can return one piece of info: the visitor's phone number.
     *
     * @param phone The visitor's phone number.
     */
    fun onStartCallme(phone : String?)

}