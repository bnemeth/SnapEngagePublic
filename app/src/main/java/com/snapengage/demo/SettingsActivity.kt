package com.snapengage.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chat.sdk.Chat
import com.chat.sdk.CustomVariables
import com.chat.sdk.utils.ConfigurationValidatorImpl
import kotlinx.android.synthetic.main.activity_settings.*
import java.lang.Exception
import java.util.*
import kotlin.random.Random

class SettingsActivity : AppCompatActivity() {

    private lateinit var customVariablesAdapter: CustomVariablesAdapter

    private var bool = 0
    private var integer = 0
    private var double = 0
    private var string = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupCustomVariablesAdapter(savedInstanceState)
        setOnClickListeners()
    }

    private fun setupCustomVariablesAdapter(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            this.customVariablesAdapter = CustomVariablesAdapter()
            customVariablesRecyclerView.adapter = customVariablesAdapter
        }
    }

    private fun setOnClickListeners() {
        saveButton.setOnClickListener {
            try {
                val config = createConfiguration()
                ConfigurationValidatorImpl().validate(config)
                startActivity(ChatActivity.startIntent(this, config))
            } catch (exception: Exception) {
                showToast(exception.localizedMessage ?: "Invalid Configuration")
                return@setOnClickListener
            }
        }
        addBooleanVariableView.setOnClickListener {
            val rawData = Random.nextBoolean()
            addToCustomVariableList("boolean_${++bool}", rawData.toString(), rawData)
        }
        addStringVariableView.setOnClickListener {
            val rawData = UUID.randomUUID().toString().substring(0, 10)
            addToCustomVariableList("string_${++string}", rawData, rawData)
        }
        addDoubleVariableView.setOnClickListener {
            val rawData = Random.nextDouble()
            addToCustomVariableList("double_${++double}", rawData.toString(), rawData)
        }
        addIntVariableView.setOnClickListener {
            val rawData = Random.nextInt()
            addToCustomVariableList("int_${++integer}", rawData.toString(), rawData)
        }
    }

    private fun createConfiguration(): Chat.Configuration {
        val customVariables = CustomVariables()
        val customVariableItems = customVariablesAdapter.itemList
        if (!customVariableItems.isEmpty()) {
            customVariableItems.forEach {
                customVariables.put(it.key, it.raw)
            }
        }

        return Chat.Configuration(
            widgetIdView.text.toString(),
            baseJavascriptUrlView.text.toString(),
            providerView.text.toString(),
            entryPageUrlView.text.toString(),
            baseInstanceUrlView.text.toString(),
            customVariables
        )
    }

    private fun addToCustomVariableList(name: String, value: String, raw: Any) {
        with(customVariablesAdapter) {
            val newVariable = CustomVariablesAdapter.CustomVariable(name, value, raw)
            itemList.add(newVariable)
            customVariablesRecyclerView.post {
                notifyItemInserted(itemCount - 1)
            }
        }
    }

    private fun showToast(msg: Int) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}