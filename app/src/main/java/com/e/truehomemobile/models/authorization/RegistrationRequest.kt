package com.e.truehomemobile.models.authorization

data class RegistrationRequest(
    val username: String,
    val password: String,
    val email: String
) {
}