package com.chat.sdk.error_handling

import java.lang.RuntimeException

class WidgetAvailabilityException(val statusCode: Int, val failingUrl : String, message: String) : RuntimeException(message)