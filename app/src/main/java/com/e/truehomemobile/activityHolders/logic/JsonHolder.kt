package com.e.truehomemobile.activityHolders.logic

import com.e.truehomemobile.models.authorization.LoginRequest

class JsonHolder {

    fun createLoginRequestJson(loginRequest: LoginRequest): String{
        var requestJson = "{" + "\"login\":\"" + loginRequest.login + "\"," +
                "\"password\":\"" + loginRequest.password + "\" }"

        return requestJson
    }
}