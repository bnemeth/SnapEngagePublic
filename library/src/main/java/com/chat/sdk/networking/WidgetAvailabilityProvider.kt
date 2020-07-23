package com.chat.sdk.networking

import androidx.annotation.WorkerThread
import java.lang.Exception

/**
 * Abstraction of widget availability provider
 */
interface WidgetAvailabilityProvider {

    /**
     * Get WidgetAvailability
     *
     * @throws Exception if any error occurred
     *
     * @param baseInstanceUrl is the base url without the widgetId, which can be used to call HTTP APIs.
     * @param widgetId id of the widget
     * @return instance of WidgetAvailability
     */
    @Throws(Exception::class)
    @WorkerThread
    fun checkWidgetAvailability(baseInstanceUrl: String, widgetId: String): WidgetAvailability

}