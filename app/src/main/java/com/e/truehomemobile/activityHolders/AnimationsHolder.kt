package com.e.truehomemobile.activityHolders

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.e.truehomemobile.R

class AnimationsHolder(val context: Context) {


    fun spin(view: View, duration: Long, offset: Long){
        var animation = AnimationUtils.loadAnimation(context, R.anim.spin_right)
        animation.duration = duration
        animation.startOffset = offset
        view.startAnimation(animation)
    }

    fun fallFromTop(view: View, duration: Long, offset: Long){
        val animation = AnimationUtils.loadAnimation(context, R.anim.top_to_bottom)
        animation.duration = duration
        animation.startOffset = offset
        view.startAnimation(animation)
    }

    fun flyFromBottom(view: View, duration: Long, offset: Long){
        val animation = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top)
        animation.duration = duration
        animation.startOffset = offset
        view.startAnimation(animation)
    }
}