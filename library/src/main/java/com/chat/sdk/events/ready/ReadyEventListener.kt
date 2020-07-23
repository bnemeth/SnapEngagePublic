package com.chat.sdk.events.ready

/**
 * Listener for ReadyEvents.
 */
interface ReadyEventListener {

    /**
     * onReady events fires when the chat loaded and ready to use.
     */
    fun onReady()

}