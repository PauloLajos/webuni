package hu.webuni.familybudget

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {

    private val intro = "intro"
    private val name = "name"
    private val app_prefs: SharedPreferences

    init {
        app_prefs = context.getSharedPreferences(
            "shared",
            Context.MODE_PRIVATE
        )
    }

    fun putIsLogin(loginOrOut: Boolean) {
        val edit = app_prefs.edit()
        edit.putBoolean(intro, loginOrOut)
        edit.commit()
    }

    fun getIsLogin(): Boolean {
        return app_prefs.getBoolean(intro, false)
    }

    fun putName(loginOrOut: String) {
        val edit = app_prefs.edit()
        edit.putString(name, loginOrOut)
        edit.commit()
    }

    fun getName(): String? {
        return app_prefs.getString(name, "")
    }
}