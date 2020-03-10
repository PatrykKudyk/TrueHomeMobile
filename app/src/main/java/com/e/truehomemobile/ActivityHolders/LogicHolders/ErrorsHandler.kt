package com.e.truehomemobile.ActivityHolders.LogicHolders

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

    fun clearErrors(view: TextInputEditText){
        view.error = null
    }

    fun clearErrors(view: TextInputLayout){
        view.error = null
    }

    private fun getStringIdentifier(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "string", context.packageName)
    }
}