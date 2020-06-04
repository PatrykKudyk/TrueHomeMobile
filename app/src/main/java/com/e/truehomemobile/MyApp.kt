package com.e.truehomemobile

import android.app.Application
import com.e.truehomemobile.models.authorization.LoginResponse

class MyApp() : Application() {
    companion object {
//        const val apiUrl = "http://10.0.2.2:50011/"
        const val homeUrl = "http://54.38.55.82:18768/"
        const val identityUrl = "http://54.38.55.82:18766/"
        lateinit var loginResponse: LoginResponse
        var isResponseReceived: Boolean = false
        var isLogged: Boolean = false
        var token: String = ""
        var refreshToken: String = ""
        var language: String = "en"
    }
}