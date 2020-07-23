package com.chat.sdk.error_handling

/**
 * Listener for Errors.
 */
interface ErrorListener {

    /**
     * This event fires when a network load error occurred.
     *
     * @param networkException the network load error.
     */
    fun onNetworkError(networkException: NetworkException)

    /**
     * This event fires if the SDK cannot load the chat internally.
     *
     * @param internalException the internal error.
     */
    fun onInternalError(internalException: InternalException)

}