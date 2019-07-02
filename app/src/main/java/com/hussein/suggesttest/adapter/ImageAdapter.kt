package com.hussein.suggesttest.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.support.v4.content.ContextCompat.getSystemService
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import android.view.View
import com.hussein.suggesttest.R
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.item_gallery_layout.view.*


class ImageAdapter(mContext: Context,list: List<String>) : PagerAdapter() {
    var listImages = list
    var mContext: Context? = mContext
    var mLayoutInflater: LayoutInflater? = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return listImages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater!!.inflate(R.layout.item_gallery_layout, container, false)
        val ivRestaurant = itemView.ivRestaurant
        val pbarLoad = itemView.pbarLoad
        pbarLoad.visibility = View.VISIBLE
        Picasso.with(mContext).load(listImages[position]).placeholder(R.drawable.logo).into(ivRestaurant, object : Callback {
            override fun onSuccess() {
                pbarLoad.visibility = View.GONE
            }

            override fun onError() {
                Picasso.with(mContext).load(listImages[position]).into(ivRestaurant)
                //pbarLoad.visibility = View.GONE
            }
        })

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}