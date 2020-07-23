package com.chat.sdk.networking

import java.lang.Error

/**
 * Data class which describe widget availability
 *
 * @property statusCode status
 * @property data widget data
 */
data class WidgetAvailability(val statusCode: Int, val data: Data) {

    /**
     * Data class which describe widget availability data
     *
     * @property widgetId widget id
     * @property online is widget online
     * @property emailRequired is email required
     * @property messageRequired is message required
     * @property screenshotRequired is screenshot required
     *
     */
    data class Data(
        val widgetId: String,
        val online: Boolean,
        val emailRequired: Boolean,
        val messageRequired: Boolean,
        val screenshotRequired: Boolean
    )

    /**
     * Data class which describe error
     *
     * @property message error message
     *
     */
    internal data class Error(val message : String)
}
