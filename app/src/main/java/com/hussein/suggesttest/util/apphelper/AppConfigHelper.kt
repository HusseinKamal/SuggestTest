package com.hussein.suggesttest.util.apphelper

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import com.hussein.suggesttest.R
import android.view.animation.LayoutAnimationController
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.ViewGroup
import android.content.Context
class AppConfigHelper {

    companion object {
        const val BASE_URL = "https://wainnakel.com/api/v1/"
        const val SUGGEST_URL = "GenerateFS.php"

        const val ARABIC_LANGUAGE="ar"
        const val ENGLISH_LANGUAGE="en"
        const val GALLERY_SHOW_INTENT="Images"
        const val DETAILS_DIALOG_TAG="details"



        fun transparentHeader(activity: Activity) {
            //make status bar transparent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val w = activity.window // in Activity's onCreate() for instance
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
        }


        fun getBitmapFromView(view: View): Bitmap {
            val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(returnedBitmap)
            val bgDrawable = view.background
            if (bgDrawable != null) {
                //has background drawable, then draw it on the canvas
                bgDrawable.draw(canvas)
            } else {
                //does not have background drawable, then draw white background on the canvas
                canvas.drawColor(Color.WHITE)
            }
            view.draw(canvas)
            return returnedBitmap
        }

        fun gotoActivity(context: Activity,activityClass:Class<*>,isFinish:Boolean) {
            try {
                val objIntent = Intent(context, activityClass)
                objIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK  or Intent.FLAG_ACTIVITY_NO_ANIMATION
                context.startActivity(objIntent)
                if(isFinish) {
                    context.finish()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        fun finishActivity(activity: Activity) {
            try {
                activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
                activity.finish()
            } catch (ex: Exception) {
                activity.finish()
                ex.printStackTrace()
            }

        }


        fun setLayoutAnim_slidedownfromtop(panel: ViewGroup, ctx: Context) {
            try {
                val set = AnimationSet(true)

                var animation: Animation = AlphaAnimation(0.0f, 1.0f)
                animation.duration = 100
                set.addAnimation(animation)

                animation = TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
                )
                animation.setDuration(500)
                set.addAnimation(animation)

                val controller = LayoutAnimationController(set, 0.25f)
                panel.layoutAnimation = controller
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        fun setLayoutAnim_slideup(panel: ViewGroup, ctx: Context) {
            try {
                val set = AnimationSet(true)

                /*
             * Animation animation = new AlphaAnimation(1.0f, 0.0f);
             * animation.setDuration(200); set.addAnimation(animation);
             */

                var animation: Animation = AlphaAnimation(0.0f, 1.0f)
                animation.duration = 100
                set.addAnimation(animation)

                animation = TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f
                )
                animation.setDuration(500)
                set.addAnimation(animation)
                animation.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {

                    }

                    override fun onAnimationRepeat(animation: Animation) {

                    }

                    override fun onAnimationEnd(animation: Animation) {
                        // MapContacts.this.mapviewgroup.setVisibility(View.INVISIBLE);

                    }
                })
                set.addAnimation(animation)

                val controller = LayoutAnimationController(
                    set, 0.25f
                )
                panel.layoutAnimation = controller
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

    }


}
