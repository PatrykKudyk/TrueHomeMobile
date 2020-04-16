package com.e.truehomemobile.models.authorization

data class LoginResponse(val idUser: Int,
                         val idSession: String,
                         val token: String,
                         val refreshToken: String,
                         val experienceTime: Int,
                         val claims: ArrayList<String>) {
}