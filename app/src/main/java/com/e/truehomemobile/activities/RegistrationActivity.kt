package com.e.truehomemobile.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
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
            clearFieldErrors()
            if(areFieldsCorrect()){
                makeSuccessActions()
                Handler().postDelayed({
                    val intent = Intent()
                    intent.putExtra("login", login_field.text.toString())

                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }, 2000)
            }
        }

        backTextView.setOnClickListener{
            finish()
        }

    }

    private fun makeSuccessActions() {
        val layoutAnimation = AnimationUtils.loadAnimation(this,
            R.anim.gone_to_down)
        fields_button_layout.startAnimation(layoutAnimation)
        fields_button_layout.visibility = View.GONE
        registeredTextView.visibility = View.VISIBLE
    }

    private fun isEmailCorrect(): Boolean {
        val email = email_field.text.toString()
        val emailSplitted = email.split("@")
        if(emailSplitted.size != 2){
            email_field_layout.error = getString(getStringIdentifier(this,
                "field_error_email_incorrect"))
            email_repeat_field_layout.error = getString(getStringIdentifier(this,
                "field_error_email_incorrect"))
            return false
        }
        if(emailSplitted[1].isEmpty()){
            email_field_layout.error = getString(getStringIdentifier(this,
                "field_error_email_incorrect"))
            email_repeat_field_layout.error = getString(getStringIdentifier(this,
                "field_error_email_incorrect"))
            return false
        }
        val secondEmailSplit = emailSplitted[1].split(".")
        if(secondEmailSplit.size < 2){
            email_field_layout.error = getString(getStringIdentifier(this,
                "field_error_email_incorrect"))
            email_repeat_field_layout.error = getString(getStringIdentifier(this,
                "field_error_email_incorrect"))
            return false
        }
        for(string in secondEmailSplit){
            if(string.isEmpty()){
                email_field_layout.error = getString(getStringIdentifier(this,
                    "field_error_email_incorrect"))
                email_repeat_field_layout.error = getString(getStringIdentifier(this,
                    "field_error_email_incorrect"))
            }
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
        password_repeat_field_layout.error = getString(getStringIdentifier(this,
            "field_error_password_incorrect_symbols"))
        password_field.text = null
        password_repeat_field.text = null
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

    private fun makeFirstLayerFieldTests(): Boolean{
        var isPassed = arePasswordsEqual()
        if(!areEmailsEqual()){
            isPassed = false
        }
        if(!isLoginLengthCorrect()){
            isPassed = false
        }
        return isPassed
    }

    private fun makeSecondLayerFieldTests(): Boolean{
        var isPassed = isPasswordCorrect()
        if(!isEmailCorrect()) {
            isPassed = false
        }
        return isPassed
    }

    private fun areFieldsCorrect(): Boolean {
        if(areAllFieldsFilled()){
            if(makeFirstLayerFieldTests()){  //i had to create a method there otherwise, the test were not ran simultaneously
                if(makeSecondLayerFieldTests()){                        //same as previously
                    return true
                }
                return false
            }
            return false
        }
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
