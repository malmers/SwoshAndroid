package me.swosh.android.data

import android.content.SharedPreferences

class Preference(val preference: SharedPreferences) {

    val SHARED_PREFS_PHONE = "SHARED_PREFS_PHONE"

    var phone
        get() = getValue(SHARED_PREFS_PHONE)
        set(value) = setValue(SHARED_PREFS_PHONE, value)

    fun setValue(key: String, value: String) {
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String): String {
        return preference.getString(key, "")
    }
}
