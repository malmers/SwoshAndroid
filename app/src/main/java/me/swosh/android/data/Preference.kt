package me.swosh.android.data

import android.content.SharedPreferences

class Preference(val preference: SharedPreferences) {

    var phone: String
        get() = getValue("phone")
        set(value) = setValue("phone", value)

    fun setValue(key: String, value: String) {
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String): String {
        return preference.getString(key, "")
    }
}
