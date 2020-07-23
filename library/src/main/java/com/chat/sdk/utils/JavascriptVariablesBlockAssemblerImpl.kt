package com.chat.sdk.utils

import com.chat.sdk.CustomVariables

/**
 * Utility class to assemble the custom variables javascript block.
 */
internal class JavascriptVariablesBlockAssemblerImpl : JavascriptVariablesBlockAssembler {

    /**
     * Assembles the custom variables javascript block
     *
     * @param customVariables list of custom variables that will be inserted as javascript variable
     * @return javascript block string
     */
    override fun assemble(customVariables: List<CustomVariables.JsVariable>?): String {
        return customVariables.takeIf { !it.isNullOrEmpty() }?.let {
            val builder = StringBuilder()

            builder.append(openingTag())

            it.forEach { variable ->
                builder.append(variableLine(variable))
            }

            builder.append(closingTag())

            builder.toString()
        }.orEmpty()
    }

    /**
     * Returns the javascript block opening tag.
     * Example: <script type="text/javascript">
     */
    private fun openingTag() = "<script type=\"text/javascript\">\n"

    /**
     * Returns a variable line with line break.
     * Example:   var variableName = variableValue;
     *
     * @param variable the variable that will be injected.
     */
    private fun variableLine(variable : CustomVariables.JsVariable) =
        "  var ${variable.name} = ${variable.value};\n"

    /**
     * Returns the javascript block closing tag.
     * Example: </script>
     */
    private fun closingTag() = "</script>"

}