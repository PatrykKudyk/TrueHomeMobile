package com.e.truehomemobile.activityHolders

import android.widget.EditText

class ValidationHolder {

    fun isFieldFilled(editText: EditText): Boolean{
        if(editText.text.toString() == ""){
            return false
        }
        return true
    }

    fun isEmailCorrect(email: String): Boolean{
        val emailSplitted = email.split("@")
        if(emailSplitted.size != 2){
            return false
        }
        if(emailSplitted[1].isEmpty()){
            return false
        }
        val secondEmailSplit = emailSplitted[1].split(".")
        if(secondEmailSplit.size < 2){
            return false
        }
        for(string in secondEmailSplit){
            if(string.isEmpty()){
                return false
            }
        }
        return true
    }

    fun areFieldsEqual(editText: EditText, editText2: EditText): Boolean{
        if(editText.text.toString() == editText2.text.toString()){
            return true
        }
        return false
    }

    fun isLengthCorrect(string: String, length: Int): Boolean{
        if(string.length < length){
            return false
        }
        return true
    }

    fun isPasswordCorrect(string: String): Boolean{
        val password = string.toCharArray()
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
        return false
    }
}