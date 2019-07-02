package com.hussein.suggesttest.ui
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.hussein.suggesttest.R
import com.hussein.suggesttest.util.apphelper.RevealAnimation
import com.hussein.suggesttest.util.languagehelper.LanguageHelper
import com.google.android.gms.maps.model.MarkerOptions
import android.animation.ValueAnimator
import com.hussein.suggesttest.model.Restaurant
import com.hussein.suggesttest.util.webservicehelper.ApiEndPoint
import com.hussein.suggesttest.util.webservicehelper.ApiEndpointInterface
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.maps.MapsInitializer
import android.graphics.Point
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.DrawableCompat
import android.os.Build
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.net.Uri
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import com.google.android.gms.maps.CameraUpdateFactory
import com.hussein.suggesttest.dialog.DetailsDailog
import com.hussein.suggesttest.util.apphelper.AppConfigHelper
import kotlinx.android.synthetic.main.header_layout.*
import java.util.ArrayList


class HomeActivity : AppCompatActivity(),View.OnTouchListener,View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    private var mGoogleMap: GoogleMap? = null
    private var options = MarkerOptions()
    private lateinit var latlngs:LatLng
    private var name: String? = null
    private var snippet: String? = null
    private var ICON_WIDTH: Int = 0
    private var ICON_HEIGHT: Int = 0
    private val ICON_SELECTED_SCALE_FACTOR = 2f
    private var animator: ValueAnimator? = null
    private lateinit var restData: Restaurant
    private var isOpen:Boolean=false

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (v?.id) {
            R.id.ivImage -> {
                try {
                    val objIntent = Intent (this, GalleryActivity::class.java)
                    objIntent.putStringArrayListExtra(AppConfigHelper.GALLERY_SHOW_INTENT, restData.image as ArrayList<String>?)
                    objIntent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(objIntent)
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
            R.id.ivInfo -> {
                showDetailsDialog()
            }
            R.id.ivMap -> {
                try {
                    val uriSTR="geo:"+latlngs.latitude+","+latlngs.longitude
                    val gmmIntentUri = Uri.parse(uriSTR)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    if (mapIntent.resolveActivity(packageManager) != null) {
                        startActivity(mapIntent)
                    }
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
            R.id.iv_back_arrow -> {
                AppConfigHelper.finishActivity(this)
            }
            R.id.ivArrow -> {
                try {
                    if(isOpen)
                    {
                        AppConfigHelper.setLayoutAnim_slideup(lyDetails,this)
                        ivArrow.setImageResource(R.drawable.ic_arrow_down)
                        lyDetails.visibility=View.GONE
                        isOpen=false

                    }
                    else
                    {
                        lyDetails.visibility=View.VISIBLE
                        AppConfigHelper.setLayoutAnim_slidedownfromtop(lyDetails,this)
                        ivArrow.setImageResource(R.drawable.ic_arrow_up)
                        isOpen=true
                    }
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
        }
        return false
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSuggest -> {
                try {
                    getSuggestRestaurant()
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
            R.id.btnSetting -> {
            }
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    public override fun onResume() {
        super.onResume()
        map.onResume()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map.onSaveInstanceState(outState)
    }
    override fun onMapReady(p0: GoogleMap?) {
        try {
            mGoogleMap = p0
            mGoogleMap!!.setOnMarkerClickListener(this)
            mGoogleMap!!.setOnMapClickListener(this)
            //get suggest restaurant
            getSuggestRestaurant()
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    override fun onMapClick(p0: LatLng?) {
        try {
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return try {
            if(p0!=null) {
                resizeMarker(p0)
            }
            true
        } catch (e:Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguageHelper().initLanguage(this, true)//set the selected language settings
        setContentView(R.layout.activity_main)
        RevealAnimation.makeReveal(this,lyContainer,savedInstanceState)
        try {
            MapsInitializer.initialize(this)
            map.onCreate(savedInstanceState)
            map.getMapAsync(this)
            initViews()
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }
    private fun initViews()
    {
        try {
            val size = Point()
            val display = windowManager.defaultDisplay
            display.getSize(size)
            ICON_WIDTH = (size.x * .1).toInt()
            ICON_HEIGHT = ICON_WIDTH

            //listeners
            ivImage.setOnTouchListener(this)
            ivMap.setOnTouchListener(this)
            ivInfo.setOnTouchListener(this)
            iv_back_arrow.setOnTouchListener(this)
            ivArrow.setOnTouchListener(this)
            btnSuggest.setOnClickListener(this)
            btnSetting.setOnClickListener(this)

        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            RevealAnimation.unRevealActivity(this,lyContainer)
        }
        catch (e:Exception)
        {
            e.printStackTrace()
            finish()
        }

    }

    private fun getSuggestRestaurant() {
        try {
            val objApiEndpointInterface = ApiEndPoint.client()!!.create(ApiEndpointInterface::class.java)
            objApiEndpointInterface.getSuggestRestaurant("26.2716025,50.2017993", "value")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Restaurant> {
                    override fun onNext(restaurant: Restaurant) {
                        try {
                            if (restaurant != null) {
                                restData=restaurant
                                bindData()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                    }
                })
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }
    private fun bindData() {
        try {
            name=restData.name
            snippet= restData.cat
            tvName.text=restData.name
            tvCat.text=" -"+restData.cat
            tvRateVaue.text=restData.rating.toString()
            latlngs= LatLng(restData.lat,restData.lon)

            //show details
            lyDetails.visibility=View.VISIBLE
            AppConfigHelper.setLayoutAnim_slidedownfromtop(lyDetails,this)
            ivArrow.setImageResource(R.drawable.ic_arrow_up)
            isOpen=true
            //create marker
            createMarker(restData.lat,restData.lon, name!!, snippet!!)
            //animate google map camera
            animateMapCamera(latlngs)
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    private fun createMarker(latitude: Double, longitude: Double, title: String, snippet: String): Marker {
        val mapIcon = getBitmapFromVectorDrawable(this, R.drawable.ic_location)
        val newBitMap = Bitmap.createScaledBitmap(mapIcon, ICON_WIDTH, ICON_HEIGHT, false)
        val mapIconBitMapDescriptor = BitmapDescriptorFactory.fromBitmap(newBitMap)
        options = MarkerOptions()
            .position(LatLng(latitude, longitude))
            .title(title)
            .snippet(snippet)
            .icon(mapIconBitMapDescriptor)
        val marker = mGoogleMap!!.addMarker(options)
        marker.tag = newBitMap
        return marker
    }

    private fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(context, drawableId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun animateMapCamera(latLng: LatLng)
    {
        try {
               mGoogleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    private fun resizeMarker(marker: Marker)
    {
        try {
            val mapIcon =  marker.tag as Bitmap
            val animation = ValueAnimator.ofFloat(1F, ICON_SELECTED_SCALE_FACTOR)
            animation.duration = 600
            animation.interpolator = AnticipateOvershootInterpolator()
            animation.addUpdateListener{
                val scaleFactor:Float = it.animatedValue as Float
                val newBitMap = Bitmap.createScaledBitmap(mapIcon, (ICON_WIDTH * scaleFactor).toInt(), (ICON_HEIGHT * scaleFactor).toInt(),false)
                marker.tag = newBitMap;
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(newBitMap));
            }
            animation.start()
            this.animator = animation
            mGoogleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latlngs, 17.0f))
            marker.showInfoWindow()
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    private fun showDetailsDialog() {
        try {
            val objFragmentTransaction = supportFragmentManager.beginTransaction()
            val prevFragment = supportFragmentManager.findFragmentByTag(AppConfigHelper.DETAILS_DIALOG_TAG)
            if (prevFragment != null) {
                objFragmentTransaction.remove(prevFragment)
            }
            val objDialog = DetailsDailog.newInstance(restData)
            objDialog.show(objFragmentTransaction, AppConfigHelper.DETAILS_DIALOG_TAG)
        } catch (objExcption: Exception) {
            objExcption.printStackTrace()
        }

    }
}
