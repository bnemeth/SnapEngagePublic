package com.chat.sdk.utils

import com.chat.sdk.CustomVariables
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class JavascriptVariablesBlockAssemblerTest {

    private lateinit var SUT : JavascriptVariablesBlockAssemblerImpl

    @Before
    fun setup() {
        this.SUT = JavascriptVariablesBlockAssemblerImpl()
    }

    @Test
    fun nullCustomVariablesBlock_shouldBeEmptyString() {
        assertJavascriptBlockEquals("", null)
    }

    @Test
    fun emptyCustomVariablesBlock_shouldBeEmptyString() {
        assertJavascriptBlockEquals("", hashMapOf())
    }

    @Test
    fun nonEmptyCustomVariablesBlock_shouldBeQuotedIfNeeded() {
        assertJavascriptBlockEquals(
            "<script type=\"text/javascript\">\n" +
                    "  var Int = 3;\n" +
                    "  var Boolean = true;\n" +
                    "  var String = \"Alma\";\n" +
                    "  var Double = 3.4;\n" +
                    "  var Float = 3.4;\n" +
                    "</script>", linkedMapOf(
                "Int" to 3,
                "Boolean" to true,
                "String" to "Alma",
                "Double" to 3.4,
                "Float" to 3.4f
            )
        )
    }

    private fun assertJavascriptBlockEquals(
        expected: String?,
        customVariables: HashMap<String, Any>?
    ) {
        val variables : CustomVariables? = customVariables?.let {
            CustomVariables(it)
        }
        assertEquals(expected, SUT.assemble(variables?.javascriptVariables))
    }
}