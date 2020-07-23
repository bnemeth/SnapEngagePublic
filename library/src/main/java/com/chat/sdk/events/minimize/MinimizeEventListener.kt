package com.chat.sdk.events.minimize

/**
 * Listener for MinimizeEvents.
 */
interface MinimizeEventListener {

    /**
     * The Minimize event is triggered when the visitor clicks the minimize icon in the chat box, or during live and active chats when they click on the minimized 'button' to maximize the chat box.
     *
     * @param isMinimized The state of the chat box AFTER the visitor has clicked.
     * @param chatType The type of chat the visitor is using.
     * @param boxType The type of the chat box the visitor sees.
     */
    fun onMinimize(isMinimized : Boolean?, chatType : String?, boxType : String?)

}