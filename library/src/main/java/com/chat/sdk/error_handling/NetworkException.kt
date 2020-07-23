package com.chat.sdk.error_handling

import java.io.IOException

/**
 * Network load error.
 *
 * @property clientWasReady true if the client was ready, false if not.
 * @property errorCode the given HTTP response code.
 * @property failingUrl the requested url.
 *
 * @param descriptor the cause of the exception.
 */
class NetworkException(
    val clientWasReady: Boolean,
    val errorCode: Int,
    val failingUrl: String,
    descriptor: String
) : IOException(descriptor)