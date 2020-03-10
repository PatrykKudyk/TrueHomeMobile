package com.e.truehomemobile.ActivityHolders.LogicHolders

import android.view.View
import android.widget.EditText

class ValidationHolder {

    fun isFieldFilled(editText: EditText): Boolean{
        if(editText.text.toString() == ""){
            return false
        }
        return true
    }
}