package com.hussein.suggesttest.ui

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.hussein.suggesttest.R
import com.hussein.suggesttest.util.apphelper.AppConfigHelper
import com.hussein.suggesttest.util.apphelper.RevealAnimation
import com.hussein.suggesttest.util.languagehelper.LanguageHelper
import com.hussein.suggesttest.util.sharedpreference.SharedPrefHelper
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguageHelper().initLanguage(this, true)//set the selected language settings
        setContentView(R.layout.activity_splash)
        AppConfigHelper.transparentHeader(this)
        initViews()
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun initViews()
    {
        try
        {
            btnSuggest.setOnClickListener{
                startWithSelectedLanguage()
            }
        }
        catch (e:Exception)
        {
         e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun startWithSelectedLanguage() {
        try {
                SharedPrefHelper.setSharedString(this, SharedPrefHelper.SHARED_PREFERENCE_LANGUAGE_KEY, AppConfigHelper.ARABIC_LANGUAGE)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                    window.decorView.textDirection = View.LAYOUT_DIRECTION_RTL
                }
            RevealAnimation.presentActivity(this,btnSuggest,HomeActivity::class.java,false)

        }
        catch (objException: Exception) {
            objException.printStackTrace()
        }
      }
}
