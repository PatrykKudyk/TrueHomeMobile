package com.e.truehomemobile.Activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.animation.AnimationUtils
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        startAnimations()

        val password_field_layout : TextInputLayout = findViewById(R.id.password_field_layout)
        val typeface = ResourcesCompat.getFont(this, R.font.josefinsansregular)
        password_field_layout.setTypeface(typeface)


    }

    private fun startAnimations() {
        val topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
        val leftToRight = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        val rightToLeft = AnimationUtils.loadAnimation(this, R.anim.right_to_left)

        logoImageView.startAnimation(topToBottom)
        login_field_layout.startAnimation(topToBottom)
        password_field_layout.startAnimation(topToBottom)
        login_button.startAnimation(rightToLeft)
        register_button.startAnimation(leftToRight)
    }

    override fun onBackPressed() {

    }
}
