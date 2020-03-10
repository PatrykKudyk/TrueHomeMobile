package com.e.truehomemobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.e.truehomemobile.activityHolders.AnimationsHolder
import com.e.truehomemobile.R
import kotlinx.android.synthetic.main.activity_logo.*

class LogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        val animationHolder = AnimationsHolder(this)
        animationHolder.spin(loadingBar,800, 0)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        },3700)
    }

    override fun onBackPressed() {

    }
}
