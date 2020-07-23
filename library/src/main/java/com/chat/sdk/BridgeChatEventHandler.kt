package com.chat.sdk

/**
 * Abstraction of a centralized chat event handler which extends all the event handlers.
 * Also register and unregister javascript bridges.
 */
interface BridgeChatEventHandler : ChatEventHandler {

    /**
     * This method registers all the internal bridge objects
     *
     * @param registerHandler closure is a callback for the ChatWebView to register the javascript bridges.
     */
    fun registerJsInterfaces(registerHandler: (Any, String) -> Unit)

    /**
     * This method unregisters all the internal bridge objects
     *
     * @param unregisterHandler closure is a callback for the ChatWebView to unregister the javascript bridges.
     */
    fun unregisterJsInterfaces(unregisterHandler: (String) -> Unit)
}