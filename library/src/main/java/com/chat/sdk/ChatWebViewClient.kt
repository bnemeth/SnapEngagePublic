package com.chat.sdk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.*

/**
 * WebViewClient class to handle errors and to perform navigation on link clicks.
 *
 * @property onReceivedError Closure that will be invoked after an error occurred.
 */
internal class ChatWebViewClient(
    private val onReceivedError: (Int, String, String) -> Unit
) : WebViewClient() {

    /**
     * A callback that triggers if an error occured. Used in API level 23 and higher.
     *
     * @param view The WebView that is initiating the callback.
     * @param request The originating request.
     * @param error Information about the error occurred.
     */
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            onReceivedError(error?.errorCode ?: 0, request?.url.toString(), error?.description.toString())
        }
    }

    /**
     * A callback that triggers if an error occured. Used under API level 23.
     *
     * @param view The WebView that is initiating the callback.
     * @param errorCode The error code corresponding to an ERROR_* value.
     * @param description A String describing the error.
     * @param failingUrl The url that failed to load.
     */
    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onReceivedError(errorCode, failingUrl.orEmpty(), description.orEmpty())
        }
    }

    /**
     * A callback that triggers if before an URL begins to load. In this case, the application will open the link in an external browser.
     *
     * @param view The WebView that is initiating the callback.
     * @param request Object containing the details of the request.
     * @return true to cancel the current load, otherwise return false.
     */
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        request?.let {
            openUrlInExternalBrowser(view?.context, request.url)
        }
        return true
    }

    /**
     * A callback that triggers if before an URL begins to load. In this case, the application will open the link in an external browser.
     *
     * @param view The WebView that is initiating the callback.
     * @param url The URL to be loaded.
     * @return true to cancel the current load, otherwise return false.
     */
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            openUrlInExternalBrowser(view?.context, Uri.parse(url))
        }
        return true
    }

    /**
     * Open url in external browser
     *
     * @param context Context.
     * @param url The URL to be loaded.
     */
    private fun openUrlInExternalBrowser(context: Context?, url: Uri) {
        val i = Intent(Intent.ACTION_VIEW, url)
        context?.startActivity(i)
    }
}