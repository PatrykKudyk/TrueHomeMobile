package com.e.truehomemobile.activityHolders.logic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.e.truehomemobile.activities.LoginActivity
import com.e.truehomemobile.models.classes.LogicHolder

class LogoLogicHolder(context: Context, activity: Activity):
        LogicHolder(context, activity){

    fun handleActivity(){
        Handler().postDelayed({
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        },3700)
    }
}