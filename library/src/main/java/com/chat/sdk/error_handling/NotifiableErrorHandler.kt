package com.chat.sdk.error_handling

/**
 * Error handler with notify methods.
 */
internal interface NotifiableErrorHandler : ErrorHandler {

    /**
     * Invokes the registered listeners with the given network exception
     *
     * @param networkException network load error
     */
    fun notifyNetworkError(networkException: NetworkException)

    /**
     * Invokes the registered listeners with the given internal exception
     *
     * @param internalError internal HTML load error
     */
    fun notifyInternalError(internalError: InternalException)

}