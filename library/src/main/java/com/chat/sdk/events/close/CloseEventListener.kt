package com.chat.sdk.events.close

/**
 * Listener for CloseEvents.
 */
interface CloseEventListener {

    /**
     * The Close event is fired when a close button is clicked.
     * This callback can return two pieces of info: type of window that was closed, and widget status when the close event was fired.
     *
     * @param type The type of window that was closed (see table of values below).
     * @param status The widget status when the close event was fired (see table of values below).
     */
    fun onClose(type : String?, status : String?)

}