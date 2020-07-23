package com.chat.sdk.events.open_proactive

/**
 * Listener for OpenProactiveEvents.
 */
interface OpenProactiveEventListener {

    /**
     * The OpenProactive event is fired when the proactive chat is displayed to a visitor. (Note, when the visitor replies, the StartChat event fires.)
     * This callback can return two pieces of info: the name of the agent used in the proactive message, and the proactive msg itself.
     *
     * @param agent the agent alias.
     * @param msg the proactive prompt message.
     */
    fun onOpenProactive(agent: String?, msg: String?)

}