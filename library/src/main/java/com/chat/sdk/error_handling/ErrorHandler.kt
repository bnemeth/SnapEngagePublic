package com.chat.sdk.error_handling

/**
 * Abstraction of the error handler.
 */
interface ErrorHandler {

    /**
     * Adds the given ErrorListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    fun addErrorListener(listener: ErrorListener)

    /**
     * Removes the given ErrorListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    fun removeErrorListener(listener: ErrorListener)

}