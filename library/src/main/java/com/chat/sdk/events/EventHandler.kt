package com.chat.sdk.events

import android.os.Handler
import android.os.Looper

/**
 * Abstract class for javascript bridge classes. It contains:
 * - a handler to post the listeners on the main thread
 * - list of listeners
 *
 * @param Listener generic type of the listener
 * @property uiHandler handler to post events on the main thread
 */
abstract class EventHandler<Listener>(
    protected val uiHandler: Handler = Handler(Looper.getMainLooper())
) {

    /**
     * list of the registered listeners
     */
    protected val listeners = mutableListOf<Listener>()

}