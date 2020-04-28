package com.e.truehomemobile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.LogoLogicHolder

class LogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        val logicHolder = LogoLogicHolder(this, this)
        logicHolder.handleActivity()
    }

    override fun onBackPressed() {

    }
}
