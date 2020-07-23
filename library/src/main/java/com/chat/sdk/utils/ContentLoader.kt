package com.chat.sdk.utils

import android.content.Context
import com.chat.sdk.Chat

/**
 * Class to load the predefined chat HTML from the AssetManager and substitute the placeholders based on the given Configuration.
 */
internal interface ContentLoader {

    /**
     * Starts the load
     *
     * @param context android context to acquire the AssetManager
     * @param configuration chat configuration that will be used to replace placeholders.
     */
    fun load(context: Context, configuration : Chat.Configuration)

}