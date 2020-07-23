package com.chat.sdk.utils

import android.webkit.URLUtil
import com.chat.sdk.Chat
import com.chat.sdk.error_handling.InternalException
import com.chat.sdk.error_handling.InvalidConfigurationException
import java.lang.IllegalArgumentException
import java.net.MalformedURLException

/**
 * Validates a chat configuration.
 */
class ConfigurationValidatorImpl : ConfigurationValidator {

    /**
     * Validates a chat configuration.
     *
     * @param configuration what you want to validate.
     * @throws InternalException description of the failure.
     */
    @Throws(InternalException::class)
    override fun validate(configuration: Chat.Configuration) {
        if(configuration.widgetId.isEmpty()) {
            throw invalidWidgetId(configuration.widgetId)
        }
        if(configuration.provider.isEmpty()) {
            throw invalidProvider(configuration.provider)
        }
        if(!URLUtil.isNetworkUrl(configuration.jsUrl)) {
            throw invalidJsUrl(configuration.jsUrl)
        }
        if(!URLUtil.isNetworkUrl(configuration.baseInstanceUrl)) {
            throw invalidInstanceUrl(configuration.baseInstanceUrl)
        }
        if(!URLUtil.isNetworkUrl(configuration.entryPageUrl)) {
            throw invalidEntryPageUrl(configuration.entryPageUrl)
        }
    }

    /**
     * Method to create InvalidConfigurationException because of a null or empty widgetId.
     *
     * @param provider is the provider that was provided.
     */
    private fun invalidWidgetId(provider : String) =
        InvalidConfigurationException(IllegalArgumentException("Invalid provider provided: $provider"))

    /**
     * Method to create InvalidConfigurationException because of a null or empty provider.
     *
     * @param provider is the provider that was provided.
     */
    private fun invalidProvider(provider : String) =
        InvalidConfigurationException(IllegalArgumentException("Invalid provider provided: $provider"))

    /**
     * Method to create InvalidConfigurationException because of malformed javascript url.
     *
     * @param providedUrl is the javascript url that was provided.
     */
    private fun invalidJsUrl(providedUrl : String) =
        InvalidConfigurationException(MalformedURLException("Malformed js url provided: $providedUrl"))

    /**
     * Method to create InvalidConfigurationException because of malformed instance url.
     *
     * @param providedUrl is the javascript url that was provided.
     */
    private fun invalidInstanceUrl(providedUrl : String) =
        InvalidConfigurationException(MalformedURLException("Malformed instance url provided: $providedUrl"))

    /**
     * Method to create InvalidConfigurationException because of malformed entry page url.
     *
     * @param providedUrl is the entry page url that was provided.
     */
    private fun invalidEntryPageUrl(providedUrl : String) =
        InvalidConfigurationException(MalformedURLException("Malformed entry page url provided: $providedUrl"))

}