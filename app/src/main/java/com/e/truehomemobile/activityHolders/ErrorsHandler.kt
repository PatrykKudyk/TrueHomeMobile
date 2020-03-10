package com.e.truehomemobile.activityHolders

import android.content.Context
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ErrorsHandler(val context: Context) {

    fun setEmptyFieldError(view: TextInputLayout){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_empty_field"))
    }

    fun setEmptyFieldError(view: TextInputEditText){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_empty_field"))
    }

    fun clearError(view: TextInputEditText){
        view.error = null
    }

    fun clearError(view: TextInputLayout){
        view.error = null
    }

    fun setIncorrectEmailError(view: TextInputLayout){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_email_incorrect"))
    }

    fun setIncorrectEmailError(view: TextInputEditText){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_email_incorrect"))
    }

    fun setEmailsNotEqualError(view: TextInputEditText){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_emails_not_equal"))
    }

    fun setEmailsNotEqualError(view: TextInputLayout){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_emails_not_equal"))
    }

    fun setPasswordsNotEqualError(view: TextInputLayout){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_passwords_not_equal"))
    }

    fun setPasswordsNotEqualError(view: TextInputEditText){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_passwords_not_equal"))
    }

    fun setLoginLengthError(view: TextInputEditText){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_login_incorrect_length"))
    }

    fun setLoginLengthError(view: TextInputLayout){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_login_incorrect_length"))
    }

    fun setPasswordLengthError(view: TextInputLayout){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_password_incorrect_length"))
    }

    fun setPasswordLengthError(view: TextInputEditText){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_password_incorrect_length"))
    }

    fun setIncorrectPasswordError(view: TextInputEditText){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_password_incorrect_symbols"))
    }

    fun setIncorrectPasswordError(view: TextInputLayout){
        view.error = context.getString(getStringIdentifier(context,
            "field_error_password_incorrect_symbols"))
    }

    private fun getStringIdentifier(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "string", context.packageName)
    }
}