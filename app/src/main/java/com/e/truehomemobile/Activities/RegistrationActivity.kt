package com.e.truehomemobile.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        openingAnimations()
        initFonts()

        register_button.setOnClickListener {
            finish()
        }

        backTextView.setOnClickListener{
            finish()
        }

    }

    private fun initFonts() {
        val passwordFieldLayout : TextInputLayout = findViewById(R.id.password_field_layout)
        val passwordRepeatFieldLayout : TextInputLayout = findViewById(R.id.password_repeat_field_layout)
        val typeface = ResourcesCompat.getFont(this, R.font.josefinsansregular)
        passwordFieldLayout.typeface = typeface
        passwordRepeatFieldLayout.typeface = typeface
    }

    private fun openingAnimations() {
        val imageAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
        val layoutAnimation = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)

        logoImageView.startAnimation(imageAnimation)
        fields_button_layout.startAnimation(layoutAnimation)
    }

    override fun onBackPressed() {

    }
}
