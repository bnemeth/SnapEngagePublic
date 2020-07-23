package com.chat.sdk

import android.os.Parcel
import android.os.Parcelable

/**
 * Helper class to provide properly formatted custom javascript variables.
 */
class CustomVariables : Parcelable {

    /**
     * Collection that contain the javascript variables.
     */
    internal var javascriptVariables = ArrayList<JsVariable>()

    /**
     * Primary empty constructor.
     */
    constructor()

    /**
     * Secondary constructor that initialize this CustomVariables instance with a map content.
     * @param map A map, which keys are the js variable names, and its values are the js variable values.
     */
    constructor(map : Map<String, Any>) : this() {
        putAll(map)
    }

    /**
     * Secondary constructor for @Parcelable interface.
     */
    constructor(parcel: Parcel) : this() {
        parcel.readTypedList(javascriptVariables, JsVariable.CREATOR)
    }

    /**
     * Put a javascript variable into this collection.
     *
     * @param name Name of the js variable.
     * @param value Value of the js variable.
     * @return Instance of this CustomVariables object.
     */
    fun put(name: String, value: Any) : CustomVariables {
        val quotationMark = value.quotationMark()
        val jsValue = "$quotationMark$value$quotationMark"
        javascriptVariables.add(JsVariable(name, jsValue))
        return this
    }

    /**
     * Put multiple javascript variables into this collection.
     *
     * @param map A map, which keys are the js variable names, and its values are the js variable values.
     * @return Instance of this CustomVariables object.
     */
    fun putAll(map : Map<String, Any>) : CustomVariables {
        map.forEach {
            put(it.key, it.value)
        }
        return this
    }

    /**
     *  @return A quotation mark if needed based on the given class.
     */
    private fun Any.quotationMark() = when (this) {
        is Int,
        is Float,
        is Double,
        is Boolean -> ""
        else -> "\""
    }

    /**
     * @return This CustomVariables instance in a well formatted string.
     */
    override fun toString(): String {
        return if(javascriptVariables.isEmpty()) "not set" else {
            var variables = String()
            javascriptVariables.forEach {
                variables += it.toString() + "\n"
            }
            return variables
        }
    }

    /**
     *
     * @param parcel The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE. Value is either 0 or a combination of PARCELABLE_WRITE_RETURN_VALUE, and android.os.Parcelable.PARCELABLE_ELIDE_DUPLICATES.
     */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(javascriptVariables)
    }

    /**
     *
     * @return A bitmask indicating the set of special object types marshaled by this Parcelable object instance. Value is either 0 or CONTENTS_FILE_DESCRIPTOR.
     */
    override fun describeContents(): Int {
        return 0
    }

    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates instances of your Parcelable class from a Parcel.
     */
    companion object CREATOR : Parcelable.Creator<CustomVariables> {

        /**
         * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable#writeToParcel.
         *
         * @param parcel The Parcel to read the object's data from.
         * @return A CustomVariables instance.
         */
        override fun createFromParcel(parcel: Parcel): CustomVariables {
            return CustomVariables(parcel)
        }

        /**
         * Create a new array of the Parcelable class.
         *
         * @param size Returns an array of the Parcelable class, with every entry initialized to null.
         * @return Array of CustomVariable instances.
         */
        override fun newArray(size: Int): Array<CustomVariables?> {
            return arrayOfNulls(size)
        }
    }

    /**
     * Data class which describe a javascript variable.
     *
     * @property name Name of the javascript variable.
     * @property value Value of the javascript variable.
     */
    internal data class JsVariable(val name : String, val value : String) : Parcelable {

        /**
         *
         * Secondary constructor for @Parcelable interface.
         */
        constructor(parcel: Parcel) : this(
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty()
        )

        /**
         *
         * @return This JsVariable instance in a well formatted string.
         */
        override fun toString(): String {
            return "{name=$name, value=$value}"
        }


        /**
         *
         * @param parcel The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE. Value is either 0 or a combination of PARCELABLE_WRITE_RETURN_VALUE, and android.os.Parcelable.PARCELABLE_ELIDE_DUPLICATES.
         */
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(value)
        }

        /**
         *
         * @return A bitmask indicating the set of special object types marshaled by this Parcelable object instance. Value is either 0 or CONTENTS_FILE_DESCRIPTOR.
         */
        override fun describeContents(): Int {
            return 0
        }

        /**
         * Interface that must be implemented and provided as a public CREATOR field that generates instances of your Parcelable class from a Parcel.
         */
        companion object CREATOR : Parcelable.Creator<JsVariable> {

            /**
             * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable#writeToParcel.
             *
             * @param parcel The Parcel to read the object's data from.
             * @return A JsVariable instance.
             */
            override fun createFromParcel(parcel: Parcel): JsVariable {
                return JsVariable(parcel)
            }

            /**
             * Create a new array of the Parcelable class.
             *
             * @param size Returns an array of the Parcelable class, with every entry initialized to null.
             * @return Array of JsVariable instance.
             */
            override fun newArray(size: Int): Array<JsVariable?> {
                return arrayOfNulls(size)
            }
        }

    }

}