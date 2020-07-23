package com.chat.sdk.networking

import android.net.Uri
import androidx.annotation.WorkerThread
import com.chat.sdk.error_handling.WidgetAvailabilityException
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * Implementation of widget availability provider
 */
internal class WidgetAvailabilityProviderImpl : WidgetAvailabilityProvider {

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
    override fun checkWidgetAvailability(baseInstanceUrl: String, widgetId: String): WidgetAvailability {
        val uri = Uri.parse(baseInstanceUrl).buildUpon().appendPath(widgetId).build().toString()
        val connection: HttpURLConnection = URL(uri).openConnection() as HttpURLConnection
        try {
            return connection.inputStream.bufferedReader().use {
                val jsonObject = JSONObject(it.readText())
                if (jsonObject.has("error")) {
                    val errorJsonObject = jsonObject.getJSONObject("error").asWidgetAvailabilityError()
                    throw WidgetAvailabilityException(jsonObject.getStatusCode(), uri, errorJsonObject.message)
                }
                jsonObject.asWidgetAvailability()
            }
        } finally {
            connection.disconnect()
        }
    }

    /**
     * Get status code from JSONObject
     *
     * @return status code
     */
    private fun JSONObject.getStatusCode() = getInt("statusCode")

    /**
     * Convert JSONObject to WidgetAvailability
     *
     * @return instance of WidgetAvailability
     */
    private fun JSONObject.asWidgetAvailability() = WidgetAvailability(
        getStatusCode(),
        getJSONObject("data").asWidgetAvailabilityData()
    )

    /**
     * Convert JSONObject to WidgetAvailability.Data
     *
     * @return instance of WidgetAvailability.Data
     */
    private fun JSONObject.asWidgetAvailabilityData() = WidgetAvailability.Data(
        getString("widgetId"),
        getBooleanFromString("online"),
        getBooleanFromString("emailRequired"),
        getBooleanFromString("messageRequired"),
        getBooleanFromString("screenshotRequired")
    )

    /**
     * Convert JSONObject to WidgetAvailability.Error
     *
     * @return instance of WidgetAvailability.Error
     */
    private fun JSONObject.asWidgetAvailabilityError() = WidgetAvailability.Error(
        getString("message")
    )

    /**
     * Convert string parameter to boolean
     *
     * @return boolean representation of string
     */
    private fun JSONObject.getBooleanFromString(name: String) = getString(name) == "true"

}