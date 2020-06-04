package com.e.truehomemobile

import android.app.Application
import com.e.truehomemobile.models.authorization.LoginResponse

class MyApp() : Application() {
    companion object {
        const val apiUrl = "http://10.0.2.2:50011/"
        lateinit var loginResponse: LoginResponse
        var isResponseReceived: Boolean = false
        var isLogged: Boolean = false
        var token: String = ""
        var refreshToken: String = ""
        var language: String = "en"
    }
}