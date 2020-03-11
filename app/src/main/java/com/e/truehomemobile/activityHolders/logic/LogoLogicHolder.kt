package com.e.truehomemobile.activityHolders.logic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.e.truehomemobile.activities.LoginActivity
import com.e.truehomemobile.activityHolders.AnimationsHolder
import kotlinx.android.synthetic.main.activity_logo.*

class LogoLogicHolder(private val context: Context, private val activity: Activity) {

    private val animationsHolder = AnimationsHolder(context)

    fun handleActivity(){
        animationsHolder.spin(activity.loadingBar,800, 0)

        Handler().postDelayed({
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        },3700)
    }
}