package com.e.truehomemobile.activityHolders.logic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.AnimationsHolder
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.ValidationHolder
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationLogicHolder(private val context: Context, private val activity: Activity) {

    private val animationHolder = AnimationsHolder(context)
    private val errorsHandler = ErrorsHandler(context)
    private val validationHolder = ValidationHolder()

    fun initActivity(){
        makeStartAnimations()
        initFonts()
    }

    fun handleRegisterButton(){
        if(areFieldsCorrect()){
            makeSuccessActions()
            Handler().postDelayed({
                val intent = Intent()
                intent.putExtra("login", activity.login_field.text.toString())
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
            }, 2000)
        }
    }

    fun handleFieldsListeners(){
        handleLoginField()
        handleEmailField()
        handleEmailRepeatField()
        handlePasswordField()
        handlePasswordRepeatField()
    }

    private fun handlePasswordRepeatField() {
        activity.password_repeat_field.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                arePasswordsEqual()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun handlePasswordField() {
        activity.password_field.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(isPasswordLengthCorrect()){
                    isPasswordCorrect()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun handleEmailRepeatField() {
        activity.email_repeat_field.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                areEmailsEqual()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun handleEmailField() {
        activity.email_field.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isEmailCorrect()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun handleLoginField(){
        activity.login_field.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isLoginLengthCorrect()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun isEmailCorrect(): Boolean {
        errorsHandler.clearError(activity.email_field_layout)
        if(validationHolder.isEmailCorrect(activity.email_field.text.toString())){
            return true
        }
        errorsHandler.setIncorrectEmailError(activity.email_field_layout)
        return false
    }

    private fun isPasswordCorrect(): Boolean {
        if(validationHolder.isPasswordCorrect(activity.password_field.text.toString())){
            errorsHandler.clearError(activity.password_field_layout)
            return true
        }
        errorsHandler.setIncorrectPasswordError(activity.password_field_layout)
        return false
    }

    private fun isPasswordLengthCorrect(): Boolean{
        if(validationHolder.isLengthCorrect(activity.password_field.text.toString(), 8)){
            errorsHandler.clearError(activity.password_field_layout)
            return true
        }
        errorsHandler.setPasswordLengthError(activity.password_field_layout)
        return false
    }

    private fun isLoginLengthCorrect(): Boolean {
        if(validationHolder.isLengthCorrect(activity.login_field.text.toString(), 4)){
            errorsHandler.clearError(activity.login_field_layout)
            return true
        }
        errorsHandler.setLoginLengthError(activity.login_field_layout)
        return false
    }

    private fun areEmailsEqual(): Boolean {
        if(validationHolder.areFieldsEqual(activity.email_field, activity.email_repeat_field)){
            errorsHandler.clearError(activity.email_repeat_field_layout)
            return true
        }
        errorsHandler.setEmailsNotEqualError(activity.email_repeat_field_layout)
        return false
    }

    private fun arePasswordsEqual(): Boolean {
        if(validationHolder.areFieldsEqual(activity.password_field, activity.password_repeat_field)){
            errorsHandler.clearError(activity.password_repeat_field_layout)
            return true
        }
        errorsHandler.setPasswordsNotEqualError(activity.password_repeat_field_layout)
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
        clearFieldErrors()
        var isCorrect = true
        if(!validationHolder.isFieldFilled(activity.login_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(activity.login_field_layout)
        }
        if(!validationHolder.isFieldFilled(activity.email_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(activity.email_field_layout)
        }
        if(!validationHolder.isFieldFilled(activity.email_repeat_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(activity.email_repeat_field_layout)
        }
        if(!validationHolder.isFieldFilled(activity.password_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(activity.password_field_layout)
        }
        if(!validationHolder.isFieldFilled(activity.password_repeat_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(activity.password_repeat_field_layout)
        }
        return isCorrect
    }

    private fun clearFieldErrors(){
        errorsHandler.clearError(activity.login_field_layout)
        activity.login_field.clearFocus()
        errorsHandler.clearError(activity.email_field_layout)
        activity.email_field.clearFocus()
        errorsHandler.clearError(activity.email_repeat_field_layout)
        activity.email_repeat_field.clearFocus()
        errorsHandler.clearError(activity.password_field_layout)
        activity.password_field.clearFocus()
        errorsHandler.clearError(activity.password_repeat_field_layout)
        activity. password_repeat_field.clearFocus()
    }

    private fun makeSuccessActions() {
        animationHolder.flyaway(activity.fields_button_layout, 500, 0, 3)
        activity.fields_button_layout.visibility = View.GONE
        activity.registeredTextView.visibility = View.VISIBLE
    }

    private fun makeStartAnimations(){
        animationHolder.fallFromTop(activity.fields_button_layout, 600, 320)
        animationHolder.flyFromBottom(activity.logoImageView, 600, 250)
    }

    private fun initFonts(){
        val typeface = ResourcesCompat.getFont(context, R.font.josefinsansregular)
        activity.password_field_layout.typeface = typeface
        activity.password_repeat_field_layout.typeface = typeface
    }

}