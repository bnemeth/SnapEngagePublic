package com.chat.sdk

import android.webkit.ValueCallback

/**
 * Shows the chat view, when the view is ready.
 *
 * @param resultCallback Callback that invokes after the javascript evaluated.
 */
fun Chat.startLink(resultCallback: ((String?) -> Unit)?) {
    this.startLink(resultCallback.asValueCallback())
}

/**
 * Hides the chat button, when the view is ready.
 *
 * @param resultCallback Callback that invokes after the javascript evaluated.
 */
fun Chat.hideButton(resultCallback: ((String?) -> Unit)?) {
    this.hideButton(resultCallback.asValueCallback())
}

/**
 * Show the chat button, when the view is ready.
 *
 * @param resultCallback Callback that invokes after the javascript evaluated.
 */
fun Chat.showButton(resultCallback: ((String?) -> Unit)?) {
    this.showButton(resultCallback.asValueCallback())
}

/**
 * Clears the cookies, when the view is ready.
 *
 * @param resultCallback Callback that invokes after the javascript evaluated.
 */
fun Chat.clearAllCookies(resultCallback: ((String?) -> Unit)?) {
    this.clearAllCookies(resultCallback.asValueCallback())
}

/**
 * This method creates a ValueCallback that invokes the given closure.
 */
private fun ((String?) -> Unit)?.asValueCallback() = this?.let { function ->
    object : ValueCallback<String?> {
        override fun onReceiveValue(value: String?) {
            function.invoke(value)
        }
    } as ValueCallback<String?>
}