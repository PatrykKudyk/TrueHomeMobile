package com.e.truehomemobile

import android.app.Application
import com.e.truehomemobile.models.authorization.LoginResponse

class MyApp(): Application() {
    companion object{
        const val apiUrl = "https://10.0.2.2:18768/"
        lateinit var loginResponse: LoginResponse
        var isResponseReceived: Boolean = false
        var isLogged: Boolean = false
        var hasAppStarted: Boolean = false
    }
}