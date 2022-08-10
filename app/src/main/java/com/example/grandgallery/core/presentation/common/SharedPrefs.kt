package com.example.grandgallery.core.presentation.common

import android.content.Context
import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
class SharedPrefs (private var context: Context) {
    companion object {
        private const val Name = "Name"
        private const val Address = "Address"
        private const val PREF = "MyAppPrefName"
        private const val PREF_TOKEN = "user_token"
        private const val USER_ID = "user_id"
        private const val USER_ROLE = "user_role"
        private const val IS_SELECTED = "IS_SELECTED"
        private const val USER_NAME = "USER_NAME"
        private const val Locale = "Locale"
        private const val Location = "Location"
        private const val TYPE = "TYPE"
        private const val NotFound = "NotFound"

    }


    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun saveLocale(lang: String){
        put(Locale, lang)
    }
    fun getLocale() : String {
        return getLocaleValue(Locale, String::class.java)
    }

    fun saveToken(token: String){
        put(PREF_TOKEN, token)
    }

    fun getToken() : String {
        return get(PREF_TOKEN, String::class.java)
    }

    fun saveFound(found: String){
        put(NotFound, found)
    }

    fun getFound() : String {
        return get(NotFound, String::class.java)
    }

    fun saveType(token: String){
        put(TYPE, token)
    }

    fun getType() : String {
        return get(TYPE, String::class.java)
    }

    fun saveName(name: String){
        put(USER_NAME, name)
    }

    fun getName() : String {
        return get(USER_NAME, String::class.java)
    }

    fun saveLocation(name: String){
        put(Location, name)
    }

    fun getLocation() : String {
        return get(Location, String::class.java)
    }

    fun saveSelect(selected: String){
        put(IS_SELECTED, selected)
    }

    fun getSelect() : String {
        return get(IS_SELECTED, String::class.java)
    }

    fun saveID(id: String){
        put(USER_ID, id)
    }

    fun getID() : String {
        return get(USER_ID, String::class.java)
    }

    fun saveUserRole(userRole: String){
        put(USER_ROLE, userRole)
    }

    fun getUserRole() : String {
        return get(USER_ROLE, String::class.java)
    }



    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> getLocaleValue(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "en")
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    fun clear() {
        sharedPref.edit().run {
            remove(PREF_TOKEN)
        }.apply()
    }
}