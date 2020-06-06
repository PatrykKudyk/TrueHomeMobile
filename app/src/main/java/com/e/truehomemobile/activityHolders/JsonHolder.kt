package com.e.truehomemobile.activityHolders

import com.e.truehomemobile.models.authorization.LoginRequest
import com.e.truehomemobile.models.authorization.RegistrationRequest
import com.e.truehomemobile.models.user.User

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

    fun createUserUpdateJson(user: User): String {
        var requestJson = "{" + "\"emailAddress\":\"" + user.emailAddress + "\"," +
                "\"username\":\"" + user.username + "\"," +
                "\"firstName\":\"" + user.firstName + "\"," +
                "\"lastName\":\"" + user.lastName + "\"," +
                "\"age\":\"" + user.age + "\"," +
                "\"street\":\"" + user.street + "\"," +
                "\"streetNumber\":\"" + user.streetNumber + "\"," +
                "\"telephoneNumber\":\"" + user.telephoneNumber + "\"}"
        return requestJson
    }
}