package com.hussein.suggesttest.ui

import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.google.android.gms.maps.MapsInitializer
import com.hussein.suggesttest.R
import com.hussein.suggesttest.util.apphelper.AppConfigHelper
import com.hussein.suggesttest.util.languagehelper.LanguageHelper
import com.hussein.suggesttest.adapter.ImageAdapter
import kotlinx.android.synthetic.main.activity_gallery.*


class GalleryActivity : AppCompatActivity(), View.OnTouchListener {
    private var adapter: ImageAdapter? = null
    private var imageList:ArrayList<String> = ArrayList()
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (v?.id) {
            R.id.iv_back_arrow -> {
                AppConfigHelper.finishActivity(this)
            }
        }
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguageHelper().initLanguage(this, true)//set the selected language settings
        setContentView(R.layout.activity_gallery)
        AppConfigHelper.transparentHeader(this)
        initViews()
    }
    private fun initViews()
    {
        try {
            val i = intent
            imageList = i.getStringArrayListExtra(AppConfigHelper.GALLERY_SHOW_INTENT)

            adapter = ImageAdapter(this,imageList)
            adapter!!.notifyDataSetChanged()
            viewPagerImage.adapter = adapter
            mIndicator.setViewPager(viewPagerImage)
            if (imageList.size <= 1) {
                mIndicator.visibility = View.GONE
            }
            iv_back_arrow.setOnTouchListener(this)
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }
}
