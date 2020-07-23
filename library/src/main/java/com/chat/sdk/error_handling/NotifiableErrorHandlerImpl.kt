package com.chat.sdk.error_handling

/**
 * Implementation of the error handler.
 */
internal class NotifiableErrorHandlerImpl : NotifiableErrorHandler {

    /**
     * Lists of the registered ErrorListeners.
     */
    private val listeners = mutableListOf<ErrorListener>()

    /**
     * Adds the given ErrorListener to this handler.
     *
     * @param listener is the listener you want to register.
     */
    override fun addErrorListener(listener: ErrorListener) {
        listeners.add(listener)
    }

    /**
     * Removes the given ErrorListener from this handler.
     *
     * @param listener is the listener you want to unregister.
     */
    override fun removeErrorListener(listener: ErrorListener) {
        listeners.remove(listener)
    }

    /**
     * Invokes the registered listeners with the given network exception
     *
     * @param networkException network load error
     */
    override fun notifyNetworkError(networkException: NetworkException) {
        listeners.forEach {
            it.onNetworkError(networkException)
        }
    }

    /**
     * Invokes the registered listeners with the given internal exception
     *
     * @param internalError internal HTML load error
     */
    override fun notifyInternalError(internalError: InternalException) {
        listeners.forEach {
            it.onInternalError(internalError)
        }
    }

}
