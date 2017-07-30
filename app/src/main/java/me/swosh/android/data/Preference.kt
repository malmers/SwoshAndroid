package me.swosh.android.data

import android.content.SharedPreferences
import android.util.ArraySet

class Preference(val preference: SharedPreferences) {

    val SHARED_PREFS_SWOSHES: String = "SHARED_PREFS_SWOSHES"
    val SHARED_PREFS_PHONE: String = "SHARED_PREFS_PHONE"

    var swoshes: Set<String>
        get() = getValueSet(SHARED_PREFS_SWOSHES)
        set(value) = setValueSet(SHARED_PREFS_SWOSHES, value)

    var phone: String
        get() = getValue(SHARED_PREFS_PHONE)
        set(value) = setValue(SHARED_PREFS_PHONE, value)

    fun setValue(key: String, value: String) {
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setValueSet(key: String, value: Set<String>) {
        val editor = preference.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    fun getValue(key: String): String {
        return preference.getString(key, "")
    }

    fun getValueSet(key: String): Set<String>{
        return preference.getStringSet(key, ArraySet<String>())
    }
}
