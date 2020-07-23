package com.chat.sdk.utils

import com.chat.sdk.CustomVariables

/**
 * Abstraction to assemble the custom variables javascript block.
 */
internal interface JavascriptVariablesBlockAssembler {

    /**
     * Assembles the custom variables javascript block
     *
     * @param customVariables list of custom variables that will be inserted as javascript variable
     * @return javascript block string
     */
    fun assemble(customVariables: List<CustomVariables.JsVariable>?): String

}