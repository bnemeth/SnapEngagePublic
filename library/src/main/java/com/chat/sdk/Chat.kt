package com.chat.sdk

import android.net.Uri
import android.os.Parcelable
import android.webkit.URLUtil
import android.webkit.ValueCallback
import androidx.annotation.WorkerThread
import com.chat.sdk.error_handling.ErrorHandler
import com.chat.sdk.error_handling.InvalidConfigurationException
import com.chat.sdk.networking.WidgetAvailability
import kotlinx.android.parcel.Parcelize

/**
 * Abstraction of the chat. Holds the event handlers, error handler, and javascript actions.
 */
interface Chat : ChatEventHandler, ErrorHandler {

    /**
     * Class to configure the chat.
     *
     * @property widgetId is the identifier of your widget.
     * @property baseJsUrl is the base url where the chat javascript can be found without the widgetId . Has to be a valid URL.
     * @property provider is the provider of the chat.
     * @property baseInstanceUrl is the base url without the widgetId, which can be used to call HTTP APIs
     * @property entryPageUrl is the url what is going to be shown in the frontend side. Has to be a valid URL.
     * @property customVariables is the custom variables that can be passed to the javascript.
     */
    @Parcelize
    data class Configuration(
        val widgetId: String,
        val baseJsUrl: String,
        val provider: String,
        val entryPageUrl: String,
        val baseInstanceUrl: String,
        val customVariables: CustomVariables?
    ) : Parcelable {
        val jsUrl: String
            get() = Uri.parse(baseJsUrl).buildUpon().appendPath("$widgetId.js").build().toString()
    }

    /**
     * This variable represents that the chat has been loaded and ready to evaluate javascripts.
     */
    val isReady: Boolean

    /**
     * Setter to load a specific configuration to this View.
     *
     * @param configuration Configuration that specifies the chat.
     */
    fun setConfiguration(configuration: Configuration)

    /**
     * Shows the chat view, when the view is ready.
     *
     * @param resultCallback Callback that invokes after the javascript evaluated.
     */
    fun startLink(resultCallback: ValueCallback<String?>? = null)

    /**
     * Hides the chat button, when the view is ready.
     *
     * @param resultCallback Callback that invokes after the javascript evaluated.
     */
    fun hideButton(resultCallback: ValueCallback<String?>? = null)

    /**
     * Show the chat button, when the view is ready.
     *
     * @param resultCallback Callback that invokes after the javascript evaluated.
     */
    fun showButton(resultCallback: ValueCallback<String?>? = null)

    /**
     * Clears the cookies, when the view is ready.
     *
     * @param resultCallback Callback that invokes after the javascript evaluated.
     */
    fun clearAllCookies(resultCallback: ValueCallback<String?>? = null)

    /**
     * Reloads the current chat.
     */
    fun reload()

    /**
     * Check the current widget availability
     */
    @WorkerThread
    fun checkWidgetAvailability(): WidgetAvailability

    /**
     * Check widget availability
     * @param widgetId the id of the widget
     */
    @WorkerThread
    fun checkWidgetAvailability(widgetId: String): WidgetAvailability

    /**
     * Set file chooser listener (for file upload)
     *
     * @param showFileChooserListener File chooser listener
     */
    fun setShowFileChooserListener(showFileChooserListener: ShowFileChooserListener)
}