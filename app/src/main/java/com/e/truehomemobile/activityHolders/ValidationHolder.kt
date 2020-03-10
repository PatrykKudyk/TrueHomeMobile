package com.e.truehomemobile.activityHolders

import android.widget.EditText

class ValidationHolder {

    fun isFieldFilled(editText: EditText): Boolean{
        if(editText.text.toString() == ""){
            return false
        }
        return true
    }
}