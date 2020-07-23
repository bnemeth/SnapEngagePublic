package com.chat.sdk.error_handling

/**
 * Exception for invalid chat configurations.
 *
 * @constructor creates a new instance of InvalidConfigurationException based on a cause throwable.
 *
 * @param cause is the description of the problem.
 */
class InvalidConfigurationException(cause : Throwable) : InternalException(cause)