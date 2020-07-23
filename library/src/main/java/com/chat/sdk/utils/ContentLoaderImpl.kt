package com.chat.sdk.utils

import android.content.Context
import android.content.res.AssetManager
import com.chat.sdk.Chat

/**
 * Class to load the predefined chat HTML from the AssetManager and substitute the placeholders based on the given Configuration.
 *
 * @property jsBlockAssembler utility class to assemble the custom variables javascript block.
 * @property onContentLoadSuccess closure that will be invoked if the process was success.
 * @property onContentLoadFail closure that will be invoked if the process failed.
 */
internal class ContentLoaderImpl(
    private val jsBlockAssembler: JavascriptVariablesBlockAssembler,
    private val onContentLoadSuccess: (String) -> Unit,
    private val onContentLoadFail: (Throwable) -> Unit
) : ContentLoader {

    /**
     * Starts the load
     *
     * @param context android context to acquire the AssetManager
     * @param configuration chat configuration that will be used to replace placeholders.
     */
    override fun load(context: Context, configuration : Chat.Configuration) {
        try {
            val content = readContent(context.assets)
            val substitute = substitute(content, configuration)
            onContentLoadSuccess(substitute)
        } catch (throwable: Throwable) {
            onContentLoadFail(throwable)
        }
    }

    /**
     * Reads the raw HTML code from the assets manager.
     *
     * @param assetManager android asset manager to get the HTML.
     * @return raw HTML code from the asset manager.
     */
    @Throws(Throwable::class)
    private fun readContent(assetManager: AssetManager): String {
        assetManager.open("chat.html").use {
            return String(it.readBytes())
        }
    }

    /**
     * Replaces all the placeholders with the configuration values.
     *
     * @param content is the raw HTML code.
     * @param config is the chat configuration.
     * @return HTML code with replaced fields based on the given configuration.
     */
    private fun substitute(content: String, config : Chat.Configuration): String {
        return content.replace("[provider]", config.provider, false)
            .replace("[jsUrl]", config.jsUrl, false)
            .replace("[customVariables]", jsBlockAssembler.assemble(config.customVariables?.javascriptVariables), false)
    }

}