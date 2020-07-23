package com.chat.sdk.utils

import com.chat.sdk.Chat
import com.chat.sdk.error_handling.InternalException

/**
 * Validates a chat configuration.
 */
interface ConfigurationValidator {

    /**
     * Validates a chat configuration.
     *
     * @param configuration what you want to validate.
     * @throws InternalException description of the failure.
     */
    @Throws(InternalException::class)
    fun validate(configuration: Chat.Configuration)

}