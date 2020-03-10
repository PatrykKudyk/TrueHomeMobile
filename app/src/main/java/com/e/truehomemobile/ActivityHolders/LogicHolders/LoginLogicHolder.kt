package com.e.truehomemobile.ActivityHolders.LogicHolders

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.Activities.RegistrationActivity
import com.e.truehomemobile.ActivityHolders.AnimationsHolder
import com.e.truehomemobile.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginLogicHolder(private val context: Context, private val activity: Activity) {

    private val animationHolder = AnimationsHolder(context)
    private val validationHolder = ValidationHolder()
    private val errorsHandler = ErrorsHandler(context)

    fun initActivity(){
        makeStartAnimations()
        initFonts()
    }

    fun handleRegisterButton(){
        val intent = Intent(context, RegistrationActivity::class.java)
        activity.startActivityForResult(intent, 0)
        clearFields()
    }

    fun handleLoginButton(){
        clearFieldsErrors()
        if(areFieldsFilled()){
            activity.login_field.text = null
            activity.password_field.text = null
        }
    }

    private fun areFieldsFilled(): Boolean {
        var isCorrect = false
        if(!validationHolder.isFieldFilled(activity.login_field)){
            errorsHandler.setEmptyFieldError(activity.login_field_layout)
        }else{
            isCorrect = true
        }
        if(!validationHolder.isFieldFilled(activity.password_field)) {
            errorsHandler.setEmptyFieldError(activity.password_field_layout)
            isCorrect = false
        }
        return isCorrect
    }

    private fun clearFields(){
        clearFieldsErrors()
        activity.login_field.text = null
        activity.password_field.text = null
    }

    private fun clearFieldsErrors(){
        errorsHandler.clearErrors(activity.login_field_layout)
        errorsHandler.clearErrors(activity.password_field_layout)
        activity.login_field.clearFocus()
        activity.password_field.clearFocus()
    }

    private fun makeStartAnimations(){
        animationHolder.fallFromTop(activity.logoImageView,600, 200)
        animationHolder.fallFromTop(activity.login_field_layout, 600, 250)
        animationHolder.fallFromTop(activity.password_field_layout, 600, 250)
        animationHolder.flyFromBottom(activity.login_button, 400, 330)
        animationHolder.flyFromBottom(activity.register_button, 400, 330)
    }

    private fun initFonts(){
        val typeface = ResourcesCompat.getFont(context, R.font.josefinsansregular)
        activity.password_field_layout.typeface = typeface
    }
}