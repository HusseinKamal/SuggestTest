package com.hussein.suggesttest.dialog

import android.support.v4.app.DialogFragment
import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import com.hussein.suggesttest.R
import com.hussein.suggesttest.model.Restaurant
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.Gravity
import com.hussein.suggesttest.util.languagehelper.LanguageHelper
import com.hussein.suggesttest.views.CustomAppButton
import com.hussein.suggesttest.views.CustomTextView
import kotlinx.android.synthetic.main.dialog_details_layout.view.*

class DetailsDailog : DialogFragment(), View.OnClickListener {
    private lateinit var restaurant: Restaurant
    private lateinit var tvName:CustomTextView
    private lateinit var tvCat:CustomTextView
    private lateinit var tvRate:CustomTextView
    private lateinit var tvLink:CustomTextView
    private lateinit var btnOK:CustomAppButton

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnOK -> {
                try {
                    dismiss()
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
            R.id.tvLink -> {
                try {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.link))
                    startActivity(i)
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        fun newInstance(restaurant: Restaurant): DetailsDailog {
            val objDailog = DetailsDailog()
            objDailog.restaurant=restaurant
            return objDailog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        setStyle(android.app.DialogFragment.STYLE_NORMAL, R.style.BaseDialogTheme)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val objView = inflater.inflate(R.layout.dialog_details_layout, container, false)
        tvName=objView.tvName
        tvCat=objView.tvCat
        tvRate=objView.tvRate
        tvLink=objView.tvLink
        btnOK=objView.btnOK
        objView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        objView.textDirection = View.LAYOUT_DIRECTION_RTL
        bindData()
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        return objView
    }

    private fun bindData() {
        try {
            tvLink.gravity=Gravity.RIGHT
            tvCat.gravity=Gravity.RIGHT
            tvRate.gravity=Gravity.RIGHT
            tvName.gravity=Gravity.RIGHT

            tvName.text=restaurant.name
            tvCat.text=restaurant.cat
            tvRate.text= context!!.resources.getString(R.string.rate)+" "+restaurant.rating.toString()+"/10"
            tvLink.text=context!!.resources.getString(R.string.see_link)+" "+restaurant.link
            btnOK.setOnClickListener(this)
            tvLink.setOnClickListener(this)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }


    }
}