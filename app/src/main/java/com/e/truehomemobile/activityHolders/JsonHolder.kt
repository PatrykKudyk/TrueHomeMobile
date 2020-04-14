package com.e.truehomemobile.activityHolders

import com.e.truehomemobile.models.authorization.LoginRequest
import com.e.truehomemobile.models.authorization.RegistrationRequest

class JsonHolder {

    fun createLoginRequestJson(loginRequest: LoginRequest): String{
        var requestJson = "{" + "\"login\":\"" + loginRequest.login + "\"," +
                "\"password\":\"" + loginRequest.password + "\" }"
        return requestJson
    }

    fun createRegistrationRequestJson(registrationRequest: RegistrationRequest): String{
        var requestJson = "{" + "\"login\":\"" + registrationRequest.login + "\"," +
                "\"password\":\"" + registrationRequest.password + "\"," +
                "\"email\":\"" + registrationRequest.email + "\"}"
        return requestJson
    }
}