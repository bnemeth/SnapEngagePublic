package com.chat.sdk

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient

/**
 * Listener to create file chooser
 */
interface ShowFileChooserListener {

    /**
     * Tell the client to show a file chooser.
     *
     * This is called to handle HTML forms with 'file' input type, in response to the
     * user pressing the "Select File" button.
     * To cancel the request, call <code>filePathCallback.onReceiveValue(null)</code> and
     * return {@code true}.
     *
     * @param filePathCallback Invoke this callback to supply the list of paths to files to upload,
     *                         or {@code null} to cancel. Must only be called if the
     *                         {@link #onShowFileChooser} implementation returns {@code true}.
     * @param fileChooserParams Describes the mode of file chooser to be opened, and options to be
     *                          used with it.
     * @return {@code true} if filePathCallback will be invoked, {@code false} to use default
     *         handling.
     */
    fun onShowFileChooser(filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: WebChromeClient.FileChooserParams?) : Boolean
}