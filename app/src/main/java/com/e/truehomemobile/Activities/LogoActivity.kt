package com.e.truehomemobile.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.e.truehomemobile.R

class LogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        val spinRight = AnimationUtils.loadAnimation(this, R.anim.spin_right)
        val loadingBar = findViewById(R.id.loadingBar) as ImageView


        loadingBar.startAnimation(spinRight)


        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        },3700)
    }

    override fun onBackPressed() {

    }
}
