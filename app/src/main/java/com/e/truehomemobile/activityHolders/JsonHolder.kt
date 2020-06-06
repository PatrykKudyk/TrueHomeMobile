package com.e.truehomemobile.activityHolders

import com.e.truehomemobile.models.authorization.LoginRequest
import com.e.truehomemobile.models.authorization.RegistrationRequest

class JsonHolder {

    fun createLoginRequestJson(loginRequest: LoginRequest): String {
        var requestJson = "{" + "\"username\":\"" + loginRequest.username + "\"," +
                "\"password\":\"" + loginRequest.password + "\" }"
        return requestJson
    }

    fun createRegistrationRequestJson(registrationRequest: RegistrationRequest): String {
        var requestJson = "{" + "\"username\":\"" + registrationRequest.username + "\"," +
                "\"password\":\"" + registrationRequest.password + "\"," +
                "\"email\":\"" + registrationRequest.email + "\"}"
        return requestJson
    }
}