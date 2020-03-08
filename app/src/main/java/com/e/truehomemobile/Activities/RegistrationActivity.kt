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
            clearFieldErrors()
            if(areFieldsCorrect()){
                if(arePasswordsEqual() && areEmailsEqual() && isLoginLengthCorrect()){
                    if(isPasswordCorrect() && isEmailCorrect()){
                        finish()
                    }
                }
            }else{
                Toast.makeText(this,getString(getStringIdentifier(this,
                    "toast_fill_all_fields")), Toast.LENGTH_SHORT).show()
            }
        }

        backTextView.setOnClickListener{
            finish()
        }

    }

    private fun isEmailCorrect(): Boolean {
        val email = email_field.text.toString()
        val emailSplitted = email.split("@")
        if(emailSplitted.size != 2){
            email_field_layout.error = getString(getStringIdentifier(this,
                "field_error_email_incorrect"))
            return false
        }
        val secondEmailSplit = emailSplitted[1].split(".")
        if(secondEmailSplit.size < 2){
            email_field_layout.error = getString(getStringIdentifier(this,
                "field_error_email_incorrect"))
            return false
        }
        return true
    }

    private fun isPasswordCorrect(): Boolean {
        val password = password_field.text.toString().toCharArray()
        var lowerCaseFlag = false
        var upperCaseFlag = false
        var numberFlag = false
        for( c in password){
            if(Character.isDigit(c)){
                numberFlag = true
            }else if(Character.isLowerCase(c)){
                lowerCaseFlag = true
            }else if(Character.isUpperCase(c)){
                upperCaseFlag = true
            }
        }
        if(numberFlag && lowerCaseFlag && upperCaseFlag){
            return true
        }
        password_field_layout.error = getString(getStringIdentifier(this,
            "field_error_password_incorrect_symbols"))
//        password_repeat_field_layout.error = getString(getStringIdentifier(this,
//            "field_error_password_incorrect_symbols"))
        return false
    }

    private fun isLoginLengthCorrect(): Boolean {
        if(login_field.text.toString().length >= 4){
            return true
        }
        login_field_layout.error = getString(getStringIdentifier(this,
            "field_error_login_incorrect_length"))
        return false
    }

    private fun areEmailsEqual(): Boolean {
        if(email_field.text.toString() == email_repeat_field.text.toString()){
            return true
        }
        email_repeat_field_layout.error = getString(getStringIdentifier(this,
            "field_error_emails_not_equal"))
        return false
    }

    private fun arePasswordsEqual(): Boolean {
        if(password_field.text.toString() == password_repeat_field.text.toString()){
            return true
        }
        password_repeat_field_layout.error = getString(getStringIdentifier(this,
            "field_error_passwords_not_equal"))
        password_field.text = null
        password_repeat_field.text = null
        return false
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

    private fun clearFieldErrors(){
        login_field_layout.error = null
        login_field.clearFocus()
        email_field_layout.error = null
        email_field.clearFocus()
        email_repeat_field_layout.error = null
        email_repeat_field.clearFocus()
        password_field_layout.error = null
        password_field.clearFocus()
        password_repeat_field_layout.error = null
        password_repeat_field.clearFocus()
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
