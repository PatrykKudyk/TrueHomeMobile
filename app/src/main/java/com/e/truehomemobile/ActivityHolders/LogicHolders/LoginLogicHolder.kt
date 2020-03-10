package com.e.truehomemobile.ActivityHolders.LogicHolders

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.Activities.RegistrationActivity
import com.e.truehomemobile.ActivityHolders.AnimationsHolder
import com.e.truehomemobile.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginLogicHolder(val context: Context, val activity: Activity) {

    val animationHolder = AnimationsHolder(context)

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
        if(activity.login_field.text.toString() == ""){
            activity.login_field_layout.error = context.getString(getStringIdentifier(context,
                "field_error_empty_field"))
        }else{
            isCorrect = true
        }
        if(activity.password_field.text.toString() == "") {
            activity.password_field_layout.error = context.getString(getStringIdentifier(context,
                "field_error_empty_field"))
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
        activity.login_field_layout.error = null
        activity.login_field.clearFocus()
        activity.password_field_layout.error = null
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

    private fun getStringIdentifier(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "string", context.packageName)
    }

}