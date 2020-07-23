package com.snapengage.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CustomVariablesAdapter : RecyclerView.Adapter<CustomVariablesAdapter.CustomVariableViewHolder>() {

    val itemList = LinkedList<CustomVariable>()

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVariableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.listitem_custom_variable, parent, false)
        return CustomVariableViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomVariableViewHolder, position: Int) {
        val customVariable = itemList[position]
        holder.bindCustomVariable(customVariable)
    }

    inner class CustomVariableViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val customVariableText = view.findViewById<AppCompatTextView>(R.id.customVariableText)

        init {
            view.findViewById<AppCompatButton>(R.id.removeCustomVariableView)?.setOnClickListener {
                val customVariable = itemList[adapterPosition]
                itemList.remove(customVariable)
                notifyDataSetChanged()
            }
        }

        fun bindCustomVariable(customVariable: CustomVariable) {
            val variableText = "name: ${customVariable.key}\nvalue: ${customVariable.data}"
            customVariableText.text = variableText
        }

    }

    data class CustomVariable(
        val key : String,
        val data : String,
        val raw : Any
    )

}