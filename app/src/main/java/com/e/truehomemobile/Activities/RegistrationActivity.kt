package com.e.truehomemobile.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
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
            if(areFieldsCorrect()){
                finish()
            }else{
                Toast.makeText(this,getString(getStringIdentifier(this,
                    "toast_fill_all_fields")), Toast.LENGTH_SHORT).show()
            }
        }

        backTextView.setOnClickListener{
            finish()
        }

    }

    private fun areFieldsCorrect(): Boolean {
        if(areAllFieldsFilled()){
            return true
        }
        Toast.makeText(this, getString(getStringIdentifier(this,
            "toast_fill_all_fields")), Toast.LENGTH_SHORT).show()
        return false
    }

    private fun areAllFieldsFilled(): Boolean {
        var isCorrect = true
        if(login_field.text.toString() == ""){
            isCorrect = false
            login_field_layout.error = getString(getStringIdentifier(this,
                "field_error_empty_field"))
        }
        if(password_field.text.toString() == ""){
            isCorrect = false
            password_field_layout.error = getString(getStringIdentifier(this,
                "field_error_empty_field"))
        }
        if(password_repeat_field.text.toString() == ""){
            isCorrect = false
            password_repeat_field_layout.error = getString(getStringIdentifier(this,
                "field_error_empty_field"))
        }
        if(email_field.text.toString() == ""){
            isCorrect = false
            email_field_layout.error = getString(getStringIdentifier(this,
                "field_error_empty_field"))
        }
        if(email_repeat_field.text.toString() == ""){
            isCorrect = false
            email_repeat_field_layout.error = getString(getStringIdentifier(this,
                "field_error_empty_field"))
        }
        return isCorrect
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

    private fun getStringIdentifier(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "string", context.packageName)
    }

    override fun onBackPressed() {

    }
}
