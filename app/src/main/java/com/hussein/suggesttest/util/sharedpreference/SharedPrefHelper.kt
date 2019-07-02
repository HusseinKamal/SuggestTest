package com.hussein.suggesttest.util.sharedpreference

import android.content.Context
import com.google.gson.Gson

class SharedPrefHelper {
    companion object {

        val SHARED_PREFERENCE_LANGUAGE_KEY = "language"

        fun getSharedString(context: Context, key: String): String {
            val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            return sharedPreferences.getString(key, "")

        }

        fun setSharedString(context: Context, key: String, value: String) {
            val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            sharedPreferences.edit().putString(key, value).apply()
        }
    }

}