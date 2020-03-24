package com.e.truehomemobile.models.Response

class LoginResponse(val idUser: Int, val idSession: String, val token: String,
                    val refreshToken: String, val experienceTime: Int, val claims: ArrayList<String>) {
}