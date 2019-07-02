package com.hussein.suggesttest.util.languagehelper

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.view.View
import com.hussein.suggesttest.util.sharedpreference.SharedPrefHelper
import java.util.Locale

@Suppress("DEPRECATION")
class LanguageHelper {

    fun initLanguage(context: Activity, enforceRtl: Boolean) {
        val currentLanguage = context.baseContext.resources.configuration.locale.language
        val language = SharedPrefHelper.getSharedString(context, SharedPrefHelper.SHARED_PREFERENCE_LANGUAGE_KEY)
        Log.v("lang", language)
        if (!currentLanguage.equals(language, ignoreCase = true)) {
            changeLanguage(context, enforceRtl, language)
        }
    }

    fun changeLanguage(context: Activity, enforceRtl: Boolean, newLanguage: String) {
        SharedPrefHelper.setSharedString(context, SharedPrefHelper.SHARED_PREFERENCE_LANGUAGE_KEY, newLanguage)
        val locale = Locale(newLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.baseContext.resources.updateConfiguration(config,
            context.baseContext.resources.displayMetrics)
        if (enforceRtl) {
            if (newLanguage.equals("ar", ignoreCase = true) || newLanguage.equals("ur", ignoreCase = true))
                this.forceRTLIfSupported(context, true)

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun forceRTLIfSupported(context: Activity, replace: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && replace)
            context.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && !replace)
            context.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
    }

   /* companion object {

        fun getCurrentLanguage(context: Context): String {
            val value = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE).getString(SharedPrefHelper.SHARED_PREFERENCE_LANGUAGE_KEY, "en")
            Log.v("language", value)
            return value
        }
    }*/
}
