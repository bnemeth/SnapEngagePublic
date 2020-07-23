package com.chat.sdk.events.button

/**
 * Listener for ButtonEvents.
 */
interface ButtonEventListener {

    /**
     * Object containing information about the button that got clicked.
     *
     * @property botName Bot name (alias) that sent button message
     * @property buttonLabel Label of the button
     * @property buttonValue Value of the button
     */
    data class Options(
        val botName: String?,
        val buttonLabel: String?,
        val buttonValue: String?
    )

    /**
     * The InlineButtonClicked event is fired when the visitor clicks on messages that are buttons.
     *
     * @param options Object containing information about the button that got clicked.
     */
    fun onButtonEvent(options: Options?)

}