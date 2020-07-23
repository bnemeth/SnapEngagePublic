package com.chat.sdk.error_handling

/**
 * Internal error.
 *
 * @param cause The cause of the internal error.
 */
open class InternalException(cause: Throwable) : Exception(cause)