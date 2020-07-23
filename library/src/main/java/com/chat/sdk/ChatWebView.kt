package com.chat.sdk

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.webkit.*
import androidx.annotation.WorkerThread
import com.chat.sdk.error_handling.*
import com.chat.sdk.events.button.ButtonEventListener
import com.chat.sdk.events.chat_message_received.ChatMessageReceivedEventListener
import com.chat.sdk.events.chat_message_sent.ChatMessageSentEventListener
import com.chat.sdk.events.close.CloseEventListener
import com.chat.sdk.events.message_submit.MessageSubmitEventListener
import com.chat.sdk.events.minimize.MinimizeEventListener
import com.chat.sdk.events.open.OpenEventListener
import com.chat.sdk.events.open_proactive.OpenProactiveEventListener
import com.chat.sdk.events.ready.ReadyEventListener
import com.chat.sdk.events.start_callme.StartCallmeEventListener
import com.chat.sdk.events.start_chat.StartChatEventListener
import com.chat.sdk.events.switch_widget.SwitchWidgetEventListener
import com.chat.sdk.networking.WidgetAvailability
import com.chat.sdk.networking.WidgetAvailabilityProvider
import com.chat.sdk.networking.WidgetAvailabilityProviderImpl
import com.chat.sdk.utils.*

/**
 *
 * Internal view that responsible for to display the chat and the communication between the javascript library and the android app with bridge objects. Also delegates the events to the corresponding objects.
 */
internal class ChatWebView(context: Context) : WebView(context), Chat {

    /**
     * Widget availability provider
     */
    private val widgetAvailabilityProvider: WidgetAvailabilityProvider = WidgetAvailabilityProviderImpl()

    /**
     * Validator for configurations.
     */
    private val configurationValidator: ConfigurationValidator = ConfigurationValidatorImpl()

    /**
     * This object is responsible to load the local HTML code, and replace some placeholders with correct values.
     */
    private val contentLoader: ContentLoader =
        ContentLoaderImpl(JavascriptVariablesBlockAssemblerImpl(), ::onContentLoadSuccess, ::onContentLoadFail)

    /**
     * This object is responsible to handle the communication between the javascript and android.
     */
    private val eventHandler: BridgeChatEventHandler = BridgeChatEventHandlerImpl()

    /**
     * This object is responsible for the error handling.
     */
    private val errorHandler: NotifiableErrorHandler = NotifiableErrorHandlerImpl()

    /**
     * This variable stores the current configuration
     */
    private var configuration: Chat.Configuration? = null

    /**
     * Computed field to get the current provider or empty string if not set.
     */
    private val provider: String
        get() = configuration?.provider.orEmpty()

    /**
     * This variable represents that the chat has been loaded and ready to evaluate javascripts.
     */
    @Volatile
    override var isReady: Boolean = false
        private set

    /**
     * List of javascript actions that has been called before the view was ready.
     */
    private val pendingJavascriptActions = mutableListOf<JavascriptAction>()


    /**
     * List of javascript actions that has been called before the view was ready.
     */
    private var showFileChooserListener: ShowFileChooserListener? = null

    /**
     * Primary constructor that invokes the WebView constructor.
     */
    init {
        setup()
        setInternalListeners()
    }

    /**
     * WebView initialization. Allows cookies, javascripts, and register javascript bridge objects.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun setup() {
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        with(settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE

            allowFileAccessFromFileURLs = true
            allowFileAccess = true
            allowContentAccess = true
            allowUniversalAccessFromFileURLs = true
        }
        webViewClient = ChatWebViewClient(this::onLoadError)
        webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                return showFileChooserListener?.onShowFileChooser(filePathCallback, fileChooserParams) ?: super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }
        }
        registerJsInterfaces()
    }

    /**
     * Internal listeners to set internal states correctly.
     */
    private fun setInternalListeners() {
        addReadyEventListener(object : ReadyEventListener {
            override fun onReady() {
                isReady = true
                evaluatePendingJavascripts()
            }
        })
        addErrorListener(object : ErrorListener {
            override fun onNetworkError(networkException: NetworkException) {
                pendingJavascriptActions.clear()
            }

            override fun onInternalError(internalException: InternalException) {
                pendingJavascriptActions.clear()
            }
        })
    }

    /**
     * Method to clear cookies.
     *
     * @param resultCallback Callback that invokes after the javascript evaluated.
     */
    override fun clearAllCookies(resultCallback: ValueCallback<String?>?) {
        evaluateOrSaveJavascript("$provider.clearAllCookies();", object : ValueCallback<String?> {
            override fun onReceiveValue(value: String?) {
                reload()
                resultCallback?.onReceiveValue(value)
            }
        })
    }

    /**
     * Method to reload the view.
     */
    override fun reload() {
        configuration?.let {
            setConfiguration(it)
        }
    }

    /**
     * Method to hide the chat button.
     *
     * @param resultCallback Callback that invokes after the javascript evaluated.
     */
    override fun hideButton(resultCallback: ValueCallback<String?>?) {
        evaluateOrSaveJavascript("$provider.hideButton();", resultCallback)
    }

    /**
     * Method to show the chat button.
     *
     * @param resultCallback Callback that invokes after the javascript evaluated.
     */
    override fun showButton(resultCallback: ValueCallback<String?>?) {
        evaluateOrSaveJavascript("$provider.showButton();", resultCallback)
    }

    /**
     * Method to show the chat view.
     *
     * @param resultCallback Callback that invokes after the javascript evaluated.
     */
    override fun startLink(resultCallback: ValueCallback<String?>?) {
        evaluateOrSaveJavascript("$provider.startLink();", resultCallback)
    }

    /**
     * Setter to load a specific configuration to this View.
     *
     * @param configuration Configuration that specifies the chat.
     */
    override fun setConfiguration(configuration: Chat.Configuration) {
        isReady = false
        pendingJavascriptActions.clear()
        try {
            configurationValidator.validate(configuration)
            this.configuration = configuration
            contentLoader.load(context, configuration)
        } catch (ex: InternalException) {
            notifyInternalError(ex)
        }
    }

    /**
     * Callback method that invokes after the local HTML code has been loaded successfully.
     *
     * @param html The loaded HTML code to load into the WebView.
     */
    private fun onContentLoadSuccess(html: String) {
        this.loadDataWithBaseURL(configuration?.entryPageUrl, html, "text/html", "utf-8", null)
    }

    /**
     * Callback method that invokes if there was an error in HTML code loading.
     *
     * @param throwable The description of the failure.
     */
    private fun onContentLoadFail(throwable: Throwable) {
        notifyInternalError(InternalException(throwable))
    }

    /**
     * This method evaluates a javascript code and invokes the given resultcallback (if present). If the view is not ready the javascript and the callback will be added to the pending actions, and will be invoked after the view is ready.
     *
     * @param javascript Javascript to evaluate.
     * @param resultCallback Callback after javascript has been evaluated. Optional.
     */
    private fun evaluateOrSaveJavascript(javascript: String, resultCallback: ValueCallback<String?>?) {
        if (isReady) {
            this.evaluateJavascript(javascript, resultCallback)
        } else {
            pendingJavascriptActions.add(JavascriptAction(javascript, resultCallback))
        }
    }

    /**
     * Evaluates all pending javascripts and clears the list.
     */
    @Synchronized
    private fun evaluatePendingJavascripts() {
        pendingJavascriptActions.forEach {
            this.evaluateJavascript(it.javascript, it.resultCallback)
        }
        pendingJavascriptActions.clear()
    }

    /**
     * A WebViewClient callback that triggers when an error occurred.
     *
     * @param errorCode The error code corresponding to an ERROR_* value.
     * @param failingUrl The url that failed to load.
     * @param descriptor A String describing the error.
     */
    private fun onLoadError(errorCode: Int, failingUrl: String, descriptor: String) {
        val error = NetworkException(isReady, errorCode, failingUrl, descriptor)
        notifyNetworkError(error)
    }

    /**
     * This method registers all the javascript bridge objects.
     */
    private fun registerJsInterfaces() {
        eventHandler.registerJsInterfaces { bridge, name ->
            this.addJavascriptInterface(bridge, name)
        }
    }

    /**
     * Delegated method that adds a CloseEventListener to the eventHandler.
     *
     * @param listener Instance of a CloseEventListener.
     */
    override fun addCloseListener(listener: CloseEventListener) =
        eventHandler.addCloseListener(listener)

    /**
     * Delegated method that removes a CloseEventListener from the eventHandler.
     *
     * @param listener Instance of a CloseEventListener.
     */
    override fun removeCloseListener(listener: CloseEventListener) =
        eventHandler.removeCloseListener(listener)

    /**
     * Delegated method that adds a ChatMessageReceivedEventListener to the eventHandler.
     *
     * @param listener Instance of a ChatMessageReceivedEventListener.
     */
    override fun addChatMessageReceivedListener(listener: ChatMessageReceivedEventListener) =
        eventHandler.addChatMessageReceivedListener(listener)

    /**
     * Delegated method that removes a ChatMessageReceivedEventListener from the eventHandler.
     *
     * @param listener Instance of a ChatMessageReceivedEventListener.
     */
    override fun removeChatMessageReceivedListener(listener: ChatMessageReceivedEventListener) =
        eventHandler.removeChatMessageReceivedListener(listener)

    /**
     * Delegated method that adds a ButtonEventListener to the eventHandler.
     *
     * @param listener Instance of a ButtonEventListener.
     */
    override fun addButtonEventListener(listener: ButtonEventListener) =
        eventHandler.addButtonEventListener(listener)

    /**
     * Delegated method that removes a ButtonEventListener from the eventHandler.
     *
     * @param listener Instance of a ButtonEventListener.
     */
    override fun removeButtonEventListener(listener: ButtonEventListener) =
        eventHandler.removeButtonEventListener(listener)

    /**
     * Delegated method that adds a ChatMessageSentListener to the eventHandler.
     *
     * @param listener Instance of a ChatMessageSentListener.
     */
    override fun addChatMessageSentListener(listener: ChatMessageSentEventListener) =
        eventHandler.addChatMessageSentListener(listener)

    /**
     * Delegated method that removes a ChatMessageSentListener from the eventHandler.
     *
     * @param listener Instance of a ChatMessageSentListener.
     */
    override fun removeChatMessageSentListener(listener: ChatMessageSentEventListener) =
        eventHandler.removeChatMessageSentListener(listener)

    /**
     * Delegated method that adds a MessageSubmitListener to the eventHandler.
     *
     * @param listener Instance of a MessageSubmitListener.
     */
    override fun addMessageSubmitListener(listener: MessageSubmitEventListener) =
        eventHandler.addMessageSubmitListener(listener)

    /**
     * Delegated method that removes a MessageSubmitListener from the eventHandler.
     *
     * @param listener Instance of a MessageSubmitListener.
     */
    override fun removeMessageSubmitListener(listener: MessageSubmitEventListener) =
        eventHandler.removeMessageSubmitListener(listener)

    /**
     * Delegated method that adds a MinimizeListener to the eventHandler.
     *
     * @param listener Instance of a MinimizeListener.
     */
    override fun addMinimizeListener(listener: MinimizeEventListener) =
        eventHandler.addMinimizeListener(listener)

    /**
     * Delegated method that removes a MinimizeListener from the eventHandler.
     *
     * @param listener Instance of a MinimizeListener.
     */
    override fun removeMinimizeListener(listener: MinimizeEventListener) =
        eventHandler.removeMinimizeListener(listener)

    /**
     * Delegated method that adds an OpenListener to the eventHandler.
     *
     * @param listener Instance of an OpenListener.
     */
    override fun addOpenListener(listener: OpenEventListener) =
        eventHandler.addOpenListener(listener)

    /**
     * Delegated method that removes an OpenListener from the eventHandler.
     *
     * @param listener Instance of an OpenListener.
     */
    override fun removeOpenListener(listener: OpenEventListener) =
        eventHandler.removeOpenListener(listener)

    /**
     * Delegated method that adds an OpenProactiveListener to the eventHandler.
     *
     * @param listener Instance of an OpenProactiveListener.
     */
    override fun addOpenProactiveListener(listener: OpenProactiveEventListener) =
        eventHandler.addOpenProactiveListener(listener)

    /**
     * Delegated method that removes an OpenProactiveListener from the eventHandler.
     *
     * @param listener Instance of an OpenProactiveListener.
     */
    override fun removeOpenProactiveListener(listener: OpenProactiveEventListener) =
        eventHandler.removeOpenProactiveListener(listener)

    /**
     * Delegated method that adds a StartCallMeListener to the eventHandler.
     *
     * @param listener Instance of a StartCallMeListener.
     */
    override fun addStartCallMeListener(listener: StartCallmeEventListener) =
        eventHandler.addStartCallMeListener(listener)

    /**
     * Delegated method that removes a StartCallMeListener from the eventHandler.
     *
     * @param listener Instance of a StartCallMeListener.
     */
    override fun removeStartCallMeListener(listener: StartCallmeEventListener) =
        eventHandler.removeStartCallMeListener(listener)

    /**
     * Delegated method that adds a StartChatListener to the eventHandler.
     *
     * @param listener Instance of a StartChatListener.
     */
    override fun addStartChatListener(listener: StartChatEventListener) =
        eventHandler.addStartChatListener(listener)

    /**
     * Delegated method that removes a StartChatListener from the eventHandler.
     *
     * @param listener Instance of a StartChatListener.
     */
    override fun removeStartChatListener(listener: StartChatEventListener) =
        eventHandler.removeStartChatListener(listener)

    /**
     * Delegated method that adds a SwitchWidgetListener to the eventHandler.
     *
     * @param listener Instance of a SwitchWidgetListener.
     */
    override fun addSwitchWidgetListener(listener: SwitchWidgetEventListener) =
        eventHandler.addSwitchWidgetListener(listener)

    /**
     * Delegated method that removes a SwitchWidgetListener from the eventHandler.
     *
     * @param listener Instance of a SwitchWidgetListener.
     */
    override fun removeSwitchWidgetListener(listener: SwitchWidgetEventListener) =
        eventHandler.removeSwitchWidgetListener(listener)

    /**
     * Delegated method that adds a ReadyEventListener to the eventHandler.
     *
     * @param listener Instance of a ReadyEventListener.
     */
    override fun addReadyEventListener(listener: ReadyEventListener) =
        eventHandler.addReadyEventListener(listener)

    /**
     * Delegated method that removes a ReadyEventListener from the eventHandler.
     *
     * @param listener Instance of a ReadyEventListener.
     */
    override fun removeReadyEventListener(listener: ReadyEventListener) =
        eventHandler.removeReadyEventListener(listener)

    /**
     * Delegated method that adds an ErrorListener to the errorHandler.
     *
     * @param listener Instance of a ErrorListener.
     */
    override fun addErrorListener(listener: ErrorListener) =
        errorHandler.addErrorListener(listener)

    /**
     * Delegated method that removes an ErrorListener from the errorHandler.
     *
     * @param listener Instance of a ErrorListener.
     */
    override fun removeErrorListener(listener: ErrorListener) =
        errorHandler.removeErrorListener(listener)

    /**
     * Check the current widget availability
     */
    @WorkerThread
    override fun checkWidgetAvailability(): WidgetAvailability {
        configuration?.let {
            return checkWidgetAvailability(it.widgetId)
        } ?: throw InternalException(NullPointerException("Missing configuration")).also {
            notifyInternalError(it)
        }
    }

    /**
     * Check widget availability
     * @param widgetId the id of the widget
     */
    @WorkerThread
    override fun checkWidgetAvailability(widgetId: String): WidgetAvailability {
        configuration?.let {
            try {
                return widgetAvailabilityProvider.checkWidgetAvailability(it.baseInstanceUrl, widgetId)
            } catch (exception: WidgetAvailabilityException) {
                throw InternalException(exception).also { internalException ->
                    notifyInternalError(internalException)
                }
            } catch (exception: Exception) {
                throw NetworkException(isReady, 0, "", exception.message.orEmpty()).also { networkException ->
                    notifyNetworkError(networkException)
                }
            }
        } ?: throw InternalException(NullPointerException("Missing configuration")).also {
            notifyInternalError(it)
        }
    }

    /**
     * Notify internal error on main thread
     * @param exception exception
     */
    private fun notifyInternalError(exception: InternalException) = post { errorHandler.notifyInternalError(exception) }

    /**
     * Notify network error on main thread
     * @param exception exception
     */
    private fun notifyNetworkError(exception: NetworkException) = post { errorHandler.notifyNetworkError(exception) }

    /**
     * Set ShowFileChooserListener
     * @param showFileChooserListener ShowFileChooserListener
     */
    override fun setShowFileChooserListener(showFileChooserListener: ShowFileChooserListener) {
        this.showFileChooserListener = showFileChooserListener
    }
}