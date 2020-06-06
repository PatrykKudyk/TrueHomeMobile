package com.e.truehomemobile.models.authorization

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
) {
}