package com.chat.sdk.error_handling

/**
 * Abstraction of the error handler.
 * Empty implementation of ErrorListener. Useful if you do not want to handle all the errors.
 */
abstract class AbstractErrorListener : ErrorListener {

    /**
     * This event fires when a network load error occurred.
     *
     * @param networkException the network load error.
     */
    override fun onNetworkError(networkException: NetworkException) {}

    /**
     * This event fires if the SDK cannot load the chat internally.
     *
     * @param internalException the internal error.
     */
    override fun onInternalError(internalException: InternalException) {}

}