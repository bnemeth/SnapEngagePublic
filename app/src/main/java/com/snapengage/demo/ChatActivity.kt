package com.snapengage.demo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updateLayoutParams
import com.chat.sdk.Chat
import com.chat.sdk.ShowFileChooserListener
import com.chat.sdk.clearAllCookies
import com.chat.sdk.error_handling.ErrorListener
import com.chat.sdk.error_handling.InternalException
import com.chat.sdk.error_handling.NetworkException
import com.chat.sdk.events.close.CloseEventListener
import com.chat.sdk.events.minimize.MinimizeEventListener
import com.chat.sdk.events.ready.ReadyEventListener
import com.chat.sdk.startLink
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


class ChatActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val timeFromatter = DateFormat.getTimeInstance()

    private val configuration: Chat.Configuration?
        get() = intent.getParcelableExtra(EXTRA_CONFIGURATION)

    private var showRetryButton = false
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setupChat()
        setupFab()
        restoreInstanceState(savedInstanceState)
        applyOrientationChange()
        checkWidgetAvailability()
    }

    private fun checkWidgetAvailability() {
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    chatView.checkWidgetAvailability()
                }
                log(result.toString())
                if (result.data.online) {
                    fab.show()
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState?.getInt(SIS_CHAT_VISIBILITY) == View.VISIBLE) {
            openChat()
        }
        showRetryButton = savedInstanceState?.getBoolean(SIS_SHOW_RETRY_VISIBILITY) == true
    }

    private fun applyOrientationChange(configuration: Configuration = resources.configuration) {
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val contentOrientation = if (isLandscape) {
            LinearLayoutCompat.HORIZONTAL
        } else {
            LinearLayoutCompat.VERTICAL
        }
        contentContainer.orientation = contentOrientation
        updateLPBasedOnOrientation(logScroller, isLandscape)
        updateLPBasedOnOrientation(chatView, isLandscape)
    }

    private fun updateLPBasedOnOrientation(view: View, isLandscape: Boolean) {
        view.updateLayoutParams<LinearLayoutCompat.LayoutParams> {
            if (isLandscape) {
                width = 0
                height = LinearLayoutCompat.LayoutParams.MATCH_PARENT
            } else {
                width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
                height = 0
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        applyOrientationChange(newConfig)
    }

    private fun setupFab() {
        fab.setOnClickListener {
            openChat()
        }
    }

    private fun openChat() {
        chatView.startLink {
            chatView.visibility = View.VISIBLE
            fab.hide()
        }
    }

    private fun setupChat() {
        configuration?.let {

            chatView.setShowFileChooserListener(object : ShowFileChooserListener {
                override fun onShowFileChooser(filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: WebChromeClient.FileChooserParams?): Boolean {

                    this@ChatActivity.filePathCallback = filePathCallback

                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "*/*"
                    intent.addCategory(Intent.CATEGORY_OPENABLE)

                    startActivityForResult(
                        Intent.createChooser(intent, "Select a File to Upload"),
                        REQUEST_CODE_FILE_UPLOAD
                    )

                    return true
                }
            })

            chatView.addTestListeners { text ->
                log(text)
            }
            chatView.addCloseListener(object : CloseEventListener {
                override fun onClose(type: String?, status: String?) {
                    closeChat()
                }
            })
            chatView.addErrorListener(object : ErrorListener {
                override fun onNetworkError(networkException: NetworkException) {
                    showError(networkException.message.orEmpty(), networkException.clientWasReady)
                }

                override fun onInternalError(internalException: InternalException) {
                    showError("SDK_ERROR", false)
                }
            })
            chatView.addMinimizeListener(object : MinimizeEventListener {
                override fun onMinimize(isMinimized: Boolean?, chatType: String?, boxType: String?) {
                    if (isMinimized == true) {
                        closeChat()
                    }
                }
            })
            chatView.addReadyEventListener(object : ReadyEventListener {
                override fun onReady() {
                    fab.isEnabled = true
                }
            })
            chatView.setConfiguration(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_FILE_UPLOAD) {
            filePathCallback?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun log(text: String) {
        val time = timeFromatter.format(Date())
        val logText = "${logView.text}\n$time - $text"
        logView.text = logText.trim()

        logScroller.post {
            logScroller.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun showError(msg: String, clientWasReady: Boolean) {
        if (!clientWasReady) {
            Toast.makeText(this@ChatActivity, msg, Toast.LENGTH_SHORT).show()
            showRetryButton = true
        } else {
            Log.e("CHAT_ERROR", "Hopefully the JS will handle the situation: $msg")
        }
    }

    private fun closeChat() {
        fab.show()
        chatView.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.mi_retry)?.isVisible = showRetryButton
        menu?.findItem(R.id.mi_log_visibility)?.let {
            val titleRes = if (logScroller.visibility == View.VISIBLE) {
                R.string.hide_logs
            } else {
                R.string.show_logs
            }
            it.title = getString(titleRes)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_clear_log -> {
                logView.text = ""
                true
            }
            R.id.mi_clear_cookies -> {
                chatView.clearAllCookies {
                    if (chatView.visibility == View.VISIBLE) {
                        chatView.startLink()
                    }
                }

                true
            }
            R.id.mi_configuration -> {
                AlertDialog.Builder(this)
                    .setTitle(R.string.chat_configuration)
                    .setMessage(configuration?.debug())
                    .create()
                    .show()
                true
            }
            R.id.mi_retry -> {
                showRetryButton = false
                chatView.reload()
                true
            }
            R.id.mi_log_visibility -> {
                val nextVisibility = if (logScroller.visibility == View.VISIBLE) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
                logScroller.visibility = nextVisibility
                invalidateOptionsMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SIS_CHAT_VISIBILITY, chatView.visibility)
        outState.putBoolean(SIS_SHOW_RETRY_VISIBILITY, showRetryButton)
        super.onSaveInstanceState(outState)
    }

    companion object {

        private const val SIS_CHAT_VISIBILITY = "chat.visibility"
        private const val SIS_SHOW_RETRY_VISIBILITY = "show.retry.visibility"

        private const val EXTRA_CONFIGURATION = "configuration"

        private const val REQUEST_CODE_FILE_UPLOAD = 123

        fun startIntent(context: Context, config: Chat.Configuration) =
            Intent(context, ChatActivity::class.java)
                .putExtra(EXTRA_CONFIGURATION, config)

    }

}
