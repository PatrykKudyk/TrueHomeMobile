package com.e.truehomemobile.activityHolders

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.e.truehomemobile.R

class AnimationsHolder(val context: Context) {


    fun spin(view: View, duration: Long, offset: Long) {
        var animation = AnimationUtils.loadAnimation(context, R.anim.spin_right)
        animation.duration = duration
        animation.startOffset = offset
        view.startAnimation(animation)
    }

    fun fallFromTop(view: View, duration: Long, offset: Long) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.top_to_bottom)
        animation.duration = duration
        animation.startOffset = offset
        view.startAnimation(animation)
    }

    fun flyFromBottom(view: View, duration: Long, offset: Long) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top)
        animation.duration = duration
        animation.startOffset = offset
        view.startAnimation(animation)
    }

    fun flyaway(
        view: View,
        duration: Long,
        offset: Long,
        direction: Int
    ) {  // 0 - left, 1 - top, 2 - right, 3 - bottom
        lateinit var animation: Animation
        when (direction) {
            0 -> animation = AnimationUtils.loadAnimation(context, R.anim.gone_to_left)
            1 -> animation = AnimationUtils.loadAnimation(context, R.anim.gone_to_top)
            2 -> animation = AnimationUtils.loadAnimation(context, R.anim.gone_to_right)
            3 -> animation = AnimationUtils.loadAnimation(context, R.anim.gone_to_bottom)
            else -> {
            }
        }
        animation.duration = duration
        animation.startOffset = offset
        view.startAnimation(animation)
    }

    fun popUp(view: View, duration: Long, offset: Long) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.pop_up)
        animation.duration = duration
        animation.startOffset = offset
        view.startAnimation(animation)
    }
}