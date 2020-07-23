package com.chat.sdk

import android.webkit.ValueCallback

/**
 * Class that stores a pending javascript action.
 *
 * @property javascript The javascript action which will be evaluated.
 * @property resultCallback Callback that invokes after the javascript evaluated.
 */
internal class JavascriptAction(val javascript: String, val resultCallback: ValueCallback<String?>? = null)