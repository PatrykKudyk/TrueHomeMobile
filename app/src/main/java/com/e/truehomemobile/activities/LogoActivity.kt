package com.e.truehomemobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.e.truehomemobile.activityHolders.AnimationsHolder
import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.logic.LogoLogicHolder
import kotlinx.android.synthetic.main.activity_logo.*

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
