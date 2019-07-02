package com.hussein.suggesttest.util.apphelper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout

class RevealAnimation {
    companion object {

        val EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X"
        val EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y"
        var revealX: Int = 0
        var revealY: Int = 0
        fun presentActivity(activity: Activity, view: View, second: Class<*>,isFinish:Boolean) {
            try {
                if (isFinish) {
                    AppConfigHelper.finishActivity(activity)
                }
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, "transition")
                revealX = (view.x + view.width / 2).toInt()
                revealY = (view.y + view.height / 2).toInt()

                val intent = Intent(activity, second)
                intent.putExtra(EXTRA_CIRCULAR_REVEAL_X, revealX)
                intent.putExtra(EXTRA_CIRCULAR_REVEAL_Y, revealY)

                ActivityCompat.startActivity(activity, intent, options.toBundle())

            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }
        }

        fun makeReveal(activity: Activity, rootLayout: View, savedInstanceState: Bundle?) {
            try {

                val intent = activity.intent
                if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                    intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                    intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)
                ) {
                    rootLayout.visibility = View.INVISIBLE

                    revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
                    revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)


                    val viewTreeObserver = rootLayout.viewTreeObserver
                    if (viewTreeObserver.isAlive) {
                        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                            override fun onGlobalLayout() {
                                RevealAnimation.revealActivity(activity, rootLayout, revealX, revealY)
                                rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            }
                        })
                    }
                } else {
                    rootLayout.visibility = View.VISIBLE
                }

            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }
        }

        fun revealActivity(activity: Activity, rootLayout: View, x: Int, y: Int) {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val finalRadius = (Math.max(rootLayout.width, rootLayout.height) * 1.1).toFloat()

                    // create the animator for this view (the start radius is zero)
                    val circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0f, finalRadius)
                    circularReveal.duration = 400
                    circularReveal.interpolator = AccelerateInterpolator()

                    // make the view visible and start the animation
                    rootLayout.visibility = View.VISIBLE
                    circularReveal.start()
                } else {
                    activity.finish()
                }
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }
        }

         fun unRevealActivity(activity: Activity, rootLayout: View) {
             try {

                 if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                     activity.finish()
                 } else {
                     val finalRadius = (Math.max(rootLayout.width, rootLayout.height) * 1.1).toFloat()
                     val circularReveal = ViewAnimationUtils.createCircularReveal(
                         rootLayout, revealX, revealY, finalRadius, 0f
                     )

                     circularReveal.duration = 400
                     circularReveal.addListener(object : AnimatorListenerAdapter() {
                         override fun onAnimationEnd(animation: Animator) {
                             rootLayout.visibility = View.INVISIBLE
                             activity.finish()
                         }
                     })


                     circularReveal.start()
                 }
             }
             catch (e:Exception)
             {
                 e.printStackTrace()
             }

        }
    }
}
