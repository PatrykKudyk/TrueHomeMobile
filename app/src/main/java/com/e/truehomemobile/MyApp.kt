package com.e.truehomemobile

import android.app.Application

class MyApp(): Application() {
    companion object{
        val apiUrl = "https://127.0.0.1:2137/"
    }
}