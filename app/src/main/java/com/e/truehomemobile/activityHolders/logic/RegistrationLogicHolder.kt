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
    private val errorsHolder = ErrorsHandler(context)
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
        errorsHolder.clearError(activity.email_field_layout)
        if(validationHolder.isEmailCorrect(activity.email_field.text.toString())){
            return true
        }
        errorsHolder.setIncorrectEmailError(activity.email_field_layout)
        return false
    }

    private fun isPasswordCorrect(): Boolean {
        if(validationHolder.isPasswordCorrect(activity.password_field.text.toString())){
            errorsHolder.clearError(activity.password_field_layout)
            return true
        }
        errorsHolder.setIncorrectPasswordError(activity.password_field_layout)
        return false
    }

    private fun isPasswordLengthCorrect(): Boolean{
        if(validationHolder.isLengthCorrect(activity.password_field.text.toString(), 8)){
            errorsHolder.clearError(activity.password_field_layout)
            return true
        }
        errorsHolder.setPasswordLengthError(activity.password_field_layout)
        return false
    }

    private fun isLoginLengthCorrect(): Boolean {
        if(validationHolder.isLengthCorrect(activity.login_field.text.toString(), 4)){
            errorsHolder.clearError(activity.login_field_layout)
            return true
        }
        errorsHolder.setLoginLengthError(activity.login_field_layout)
        return false
    }

    private fun areEmailsEqual(): Boolean {
        if(validationHolder.areFieldsEqual(activity.email_field, activity.email_repeat_field)){
            errorsHolder.clearError(activity.email_repeat_field_layout)
            return true
        }
        errorsHolder.setEmailsNotEqualError(activity.email_repeat_field_layout)
        return false
    }

    private fun arePasswordsEqual(): Boolean {
        if(validationHolder.areFieldsEqual(activity.password_field, activity.password_repeat_field)){
            errorsHolder.clearError(activity.password_repeat_field_layout)
            return true
        }
        errorsHolder.setPasswordsNotEqualError(activity.password_repeat_field_layout)
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
            errorsHolder.setEmptyFieldError(activity.login_field_layout)
        }
        if(!validationHolder.isFieldFilled(activity.email_field)){
            isCorrect = false
            errorsHolder.setEmptyFieldError(activity.email_field_layout)
        }
        if(!validationHolder.isFieldFilled(activity.email_repeat_field)){
            isCorrect = false
            errorsHolder.setEmptyFieldError(activity.email_repeat_field_layout)
        }
        if(!validationHolder.isFieldFilled(activity.password_field)){
            isCorrect = false
            errorsHolder.setEmptyFieldError(activity.password_field_layout)
        }
        if(!validationHolder.isFieldFilled(activity.password_repeat_field)){
            isCorrect = false
            errorsHolder.setEmptyFieldError(activity.password_repeat_field_layout)
        }
        return isCorrect
    }

    private fun clearFieldErrors(){
        errorsHolder.clearError(activity.login_field_layout)
        activity.login_field.clearFocus()
        errorsHolder.clearError(activity.email_field_layout)
        activity.email_field.clearFocus()
        errorsHolder.clearError(activity.email_repeat_field_layout)
        activity.email_repeat_field.clearFocus()
        errorsHolder.clearError(activity.password_field_layout)
        activity.password_field.clearFocus()
        errorsHolder.clearError(activity.password_repeat_field_layout)
        activity. password_repeat_field.clearFocus()
    }

    private fun makeSuccessActions() {
        animationHolder.flyaway(activity.registration_card_view, 500, 0, 3)
        activity.registration_card_view.visibility = View.GONE
        activity.registeredTextView.visibility = View.VISIBLE
    }

    private fun makeStartAnimations(){
        animationHolder.popUp(activity.registration_card_view, 700, 1000)
        animationHolder.flyFromBottom(activity.logoImageView, 700, 900)
    }

    private fun initFonts(){
        val typeface = ResourcesCompat.getFont(context, R.font.josefinsansregular)
        activity.password_field_layout.typeface = typeface
        activity.password_repeat_field_layout.typeface = typeface
    }

}