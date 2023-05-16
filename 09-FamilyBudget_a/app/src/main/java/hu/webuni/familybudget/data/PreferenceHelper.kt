package hu.webuni.familybudget.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(private val context: Context) {

    private val intro = "intro"
    private val name = "name"
    private val appPrefs: SharedPreferences = context.getSharedPreferences(
        "shared",
        Context.MODE_PRIVATE
    )

    fun putIsLogin(loginOrOut: Boolean) {
        val edit = appPrefs.edit()
        edit.putBoolean(intro, loginOrOut)
        edit.apply()
    }

    fun getIsLogin(): Boolean {
        return appPrefs.getBoolean(intro, false)
    }

    fun putName(loginOrOut: String) {
        val edit = appPrefs.edit()
        edit.putString(name, loginOrOut)
        edit.apply()
    }

    fun getName(): String? {
        return appPrefs.getString(name, "")
    }
}